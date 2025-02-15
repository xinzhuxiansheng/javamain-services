package kiwi.core.storage.bitcask.log;


import kiwi.core.common.Bytes;
import kiwi.core.common.NamedThreadFactory;
import kiwi.core.error.KiwiReadException;
import kiwi.core.storage.Utils;
import kiwi.core.storage.bitcask.KeyDir;
import kiwi.core.storage.bitcask.ValueReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogCleaner implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(LogCleaner.class);

    private static final double JITTER = 0.3;

    private final Path logDir;
    private final KeyDir keyDir;
    private final Supplier<LogSegment> activeSegmentSupplier;
    private final LogSegmentNameGenerator segmentNameGenerator;
    private final double minDirtyRatio;
    private final long compactionSegmentMinBytes;
    private final long logSegmentBytes;
    private final int threads;
    private final ScheduledExecutorService scheduler;


    public LogCleaner(
            Path logDir,
            KeyDir keyDir,
            Supplier<LogSegment> activeSegmentSupplier,
            LogSegmentNameGenerator segmentNameGenerator,
            double minDirtyRatio,
            long compactionSegmentMinBytes,
            long logSegmentBytes,
            int threads) {
        this.logDir = logDir;
        this.keyDir = keyDir;
        this.activeSegmentSupplier = activeSegmentSupplier;
        this.segmentNameGenerator = segmentNameGenerator;
        this.minDirtyRatio = minDirtyRatio;
        this.compactionSegmentMinBytes = compactionSegmentMinBytes;
        this.logSegmentBytes = logSegmentBytes;
        this.threads = threads;

        this.scheduler = Executors.newSingleThreadScheduledExecutor(NamedThreadFactory.create("cleaner"));

        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    public void start(Duration interval) {
        if (interval.isZero()) {
            logger.info("Log cleaner is disabled");
            return;
        }

        // Add some jitter to prevent all log cleaners from running at the same time.
        long compactIntervalSeconds = intervalWithJitterSeconds(interval);
        long cleanIntervalSeconds = intervalWithJitterSeconds(interval);

        scheduler.scheduleAtFixedRate(this::compactLog, compactIntervalSeconds, compactIntervalSeconds, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(this::cleanLog, 0, cleanIntervalSeconds, TimeUnit.SECONDS);
    }

    private long intervalWithJitterSeconds(Duration interval) {
        long jitter = (long) (interval.toSeconds() * JITTER);
        // Shift the interval by a random amount between -jitter and +jitter.
        return interval.toSeconds() + (long) ((Math.random() - 0.5) * 2 * jitter);
    }

    void compactLog() {
        logger.info("Log compaction started");

        Map<Bytes, Long> keyTimestampMap = buildKeyTimestampMap();
        List<LogSegment> dirtySegments = findDirtySegments(keyTimestampMap);

        if (dirtySegments.isEmpty()) {
            logger.info("No dirty segments found");
            return;
        }

        List<HintSegment> hintSegments = new ArrayList<>();
        LogSegment newLogSegment = null;
        HintSegment newHintSegment = null;

        for (LogSegment dirtySegment : dirtySegments) {
            for (Record record : dirtySegment.getActiveRecords(keyTimestampMap)) {
                if (newLogSegment == null || newLogSegment.size() >= logSegmentBytes) {
                    // When new segment is full, fsync and close log and hint channels.
                    if (newLogSegment != null) {
                        newLogSegment.close();
                        newHintSegment.close();
                    }

                    Path logFile = segmentNameGenerator.next();
                    newLogSegment = LogSegment.open(logFile);

                    Path hintFile = logFile.resolveSibling(newLogSegment.name() + HintSegment.PARTIAL_EXTENSION);
                    newHintSegment = HintSegment.open(hintFile);
                    hintSegments.add(newHintSegment);

                    logger.info("Opened new compacted log segment {}", newLogSegment.name());
                }

                newLogSegment.append(record);

                long valuePosition = newLogSegment.position() - record.valueSize();
                newHintSegment.append(new Hint(record.header(), valuePosition, record.key()));

                // Prevent keydir from being updated with stale values.
                ValueReference currentValue = keyDir.get(record.key());
                if (currentValue != null && currentValue.timestamp() <= record.header().timestamp()) {
                    keyDir.update(record, newLogSegment);
                }
            }
        }

        if (newLogSegment != null) {
            newLogSegment.close();
            newHintSegment.close();
        }

        for (LogSegment dirtySegment : dirtySegments) {
            // Hint files are first marked as deleted before log files are deleted to prevent data loss.
            // If process fails after hint file is marked as deleted but before log file is deleted,
            // data can be recovered.
            String hintFileName = dirtySegment.name() + HintSegment.EXTENSION;
            Path hintFile = dirtySegment.file().resolveSibling(hintFileName);
            if (Files.exists(hintFile)) {
                Path deletedHintFile = hintFile.resolveSibling(hintFileName + ".deleted");
                Utils.renameFile(hintFile, deletedHintFile);
            }

            dirtySegment.markAsDeleted();
        }

        for (HintSegment hintSegment : hintSegments) {
            hintSegment.commit();
        }

        logger.info("Log compaction ended");
    }

    void cleanLog() {
        try (Stream<Path> paths = Files.walk(logDir)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".deleted"))
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                            logger.info("Deleted marked segment {}", path);
                        } catch (IOException ex) {
                            logger.warn("Failed to delete segments", ex);
                        }
                    });
        } catch (IOException ex) {
            logger.warn("Failed to clean dirty segments", ex);
        }
    }


    private Map<Bytes, Long> buildKeyTimestampMap() {
        return keyDir.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().timestamp()));
    }

    private List<LogSegment> findDirtySegments(Map<Bytes, Long> keyTimestampMap) {
        ExecutorService executor = Executors.newFixedThreadPool(threads, NamedThreadFactory.create("compaction"));

        List<LogSegment> dirtySegments = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(logDir)) {
            dirtySegments = paths.filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".log"))
                    .filter(path -> !activeSegmentSupplier.get().isSamePath(path))
                    .map(path -> executor.submit(() -> {
                        try {
                            LogSegment segment = LogSegment.open(path, true);
                            double ratio = segment.dirtyRatio(keyTimestampMap);
                            if (ratio >= minDirtyRatio) {
                                logger.info("Found segment {} with dirty ratio {}", segment.name(), String.format("%.4f", ratio));
                                return segment;
                            } else if (segment.size() < compactionSegmentMinBytes) {
                                // Compact empty or almost empty segments.
                                logger.info("Found small segment {} with {} bytes", segment.name(), segment.size());
                                return segment;
                            } else {
                                return null;
                            }
                        } catch (KiwiReadException ex) {
                            logger.warn("Failed to read log segment {}", path, ex);
                            return null;
                        }
                    }))
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException ex) {
                            logger.error("Failed to mark dirty segments", ex);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .sorted(Comparator.comparing(LogSegment::name))
                    .toList();
        } catch (IOException ex) {
            logger.error("Failed to mark dirty segments", ex);
        }

        executor.shutdown();

        // Prevents infinite compaction loop when only one dirty segment is found.
        if (dirtySegments.size() == 1 && dirtySegments.stream().findFirst().get().size() < compactionSegmentMinBytes) {
            logger.info("Single dirty segment found with {} bytes. Skipping compaction.", dirtySegments.stream().findFirst().get().size());
            dirtySegments = Collections.emptyList();
        }

        return dirtySegments;
    }

    @Override
    public void close() {
        logger.info("Shutting down log cleaner");
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(3, TimeUnit.MINUTES)) {
                logger.warn("Log cleaner did not shutdown in time. Forcing shutdown.");
                scheduler.shutdownNow();
            }
        } catch (InterruptedException ex) {
            logger.error("Failed to shutdown log cleaner", ex);
            scheduler.shutdownNow();
        }
    }
}
