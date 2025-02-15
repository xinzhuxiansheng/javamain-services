package core.storage.bitcask.log;

import kiwi.core.common.Bytes;
import kiwi.core.error.KiwiWriteException;
import kiwi.core.storage.bitcask.Header;
import kiwi.core.storage.bitcask.log.Hint;
import kiwi.core.storage.bitcask.log.HintSegment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class HintSegmentTest {

    @TempDir
    Path root;

    @Test
    void testOpenAsReadOnlyThrowsOnAppend() throws IOException {
        Path file = root.resolve("001.hint");
        Files.createFile(file);

        HintSegment segment = HintSegment.open(file, true);

        assertThrows(KiwiWriteException.class, () -> {
            Hint hint = new Hint(new Header(0L, 0L, 0L, 2, 2), Header.BYTES + 2, Bytes.wrap("k1"));
            segment.append(hint);
        });
    }

    @Test
    void testOpenAsAppendOnlyCreatesSegmentFile() {
        HintSegment.open(root.resolve("001.hint"));
        assertTrue(Files.exists(root.resolve("001.hint")));
    }

    @Test
    void testCommitRenamesHintFile() {
        HintSegment segment = HintSegment.open(root.resolve("001.hint.partial"));
        segment.commit();
        assertTrue(Files.exists(root.resolve("001.hint")));
        assertFalse(Files.exists(root.resolve("001.hint.partial")));
    }

    @Test
    void testCommitIsIdempotent() {
        HintSegment segment = HintSegment.open(root.resolve("001.hint"));
        segment.commit();
        assertTrue(Files.exists(root.resolve("001.hint")));
    }

    @Test
    void testGetHintsIterator() {
        Hint first = new Hint(new Header(0L, 0L, 0L, 2, 2), Header.BYTES + 2, Bytes.wrap("k1"));
        Hint second = new Hint(new Header(0L, 0L, 0L, 2, 2), 2 * (Header.BYTES + 2) + 2, Bytes.wrap("k2"));

        HintSegment writer = HintSegment.open(root.resolve("001.hint"));
        writer.append(first);
        writer.append(second);
        writer.commit();

        HintSegment reader = HintSegment.open(root.resolve("001.hint"), true);
        Iterator<Hint> hints = reader.getHints().iterator();

        assertTrue(hints.hasNext());
        assertEquals(first, hints.next());
        assertTrue(hints.hasNext());
        assertEquals(second, hints.next());
        assertFalse(hints.hasNext());
    }
}