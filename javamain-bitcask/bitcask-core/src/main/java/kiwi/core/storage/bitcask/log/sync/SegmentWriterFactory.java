package kiwi.core.storage.bitcask.log.sync;

import kiwi.core.storage.bitcask.log.LogSegment;
import kiwi.core.storage.bitcask.log.config.LogConfig;

import java.util.function.Supplier;

public class SegmentWriterFactory {
    private final LogConfig.Sync config;

    public SegmentWriterFactory(LogConfig.Sync config) {
        this.config = config;
    }

    public SegmentWriter create(Supplier<LogSegment> activeSegmentSupplier) {
        return switch (config.mode) {
            case PERIODIC -> new PeriodicSegmentWriter(activeSegmentSupplier, config.interval);
            case BATCH -> new BatchSegmentWriter(activeSegmentSupplier, config.window);
            case LAZY -> new LazySegmentWriter(activeSegmentSupplier);
        };
    }
}
