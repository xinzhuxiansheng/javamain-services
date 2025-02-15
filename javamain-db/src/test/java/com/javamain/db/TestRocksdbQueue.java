package com.javamain.db;

import com.javamain.db.rocksdbqueue01.QueueItem;
import com.javamain.db.rocksdbqueue01.RocksQueue;
import com.javamain.db.rocksdbqueue01.RocksStore;
import com.javamain.db.rocksdbqueue01.StoreOptions;
import com.javamain.db.rocksdbqueue01.exception.RocksQueueException;
import org.junit.jupiter.api.Test;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TestRocksdbQueue {
    private static List<String> dbs = new ArrayList<>();
    private static AtomicInteger queue_index = new AtomicInteger(-1);

    @Test
    public void testRocksDBWrite() throws RocksDBException, InterruptedException {
        try (final Options options = new Options().setCreateIfMissing(true)) {
            try (final RocksDB db = RocksDB.open(options, "E:\\Code\\Java\\javamain-services\\broker\\rocksdb-data01")) {
                byte[] key = "key".getBytes();
                byte[] value = "value".getBytes();
                db.put(key, value);

                byte[] result = db.get(key);
                System.out.println("write: " + new String(result));
            }
        }
    }

    @Test
    public void testRocksDBRead() throws RocksDBException, InterruptedException {
        try (final Options options = new Options().setCreateIfMissing(true)) {
            try (final RocksDB db = RocksDB.open(options, "E:\\Code\\Java\\javamain-services\\broker\\rocksdb-data01")) {
                byte[] key = "key".getBytes();
                byte[] result = db.get(key);
                System.out.println("read: " + new String(result));
            }
        }
    }

    @Test
    public void testRocksDBQueueEnqueue() throws RocksQueueException {
        StoreOptions storeOptions = StoreOptions.builder()
                .directory("E:\\Code\\Java\\javamain-services\\broker\\rocksdb-data03")
                .database("rocks_db").build();

        RocksStore rocksStore = new RocksStore(storeOptions);
        RocksQueue queue = rocksStore.createQueue(generateQueueName());
        byte[] value01 = "name01".getBytes();
        byte[] value03 = "name03".getBytes();
        byte[] value02 = "name02".getBytes();
        queue.enqueue(value01);
        queue.enqueue(value03);
        queue.enqueue(value02);
    }

    @Test
    public void testRocksDBQueueDequeue() throws RocksQueueException {
        StoreOptions storeOptions = StoreOptions.builder()
                .directory("E:\\Code\\Java\\javamain-services\\broker\\rocksdb-data03")
                .database("rocks_db").build();

        RocksStore rocksStore = new RocksStore(storeOptions);
        RocksQueue queue = rocksStore.createQueue(generateQueueName());
        QueueItem queueItem01 = queue.dequeue();
        System.out.println("index: " + queueItem01.getIndex() + " , value: " + new String(queueItem01.getValue()));

        QueueItem queueItem02 = queue.dequeue();
        System.out.println("index: " + queueItem02.getIndex() + " , value: " + new String(queueItem02.getValue()));

        QueueItem queueItem03 = queue.dequeue();
        System.out.println("index: " + queueItem03.getIndex() + " , value: " + new String(queueItem03.getValue()));
    }

    protected static String generateQueueName() {
        String queueName = new StringBuilder()
                .append("rocks_queue")
                .append("_")
                .append(queue_index.incrementAndGet())
                .toString();
        System.out.println("Generate a queue name " + queueName);
        return queueName;
    }

}
