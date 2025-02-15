package core.common;

import kiwi.core.common.Bytes;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BytesTest {

    @Test
    void testHashCode() {
        Bytes a = Bytes.wrap(new byte[]{1, 2, 3});
        Bytes b = Bytes.wrap(new byte[]{1, 2, 3});
        Bytes c = Bytes.wrap(new byte[]{1, 2, 3, 4});
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a.hashCode(), c.hashCode());
    }

    @Test
    void testEquals() {
        Bytes a = Bytes.wrap(new byte[]{1, 2, 3});
        Bytes b = Bytes.wrap(new byte[]{1, 2, 3});
        Bytes c = Bytes.wrap(new byte[]{1, 2, 3, 4});
        assertEquals(a, b);
        assertNotEquals(a, c);
    }

    @Test
    void testToStringWithPrintableChars() {
        Bytes bytes = Bytes.wrap("test".getBytes());
        assertEquals("test", bytes.toString());
    }

    @Test
    void testToStringWithNonPrintableChars() {
        Bytes bytes = Bytes.wrap(new byte[]{116, 101, 115, 116, 45, 1, 2, 3});
        assertEquals("test-\\x01\\x02\\x03", bytes.toString());
    }

}