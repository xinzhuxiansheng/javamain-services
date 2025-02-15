package kiwi.core.storage.bitcask;

import kiwi.core.common.Bytes;
import kiwi.core.storage.bitcask.log.LogSegment;
import kiwi.core.storage.bitcask.log.Record;

import java.io.IOException;
import java.nio.ByteBuffer;

public record ValueReference(LogSegment segment, long position, int valueSize, long ttl, long timestamp) {

    public static ValueReference of(LogSegment segment, long position, Record record) {
        return new ValueReference(
                segment,
                position,
                record.valueSize(),
                record.header().ttl(),
                record.header().timestamp()
        );
    }

    public Bytes get() throws IOException {
        ByteBuffer buffer = segment.read(position, valueSize);
        return Bytes.wrap(buffer.array());
    }

    public boolean isExpired(long now) {
        return ttl > 0 && now > ttl;
    }
}
