package kiwi.core.storage.bitcask;

import kiwi.core.common.Bytes;
import kiwi.core.storage.bitcask.log.LogSegment;
import kiwi.core.storage.bitcask.log.Record;

import java.util.concurrent.ConcurrentHashMap;

public class KeyDir extends ConcurrentHashMap<Bytes, ValueReference> {

    public void update(Record record, LogSegment segment) {
        if (record.isTombstone()) { // 表示删除
            super.remove(record.key());
        } else {
            long position = segment.position() - record.valueSize();
            ValueReference valueRef = ValueReference.of(segment, position, record);
            super.put(record.key(), valueRef);
        }
    }
}
