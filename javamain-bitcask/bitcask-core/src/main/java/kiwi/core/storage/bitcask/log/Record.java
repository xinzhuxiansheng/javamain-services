package kiwi.core.storage.bitcask.log;

import kiwi.core.common.Bytes;
import kiwi.core.storage.Utils;
import kiwi.core.storage.bitcask.Header;

import java.nio.ByteBuffer;

public record Record(Header header, Bytes key, Bytes value) {

    /**
     * Tombstone 用于标记删除
     */
    public static final Bytes TOMBSTONE = Bytes.EMPTY;

    public static Record of(Bytes key, Bytes value) {
        return Record.of(key, value, 0L, 0L);
    }

    public static Record of(Bytes key, Bytes value, long timestamp) {
        return Record.of(key, value, timestamp, 0L);
    }

    public static Record of(Bytes key, Bytes value, long timestamp, long ttl) {
        long checksum = Utils.checksum(timestamp, ttl, key, value);
        Header header = new Header(checksum, timestamp, ttl, key.size(), value.size());
        return new Record(header, key, value);
    }

    public int keySize() {
        return key.size();
    }

    public int valueSize() {
        return value.size();
    }

    public int size() {
        return Header.BYTES + keySize() + valueSize();
    }

    public ByteBuffer toByteBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(size());
        buffer.put(header.toByteBuffer());
        buffer.put(key.get());
        buffer.put(value.get());
        buffer.rewind(); // 设置 position = 0
        return buffer;
    }

    public boolean isValidChecksum() {
        return header.checksum() == Utils.checksum(header.timestamp(), header.ttl(), key, value);
    }

    public boolean isTombstone() {
        return value.equals(TOMBSTONE);
    }
}
