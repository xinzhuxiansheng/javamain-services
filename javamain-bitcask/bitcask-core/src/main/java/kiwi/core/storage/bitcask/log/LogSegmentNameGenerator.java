package kiwi.core.storage.bitcask.log;

import java.nio.file.Path;
import java.nio.file.Paths;

public class LogSegmentNameGenerator {
    private final Path dir;
    private long counter;

    public LogSegmentNameGenerator() {
        this(Paths.get("."), 0);
    }

    public LogSegmentNameGenerator(long counter) {
        this(Paths.get("."), counter);
    }

    public LogSegmentNameGenerator(Path logDir) {
        this(logDir, 0);
    }

    public LogSegmentNameGenerator(Path dir, long counter) {
        this.dir = dir;
        this.counter = counter;
    }

    public static LogSegmentNameGenerator from(LogSegment segment) {
        Path dir = segment.baseDir();
        long counter = Long.parseLong(segment.name()) + 1;
        return new LogSegmentNameGenerator(dir, counter);
    }

    public synchronized Path next() {
        // Max number of segments is 64-bit unsigned long.
        return dir.resolve(String.format("%020d.log", counter++));
    }
}
