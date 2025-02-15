package kiwi.core.storage;

import kiwi.core.common.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.CRC32;

public class Utils {
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public static long checksum(long timestamp, long ttl, Bytes key, Bytes value) {
        ByteBuffer buffer = ByteBuffer.allocate(2 * Long.BYTES + 2 * Integer.BYTES + key.size() + value.size());
        buffer.putLong(timestamp);
        buffer.putLong(ttl);
        buffer.putInt(key.size());
        buffer.putInt(value.size());
        buffer.put(key.get());
        buffer.put(value.get());
        CRC32 crc = new CRC32();
        crc.update(buffer.array());
        return crc.getValue();
    }

    public static void renameFile(Path from, Path to) {
        try {
            Files.move(from, to, StandardCopyOption.ATOMIC_MOVE); // StandardCopyOption.ATOMIC_MOVE 保证移动的原子性
        } catch (AtomicMoveNotSupportedException e) {
            logger.warn("Atomic move not supported, falling back to non-atomic move for {}", from);
            try {
                Files.move(from, to);
            } catch (IOException ex) {
                logger.error("Failed to rename file {}", from, ex);
            }
        } catch (IOException ex) {
            logger.error("Failed to rename file {}", from, ex);
        }
    }
}
