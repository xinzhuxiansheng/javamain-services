package core.storage.bitcask.log;

import kiwi.core.common.Bytes;
import kiwi.core.storage.bitcask.Header;
import kiwi.core.storage.bitcask.log.Record;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecordTest {

    @Test
    void testIsValidChecksumWithValidHeader() {
        Record record = Record.of(Bytes.wrap("key"), Bytes.wrap("value"));
        assertTrue(record.isValidChecksum());
    }

    @Test
    void testIsValidChecksumWithInvalidHeader() {
        Header header = new Header(0, 0, 0, 0, 0);
        Record record = new Record(header, Bytes.wrap("key"), Bytes.wrap("value"));
        assertFalse(record.isValidChecksum());
    }

    @Test
    void testIsTombstone() {
        assertTrue(Record.of(Bytes.wrap("k"), Bytes.wrap(new byte[0])).isTombstone());
        assertFalse(Record.of(Bytes.wrap("k"), Bytes.wrap("v")).isTombstone());
    }

}