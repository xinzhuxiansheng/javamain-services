package core.storage.bitcask.log;

import kiwi.core.storage.bitcask.log.LogSegment;
import kiwi.core.storage.bitcask.log.LogSegmentNameGenerator;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LogSegmentNameGeneratorTest {

    @Test
    void testNextReturnsIncreasingFileNames() {
        LogSegmentNameGenerator logSegmentNameGenerator = new LogSegmentNameGenerator();

        assertEquals(
                "./00000000000000000000.log", logSegmentNameGenerator.next().toString());
        assertEquals(
                "./00000000000000000001.log", logSegmentNameGenerator.next().toString());
    }

    @Test
    void testNextContinuesFromGivenCounter() {
        LogSegmentNameGenerator logSegmentNameGenerator = new LogSegmentNameGenerator(42);

        assertEquals(
                "./00000000000000000042.log", logSegmentNameGenerator.next().toString());
        assertEquals(
                "./00000000000000000043.log", logSegmentNameGenerator.next().toString());
    }

    @Test
    void testFromReturnsGeneratorWithCounterFromFileName() {
        LogSegment segment = new LogSegment(Paths.get("/tmp/log/00000000000000000000.log"), null);
        LogSegmentNameGenerator logSegmentNameGenerator = LogSegmentNameGenerator.from(segment);

        assertEquals(
                "/tmp/log/00000000000000000001.log", logSegmentNameGenerator.next().toString());
        assertEquals(
                "/tmp/log/00000000000000000002.log", logSegmentNameGenerator.next().toString());
    }
}