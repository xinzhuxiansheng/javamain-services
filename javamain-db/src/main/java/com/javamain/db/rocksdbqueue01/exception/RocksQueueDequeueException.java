package com.javamain.db.rocksdbqueue01.exception;

public class RocksQueueDequeueException extends RocksQueueException {
    public RocksQueueDequeueException(String rocksDBLocation, Throwable cause) {
        super("Rocks queue dequeue exception, please check rocksdb located in " + rocksDBLocation, cause);
    }
}
