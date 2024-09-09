import org.junit.Before;
import org.junit.Test;

/**
 * 当 header = 0b00000000，
 * 现在将 0b 去掉，保留8个0： 00000000，从右往左开始数，0位，1位，2位 ... 7位
 * 每位上的 0、1 表示 不同含义
 */
public class ByteTest {
    byte header;

    @Before
    public void initHeader() {
        header = 0b00000000; // 初始时所有槽位都未被使用
    }

    /**
     * 那现在设置 0位 为1
     */
    @Test
    public void set_0_slot() {
        header = setBit(header, 0);
        System.out.println("Header after set 0th bit: " + Integer.toBinaryString(header));

        header = clearBit(header,0);
        System.out.println("Header after clear 0th bit: " + Integer.toBinaryString(header));
    }

    /*
        0
        1 << 0 :
        value: 仅限制，1 或者 0
     */
    private byte setBit(byte header, int bitIndex) {
        return (byte) (header | (1 << bitIndex));
    }

    public static byte clearBit(byte header, int bitIndex) {
        return (byte) (header & ~(1 << bitIndex));
    }
}
