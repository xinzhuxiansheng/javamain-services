import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HeapPageTest {
    // 假定这个HeapPage类包含isSlotUsed和header

    private class HeapPage {
        private final byte[] header;

        public HeapPage(byte[] header) {
            this.header = header;
        }

        public boolean isSlotUsed(int i) {
            int byteIndex = i / 8;
            int bitIndex = i % 8;
            int flag = (header[byteIndex] >> bitIndex) & 1;
            return flag == 1;
        }
    }

    @Test
    public void testIsSlotUsed() {
        // 初始化header数组
        byte[] mockHeader = {(byte)0b11111111, (byte)0b11111111, (byte)0b00000011};
        HeapPage heapPage = new HeapPage(mockHeader);

        // 检查前18个槽位是否被使用
        for (int i = 0; i <= 17; i++) {
            assertTrue("Slot " + i + " should be used.", heapPage.isSlotUsed(i));
        }

        // 检查第18个槽位之后的是否未被使用
        for (int i = 18; i < mockHeader.length * 8; i++) {
            assertFalse("Slot " + i + " should not be used.", heapPage.isSlotUsed(i));
        }
    }
}
