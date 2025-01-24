package com.javamain.db.rocksdbqueue01.exception;

public class RocksQueueEnqueueException extends RocksQueueException {
    public RocksQueueEnqueueException(String rocksDBLocation, Throwable cause) {
        super("Rocks queue enqueue exception, please check rocksdb located in " + rocksDBLocation, cause);
    }
}
