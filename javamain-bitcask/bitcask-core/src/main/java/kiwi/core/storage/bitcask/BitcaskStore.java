package kiwi.core.storage.bitcask;

import kiwi.core.common.Bytes;
import kiwi.core.common.KeyValue;
import kiwi.core.common.NamedThreadFactory;
import kiwi.core.config.Options;
import kiwi.core.error.KiwiException;
import kiwi.core.error.KiwiReadException;
import kiwi.core.storage.KeyValueStore;
import kiwi.core.storage.bitcask.log.LogCleaner;
import kiwi.core.storage.bitcask.log.LogSegment;
import kiwi.core.storage.bitcask.log.LogSegmentNameGenerator;
import kiwi.core.storage.bitcask.log.Record;
import kiwi.core.storage.bitcask.log.sync.SegmentWriter;
import kiwi.core.storage.bitcask.log.sync.SegmentWriterFactory;
import kiwi.core.storage.config.StorageConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class BitcaskStore implements KeyValueStore<Bytes, Bytes> {
    private static final Logger logger = LoggerFactory.getLogger(BitcaskStore.class);

    private final KeyDir keyDir;
    private LogSegment activeSegment;
    private final Clock clock;
    private final long logSegmentBytes;
    private final LogSegmentNameGenerator segmentNameGenerator;
    private final LogCleaner logCleaner;
    private final SegmentWriter writer;

    private final ReadWriteLock rw = new ReentrantReadWriteLock();

    private BitcaskStore(
            Path logDir,
            KeyDir keyDir,
            LogSegment activeSegment,
            Clock clock,
            long logSegmentBytes,
            long compactionSegmentMinBytes,
            Duration compactionInterval,
            double minDirtyRatio,
            int compactionThreads,
            SegmentWriterFactory writerFactory) {
        this.keyDir = keyDir;
        this.activeSegment = activeSegment;
        this.clock = clock;
        this.logSegmentBytes = logSegmentBytes;
        this.segmentNameGenerator = LogSegmentNameGenerator.from(activeSegment);

        this.logCleaner = new LogCleaner(
                logDir,
                keyDir,
                activeSegmentSupplier(),
                segmentNameGenerator,
                minDirtyRatio,
                compactionSegmentMinBytes,
                logSegmentBytes,
                compactionThreads);

        this.logCleaner.start(compactionInterval);

        this.writer = writerFactory.create(activeSegmentSupplier());
    }

    public static BitcaskStore open() {
        return open(Options.defaults.storage);
    }

    public static BitcaskStore open(Path logDir) {
        return new Builder(logDir).build();
    }

    public static BitcaskStore open(StorageConfig storageConfig) {
        return new Builder(storageConfig).build();
    }

    @Override
    public Optional<Bytes> get(Bytes key) {
        Objects.requireNonNull(key, "key cannot be null");
        ValueReference valueRef = keyDir.get(key);
        if (valueRef == null) {
            return Optional.empty();
        }
        if (valueRef.isExpired(clock.millis())) {
            keyDir.remove(key);
            return Optional.empty();
        }
        try {
            Bytes valueBytes = valueRef.get();
            if (valueBytes.equals(Record.TOMBSTONE)) {
                return Optional.empty();
            }
            return Optional.of(valueBytes);
        } catch (IOException ex) {
            throw new KiwiException("Failed to read value from active segment " + activeSegment.name(), ex);
        }
    }

    @Override
    public void put(Bytes key, Bytes value) {
        put(key, value, 0L);
    }

    @Override
    public void delete(Bytes key) {
        Objects.requireNonNull(key, "key cannot be null");
        put(key, Record.TOMBSTONE);
    }

    @Override
    public void put(Bytes key, Bytes value, long ttl) {
        Objects.requireNonNull(key, "key cannot be null");
        long now = clock.millis();
        Record record = Record.of(key, value, now, ttl != 0 ? now + ttl : 0);

        rw.readLock().lock();
        try {
            int written = writer.append(record);
            if (written > 0) {
                keyDir.update(record, activeSegment);
            } else {
                throw new KiwiException("Failed to write to segment");
            }
        } finally {
            rw.readLock().unlock();
        }

        maybeRollSegment();
    }

    private void maybeRollSegment() {
        // Optimistic check to avoid acquiring lock.
        if (shouldRoll()) {
            rw.writeLock().lock();
            try {
                // We need to check again after acquiring lock to prevent
                // because multiple threads can enter maybeRollSegment and pass first optimistic check.
                if (shouldRoll()) {
                    activeSegment.markAsReadOnly();
                    activeSegment = LogSegment.open(segmentNameGenerator.next());
                    logger.info("Opened new log segment {}", activeSegment.name());
                }
            } finally {
                rw.writeLock().unlock();
            }
        }
    }

    private boolean shouldRoll() {
        return activeSegment.size() >= logSegmentBytes;
    }

    @Override
    public boolean contains(Bytes key) {
        Objects.requireNonNull(key, "key cannot be null");
        return keyDir.containsKey(key);
    }

    @Override
    public int size() {
        return keyDir.size();
    }

    @Override
    public void purge() {
        keyDir.keys().asIterator().forEachRemaining(this::delete);
    }

    @Override
    public void close() {
        logCleaner.close();
        writer.close();
    }

    private Supplier<LogSegment> activeSegmentSupplier() {
        return () -> activeSegment;
    }

    public static Builder Builder() {
        return new Builder();
    }

    public static Builder Builder(Path logDir) {
        return new Builder(logDir);
    }

    public static Builder Builder(StorageConfig storageConfig) {
        return new Builder(storageConfig);
    }

    public static class Builder {

        private Path logDir;
        private KeyDir keyDir;
        private LogSegment activeSegment;
        private Clock clock = Clock.systemUTC();
        private int keyDirBuilderThreads;
        private long logSegmentBytes;
        private long compactionSegmentMinBytes;
        private Duration compactionInterval;
        private double minDirtyRatio;
        private int compactionThreads;
        private final SegmentWriterFactory writerFactory;

        Builder() {
            this(Options.defaults.storage);
        }

        Builder(Path logDir) {
            this(Options.defaults.storage);
            this.logDir = logDir;
        }

        Builder(StorageConfig config) {
            this.logDir = config.log.dir;
            this.keyDirBuilderThreads = config.log.keyDirBuilderThreads;
            this.logSegmentBytes = config.log.segmentBytes;
            this.compactionSegmentMinBytes = config.log.compaction.segmentMinBytes;
            this.compactionInterval = config.log.compaction.interval;
            this.minDirtyRatio = config.log.compaction.minDirtyRatio;
            this.compactionThreads = config.log.compaction.threads;
            this.writerFactory = new SegmentWriterFactory(config.log.sync);
        }

        public Builder withLogDir(Path logDir) {
            this.logDir = logDir;
            return this;
        }

        public Builder withKeyDirBuilderThreads(int threads) {
            this.keyDirBuilderThreads = threads;
            return this;
        }

        public Builder withLogSegmentBytes(long logSegmentBytes) {
            this.logSegmentBytes = logSegmentBytes;
            return this;
        }

        public Builder withCompactionSegmentMinBytes(long minBytes) {
            this.compactionSegmentMinBytes = minBytes;
            return this;
        }

        public Builder withCompactionInterval(Duration compactionInterval) {
            this.compactionInterval = compactionInterval;
            return this;
        }

        public Builder withMinDirtyRatio(double minDirtyRatio) {
            this.minDirtyRatio = minDirtyRatio;
            return this;
        }

        public Builder withCompactionThreads(int threads) {
            this.compactionThreads = threads;
            return this;
        }

        public Builder withClock(Clock clock) {
            this.clock = clock;
            return this;
        }

        public BitcaskStore build() {
            init(logDir);
            return new BitcaskStore(
                    logDir,
                    keyDir,
                    activeSegment,
                    clock,
                    logSegmentBytes,
                    compactionSegmentMinBytes,
                    compactionInterval,
                    minDirtyRatio,
                    compactionThreads,
                    writerFactory);
        }

        private void init(Path logDir) {
            try {
                Files.createDirectories(logDir);
            } catch (IOException ex) {
                throw new KiwiException("Failed to create log directory " + logDir, ex);
            }

            logger.info("Building keydir from log directory {}", logDir.normalize().toAbsolutePath());

            try (Stream<Path> paths = Files.walk(logDir)) {
                List<Path> segmentPaths = paths.filter(Files::isRegularFile)
                        .filter(path -> path.getFileName().toString().endsWith(".log"))
                        .sorted()
                        .toList();

                ExecutorService executor = Executors.newFixedThreadPool(keyDirBuilderThreads, NamedThreadFactory.create("keydir"));
                List<Future<KeyValue<Path, Map<Bytes, ValueReference>>>> futures = new ArrayList<>();

                for (Path segmentPath : segmentPaths) {
                    futures.add(executor.submit(() -> {
                        LogSegment segment;
                        if (segmentPath.equals(segmentPaths.get(segmentPaths.size() - 1))) { // 按 fileName 排序
                            activeSegment = LogSegment.open(segmentPath);
                            segment = activeSegment;
                        } else {
                            segment = LogSegment.open(segmentPath, true);
                        }
                        Map<Bytes, ValueReference> partialKeyDir = segment.buildKeyDir();
                        return KeyValue.of(segmentPath, partialKeyDir);
                    }));
                }

                // Wait for all tasks to complete
                List<KeyValue<Path, Map<Bytes, ValueReference>>> results = new ArrayList<>();
                for (Future<KeyValue<Path, Map<Bytes, ValueReference>>> future : futures) {
                    results.add(future.get());
                }
                executor.shutdown();

                // Collect all key-value pairs from all segments ordered by segment number.
                // This is necessary to ensure that the latest value for a key is retained.
                keyDir = new KeyDir();

                results.stream()
                        .sorted(Comparator.comparing(KeyValue::key))
                        .map(KeyValue::value)
                        .forEach(partialKeyDir -> {
                            for (Map.Entry<Bytes, ValueReference> entry : partialKeyDir.entrySet()) {
                                Bytes key = entry.getKey();
                                ValueReference value = entry.getValue();
                                if (value == null) {
                                    keyDir.remove(key);
                                } else {
                                    keyDir.put(key, value);
                                }
                            }
                        });

                // Remove tombstones.
                keyDir.values().removeIf(Objects::isNull);

                // Create new active segment when there are no segment files.
                if (activeSegment == null) {
                    Path activeSegmentPath = new LogSegmentNameGenerator(logDir).next();
                    activeSegment = LogSegment.open(activeSegmentPath);
                }
            } catch (IOException | InterruptedException | ExecutionException ex) {
                throw new KiwiReadException("Failed to read log directory " + logDir, ex);
            }

            logger.info("Store initialized with {} entries and {} active log segment", keyDir.size(), activeSegment.name());
        }
    }
}