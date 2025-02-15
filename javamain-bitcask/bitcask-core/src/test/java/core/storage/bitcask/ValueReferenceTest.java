package core.storage.bitcask;

import kiwi.core.storage.bitcask.ValueReference;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValueReferenceTest {

    @Test
    void testIsExpiredNoTTL() {
        ValueReference valueReference = new ValueReference(null, 0, 0, 0, 0);
        assertFalse(valueReference.isExpired(1));
    }

    @Test
    void testIsExpiredWithTTL() {
        ValueReference valueReference = new ValueReference(null, 0, 0, 1, 0);
        assertTrue(valueReference.isExpired(2));
    }

}