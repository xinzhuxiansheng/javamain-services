package kiwi.core.storage.bitcask.log.config;

import com.typesafe.config.Config;

import java.nio.file.Path;
import java.time.Duration;

public class LogConfig {
    public final Path dir;
    public final long segmentBytes;
    public final int keyDirBuilderThreads;
    public final Sync sync;
    public final Compaction compaction;

    public LogConfig(Config config) {
        this.dir = Path.of(config.getString("dir"));
        this.segmentBytes = config.getLong("segment.bytes");
        this.keyDirBuilderThreads = config.getInt("keydir.builder.threads");
        this.sync = new Sync(config.getConfig("sync"));
        this.compaction = new Compaction(config.getConfig("compaction"));
    }

    public static class Sync {
        public enum Mode {
            PERIODIC, BATCH, LAZY
        }

        public final Mode mode;
        public final Duration interval;
        public final Duration window;

        public Sync(Config config) {
            String mode = config.getString("mode").toUpperCase();
            try {
                this.mode = Mode.valueOf(mode);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid sync mode: " + mode);
            }

            this.interval = config.getDuration("periodic.interval");
            this.window = config.getDuration("batch.window");
        }
    }

    public static class Compaction {
        public final Duration interval;
        public final double minDirtyRatio;
        public final long segmentMinBytes;
        public final int threads;

        public Compaction(Config config) {
            this.interval = config.getDuration("interval");
            this.minDirtyRatio = config.getDouble("min.dirty.ratio");
            this.segmentMinBytes = config.getLong("segment.min.bytes");
            this.threads = config.getInt("threads");
        }
    }
}
