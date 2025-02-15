package kiwi.core.common;

import net.openhft.hashing.LongHashFunction;

import java.util.Arrays;
import java.util.Objects;

public class Bytes {
    private static final LongHashFunction XX_HASH = LongHashFunction.xx();

    public static final Bytes EMPTY = new Bytes(new byte[0]);

    private static final char[] HEX_CHARS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private final byte[] bytes;
    private final int hashCode;

    public Bytes(byte[] bytes) {
        this.bytes = bytes;
        this.hashCode = calculateHashCode(bytes);
    }

    private int calculateHashCode(byte[] value) {
        if (value == null) {
            return 0;
        }
        // XX_HASH.hashBytes(value) 返回的是 long 类型的哈希值，而 Java 的 hashCode() 方法要求返回 int 类型。因此，方法的最后一步是将 long 类型的哈希值转换为 int 类型。
        long hash = XX_HASH.hashBytes(value);
        return Long.hashCode(hash);
    }

    /**
     * 将 bytes[] 数组包装成 Bytes 对象
     */
    public static Bytes wrap(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return new Bytes(bytes);
    }

    public byte[] get() {
        return this.bytes;
    }

    public int size() {
        return bytes.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o instanceof Bytes) {
            return Arrays.equals(this.bytes, ((Bytes) o).get());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        if (bytes == null)
            return result.toString();

        for (byte b : bytes) {
            int ch = b & 0xFF;
            if (ch >= ' ' && ch <= '~' && ch != '\\') {
                result.append((char) ch);
            } else {
                result.append("\\x");
                result.append(HEX_CHARS_UPPER[ch / 0x10]);
                result.append(HEX_CHARS_UPPER[ch % 0x10]);
            }
        }
        return result.toString();
    }
}
