package com.javamain.db.rocksdbqueue01;


import com.javamain.db.rocksdbqueue01.exception.RocksQueueDequeueException;
import com.javamain.db.rocksdbqueue01.exception.RocksQueueEnqueueException;
import com.javamain.db.rocksdbqueue01.exception.RocksQueueException;
import com.javamain.db.rocksdbqueue01.exception.RocksQueueRemoveHeadException;
import com.javamain.db.rocksdbqueue01.jmx.RocksQueueMetric;
import com.javamain.db.rocksdbqueue01.util.Bytes;
import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.RocksDBException;
import org.rocksdb.RocksIterator;
import org.rocksdb.WriteBatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

public class RocksQueue {
    private static final Logger log = LoggerFactory.getLogger(RocksQueue.class);

    private static final byte[] HEAD = Bytes.stringToBytes("head");
    private static final byte[] TAIL = Bytes.stringToBytes("tail");
    private static final byte[] ONE = Bytes.longToByte(1);

    private final String queueName;

    private final AtomicLong head = new AtomicLong();
    private final AtomicLong tail = new AtomicLong();

    private final ColumnFamilyHandle cfHandle;
    private final ColumnFamilyHandle indexCfHandle;
    private final RocksIterator tailIterator;
    private final RocksStore store;
    private final RocksQueueMetric rocksQueueMetric;

    public RocksQueue(final String queueName, final RocksStore store) {
        this.queueName = queueName;
        this.store = store;

        this.cfHandle = store.createColumnFamilyHandle(queueName);
        this.indexCfHandle = store.createColumnFamilyHandle(getIndexColumnFamilyName(queueName));

        this.tail.set(getIndexId(TAIL, 0));
        this.head.set(getIndexId(HEAD, 0));

        this.tailIterator = store.newIteratorCF(cfHandle);

        this.rocksQueueMetric = new RocksQueueMetric(this, this.store.getDatabase());
        this.rocksQueueMetric.register();
        this.rocksQueueMetric.onInit();
    }

    private long getIndexId(byte[] key, long defaultValue) {
        byte[] value = store.getCF(key, indexCfHandle);

        if (value == null) {
            return defaultValue;
        }

        return Bytes.byteToLong(value);
    }

    public long enqueue(byte[] value) throws RocksQueueException {
        long id = tail.incrementAndGet();

        try (final WriteBatch writeBatch = new WriteBatch()) {
            final byte[] indexId = Bytes.longToByte(id);
            writeBatch.put(cfHandle, indexId, value);
            writeBatch.merge(indexCfHandle, TAIL, ONE);
            store.write(writeBatch);

            this.rocksQueueMetric.onEnqueue(value.length);
        } catch (RocksDBException e) {
            tail.decrementAndGet();
            log.error("Enqueue {} fails", id, e);
            throw new RocksQueueEnqueueException(store.getRockdbLocation(), e);
        }

        return id;
    }

    /**
     * Get the head and remove it from queue
     *
     * @return
     */
    public QueueItem dequeue() throws RocksQueueException {
        QueueItem item = consume();
        try {
            removeHead();
        } catch (RocksQueueException e) {
            throw new RocksQueueDequeueException(store.getRockdbLocation(), e);
        }
        if (item != null && item.getValue() != null) {
            this.rocksQueueMetric.onDequeue(item.getValue().length);
        }
        return item;
    }

    /**
     * Get the head of queue, in case there will have many deleted tombstones,
     * the final return index maybe bigger than the startId.
     *
     * @return
     */
    public QueueItem consume() {
        if (this.getSize() == 0) {
            return null;
        }

        //for the first time, if head is 0, seek from 1
        long sid = head.get() + 1;

        if (!tailIterator.isValid()) {
            tailIterator.seek(Bytes.longToByte(sid));
        }

        //when dequeue happens faster than enqueue, the tail iterator would be exhausted,
        //so we seek it again
        if (!tailIterator.isValid()) {
            return null;
        }

        this.rocksQueueMetric.onConsume();

        return new QueueItem(Bytes.byteToLong(tailIterator.key()), tailIterator.value());
    }

    /**
     * Remove the head from queue
     *
     * @return
     */
    public void removeHead() throws RocksQueueException {
        if (this.getSize() <= 0) {
            return;
        }

        try (final WriteBatch writeBatch = new WriteBatch()) {
            writeBatch.remove(cfHandle, Bytes.longToByte(head.get()));
            writeBatch.merge(indexCfHandle, HEAD, ONE);
            store.write(writeBatch);
            head.incrementAndGet();
            tailIterator.next();
        } catch (RocksDBException e) {
            log.error("Remove head {} failed.", head.get());
            throw new RocksQueueRemoveHeadException(store.getRockdbLocation(), e);
        }
    }

    public void close() {
        cfHandle.close();
        indexCfHandle.close();
        tailIterator.close();

        this.rocksQueueMetric.onClose();
    }

    private String getIndexColumnFamilyName(String queueName) {
        return new StringBuilder()
                .append("_")
                .append(queueName)
                .toString();
    }

    public boolean isEmpty() {
        return tail.get() == 0 ? true : tail.get() <= head.get();
    }

    public long getSize() {
        return tail.get() - head.get();
    }

    public long getHeadIndex() {
        return head.get();
    }

    public long getTailIndex() {
        return tail.get();
    }

    public long approximateSize() {
        return getIndexId(TAIL, 0) - getIndexId(HEAD, 0);
    }

    public String getQueueName() {
        return this.queueName;
    }

    public RocksQueueMetric getRocksQueueMetric() {
        return this.rocksQueueMetric;
    }
}
