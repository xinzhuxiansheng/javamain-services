package kiwi.core.storage.bitcask.log.sync;

import kiwi.core.common.NamedThreadFactory;
import kiwi.core.storage.bitcask.log.LogSegment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class PeriodicSegmentWriter extends SegmentWriter {
    private static final Logger logger = LoggerFactory.getLogger(PeriodicSegmentWriter.class);

    private final ScheduledExecutorService scheduler;

    public PeriodicSegmentWriter(Supplier<LogSegment> activeSegmentSupplier, Duration interval) {
        super(activeSegmentSupplier);

        this.scheduler = Executors.newSingleThreadScheduledExecutor(NamedThreadFactory.create("sync"));
        this.scheduler.scheduleAtFixedRate(() -> {
                    if (!closed.get()) {
                        sync();
                        logger.trace("Synced active segment {}", activeSegment().name());
                    }
                },
                interval.toMillis(),
                interval.toMillis(),
                TimeUnit.MILLISECONDS
        );
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
}
