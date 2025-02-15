package kiwi.core.storage.bitcask.log.sync;

import kiwi.core.common.KeyValue;
import kiwi.core.common.NamedThreadFactory;
import kiwi.core.error.KiwiWriteException;
import kiwi.core.storage.bitcask.log.LogSegment;
import kiwi.core.storage.bitcask.log.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class BatchSegmentWriter extends SegmentWriter {
    private static final Logger logger = LoggerFactory.getLogger(BatchSegmentWriter.class);

    private final ScheduledExecutorService scheduler;
    private final BlockingQueue<WriteRequest> queue;

    public BatchSegmentWriter(Supplier<LogSegment> activeSegmentSupplier, Duration window) {
        super(activeSegmentSupplier);

        queue = new LinkedBlockingQueue<>();

        scheduler = Executors.newSingleThreadScheduledExecutor(NamedThreadFactory.create("sync"));
        scheduler.scheduleAtFixedRate(this::processBatch, 0, window.toMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int append(Record record) throws KiwiWriteException {
        WriteRequest request = new WriteRequest(record);
        try {
            queue.put(request);
            return request.waitForSync();
        } catch (IOException | InterruptedException e) {
            throw new KiwiWriteException("Interrupted while waiting for sync", e);
        }
    }

    private void processBatch() {
        if (closed.get() || queue.isEmpty()) {
            return;
        }

        try {
            List<WriteRequest> batch = new ArrayList<>(queue.size());

            int drained = queue.drainTo(batch);
            if (drained == 0) {
                return;
            }

            List<KeyValue<WriteRequest, Integer>> writes = new ArrayList<>(batch.size());
            for (WriteRequest request : batch) {
                int bytes = super.append(request.record);
                writes.add(KeyValue.of(request, bytes));
            }

            sync();

            for (KeyValue<WriteRequest, Integer> kv : writes) {
                WriteRequest request = kv.key();
                int written = kv.value();
                request.markSynced(written);
            }

            logger.trace("Synced active segment with batch of {} records", batch.size());
        } catch (KiwiWriteException ex) {
            handleSyncFailure(ex);
        }
    }

    private void handleSyncFailure(KiwiWriteException ex) {
        List<WriteRequest> failedRequests = new ArrayList<>();
        queue.drainTo(failedRequests);

        for (WriteRequest request : failedRequests) {
            request.markFailed(ex);
        }
    }

    @Override
    public void close() {
        super.close();
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(15, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            logger.error("Error while shutting down periodic sync scheduler", e);
        }
    }

    private static class WriteRequest {
        public final Record record;

        private final Object lock = new Object();
        private volatile int written = 0;
        private volatile boolean isSynced = false;
        private volatile KiwiWriteException syncException = null;

        private WriteRequest(Record record) {
            this.record = record;
        }

        public int waitForSync() throws InterruptedException, IOException {
            synchronized (lock) {
                while (!isSynced && syncException == null) {
                    lock.wait();
                }
            }

            if (syncException != null) {
                throw syncException;
            }

            return written;
        }

        public void markSynced(int writtenBytes) {
            synchronized (lock) {
                written = writtenBytes;
                isSynced = true;
                lock.notifyAll();
            }
        }

        public void markFailed(KiwiWriteException ex) {
            synchronized (lock) {
                isSynced = true;
                syncException = ex;
                lock.notifyAll();
            }
        }
    }
}
