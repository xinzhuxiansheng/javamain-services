package kiwi.core.storage.bitcask.log.sync;

import kiwi.core.storage.bitcask.log.LogSegment;

import java.util.function.Supplier;

/**
 * A {@link SegmentWriter} that appends records to the active segment and defers
 * fsync to operating system. This is useful for performance sensitive applications but
 * may result in data loss in case of a crash.
 */
public class LazySegmentWriter extends SegmentWriter {
    public LazySegmentWriter(Supplier<LogSegment> activeSegmentSupplier) {
        super(activeSegmentSupplier);
    }
}
