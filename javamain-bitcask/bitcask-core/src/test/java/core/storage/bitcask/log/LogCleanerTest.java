package core.storage.bitcask.log;

import kiwi.core.LogSegmentSupport;
import kiwi.core.common.Bytes;
import kiwi.core.storage.bitcask.BitcaskStore;
import kiwi.core.storage.bitcask.Header;
import kiwi.core.storage.bitcask.KeyDir;
import kiwi.core.storage.bitcask.log.Record;
import kiwi.core.storage.bitcask.log.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class LogCleanerTest extends LogSegmentSupport {

    @Test
    void testCompactAndClean() throws IOException {
        writeRecords(
                "00000000000000000000.log",
                List.of(
                        Record.of(Bytes.wrap("k1"), Bytes.wrap("v1"), 0),
                        Record.of(Bytes.wrap("k2"), Bytes.wrap("v2"), 0)
                ));
        LogSegment segment000 = LogSegment.open(root.resolve("00000000000000000000.log"));

        // This hint file will be deleted after compaction.
        writeHints(
                "00000000000000000000.hint",
                List.of(
                        new Hint(new Header(0L, 0L, 0L, 2, 2), Header.BYTES + 2, Bytes.wrap("k1")),
                        new Hint(new Header(0L, 0L, 0L, 2, 2), 2 * (Header.BYTES + 2) + 2, Bytes.wrap("k2"))
                ));

        writeRecords(
                "00000000000000000001.log",
                List.of(
                        Record.of(Bytes.wrap("k2"), Bytes.wrap("vu"), 1),
                        Record.of(Bytes.wrap("k3"), Bytes.wrap("v3"), 1)
                ));
        LogSegment segment001 = LogSegment.open(root.resolve("00000000000000000001.log"));

        writeRecords(
                "00000000000000000002.log",
                List.of(
                        Record.of(Bytes.wrap("k3"), Bytes.wrap("vu"), 2),
                        Record.of(Bytes.wrap("k4"), Bytes.wrap("v4"), 2)
                ));
        LogSegment segment002 = LogSegment.open(root.resolve("00000000000000000002.log"));

        KeyDir keyDir = new KeyDir();
        keyDir.update(Record.of(Bytes.wrap("k1"), Bytes.wrap("v1"), 0), segment000);
        keyDir.update(Record.of(Bytes.wrap("k2"), Bytes.wrap("vu"), 1), segment001);
        keyDir.update(Record.of(Bytes.wrap("k3"), Bytes.wrap("vu"), 2), segment002);
        keyDir.update(Record.of(Bytes.wrap("k4"), Bytes.wrap("v4"), 2), segment002);

        LogSegmentNameGenerator nameGenerator = LogSegmentNameGenerator.from(segment002);
        Supplier<LogSegment> activeSegmentSupplier = () -> segment002;

        int segmentBytes = 2 * (Header.BYTES + 4); // 2 records per segment file.
        LogCleaner cleaner = new LogCleaner(
                root,
                keyDir,
                activeSegmentSupplier,
                nameGenerator,
                0.25,
                0,
                segmentBytes,
                1
        );

        cleaner.compactLog();

        // Dirty segments are marked for deletion.
        assertFalse(Files.exists(root.resolve("00000000000000000000.log")));
        assertTrue(Files.exists(root.resolve("00000000000000000000.log.deleted")));
        assertTrue(Files.exists(root.resolve("00000000000000000000.hint.deleted")));
        assertFalse(Files.exists(root.resolve("00000000000000000001.log")));
        assertTrue(Files.exists(root.resolve("00000000000000000001.log.deleted")));

        // Active segment is not deleted.
        assertTrue(Files.exists(root.resolve("00000000000000000002.log")));
        assertFalse(Files.exists(root.resolve("00000000000000000002.log.deleted")));

        // New compacted segments are created.
        assertTrue(Files.exists(root.resolve("00000000000000000003.log")));

        // Hint files are created.
        assertTrue(Files.exists(root.resolve("00000000000000000003.hint")));

        // Store is built from compacted segments and hint files.
        BitcaskStore store = BitcaskStore.Builder(root)
                .withLogSegmentBytes(segmentBytes)
                .withCompactionInterval(Duration.ZERO) // Disables automatic compaction.
                .build();

        assertEquals(Bytes.wrap("v1"), store.get(Bytes.wrap("k1")).orElseThrow());
        assertEquals(Bytes.wrap("vu"), store.get(Bytes.wrap("k2")).orElseThrow());
        assertEquals(Bytes.wrap("vu"), store.get(Bytes.wrap("k3")).orElseThrow());
        assertEquals(Bytes.wrap("v4"), store.get(Bytes.wrap("k4")).orElseThrow());

        // Clean up deleted segments.
        cleaner.cleanLog();

        // Deleted segments are removed.
        assertFalse(Files.exists(root.resolve("00000000000000000000.log.deleted")));
        assertFalse(Files.exists(root.resolve("00000000000000000000.hint.deleted")));
        assertFalse(Files.exists(root.resolve("00000000000000000001.log.deleted")));

        // There are no missing entries.
        assertEquals(Bytes.wrap("v1"), store.get(Bytes.wrap("k1")).orElseThrow());
        assertEquals(Bytes.wrap("vu"), store.get(Bytes.wrap("k2")).orElseThrow());
        assertEquals(Bytes.wrap("vu"), store.get(Bytes.wrap("k3")).orElseThrow());
        assertEquals(Bytes.wrap("v4"), store.get(Bytes.wrap("k4")).orElseThrow());
    }
}