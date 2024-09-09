import org.junit.Before;
import org.junit.Test;

public class ByteArrayTest {
    byte[] header;

    @Before
    public void initHeader(){
        header = new byte[]{(byte) 0b11111111, (byte) 0b11111111};
    }

    @Test
    public void set_1_3_slot(){
        markSlotUsed(10,false);
        System.out.println("Header after set 10th bit: " + String.format("%8s", Integer.toBinaryString(header[1] & 0xFF)).replace(' ', '0'));
    }


    private void markSlotUsed(int i, boolean value) {
        int headerbit = i % 8;
        int headerbyte = (i - headerbit) / 8;

        if(value)
            header[headerbyte] |= 1 << headerbit;
        else
            header[headerbyte] &= (0xFF ^ (1 << headerbit));
    }
}
