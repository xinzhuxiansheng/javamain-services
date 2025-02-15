package kiwi.core.storage.bitcask.log;

import kiwi.core.common.Bytes;
import kiwi.core.error.KiwiException;
import kiwi.core.error.KiwiReadException;
import kiwi.core.error.KiwiWriteException;
import kiwi.core.storage.Utils;
import kiwi.core.storage.bitcask.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HintSegment {
    private static final Logger logger = LoggerFactory.getLogger(HintSegment.class);

    public static String EXTENSION = ".hint";
    public static String PARTIAL_EXTENSION = EXTENSION + ".partial";

    private final Path file;
    private final FileChannel channel;

    HintSegment(Path file, FileChannel channel) {
        this.file = file;
        this.channel = channel;
    }

    public static HintSegment open(Path file) {
        return open(file, false);
    }

    public static HintSegment open(Path file, boolean readOnly) {
        try {
            FileChannel channel;
            if (readOnly) {
                channel = FileChannel.open(file, StandardOpenOption.READ);
            } else {
                channel = FileChannel.open(file, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            }
            return new HintSegment(file, channel);
        } catch (Exception e) {
            throw new KiwiException("Failed to open hint segment " + file, e);
        }
    }

    public int append(Hint hint) throws KiwiWriteException {
        try {
            return channel.write(hint.toByteBuffer());
        } catch (IOException | IllegalStateException e) {
            throw new KiwiWriteException("Failed to write hint to segment " + file, e);
        }
    }

    public void close() {
        try {
            if (channel.isOpen()) {
                channel.force(true);
                channel.close();
            }
        } catch (IOException e) {
            throw new KiwiException("Failed to close hint segment " + file, e);
        }
    }

    public void commit() {
        String fileName = file.getFileName().toString();
        if (!fileName.endsWith(PARTIAL_EXTENSION)) {
            return;
        }
        close();

        String commitedFileName = fileName.replace(PARTIAL_EXTENSION, EXTENSION);
        Path committedFile = file.resolveSibling(commitedFileName);

        Utils.renameFile(file, committedFile);
        logger.info("Marked hint segment {} as committed", file);
    }

    public Iterable<Hint> getHints() {
        return () -> new HintIterator(channel);
    }

    private static class HintIterator implements Iterator<Hint> {
        private final FileChannel channel;
        private long position;
        private Hint nextHint;

        public HintIterator(FileChannel channel) {
            this.channel = channel;
            this.position = 0;
            this.nextHint = null;
        }

        @Override
        public boolean hasNext() {
            if (nextHint != null) {
                return true;
            }

            // Hint file format: [checksum:8][timestamp:8][ttl:8][keySize:4][valueSize:4][valuePosition:8][key:keySize]
            try {
                if (position >= channel.size()) {
                    return false;
                }

                channel.position(position);

                int headerSize = Header.BYTES + Long.BYTES;
                ByteBuffer buffer = ByteBuffer.allocate(headerSize);
                if (channel.read(buffer) < headerSize) {
                    return false; // Not enough data for a header
                }
                buffer.flip();

                long checksum = buffer.getLong();
                long timestamp = buffer.getLong();
                long ttl = buffer.getLong();
                int keySize = buffer.getInt();
                int valueSize = buffer.getInt();
                long valuePosition = buffer.getLong();
                Header header = new Header(checksum, timestamp, ttl, keySize, valueSize);

                buffer.clear();

                ByteBuffer keyBuffer = ByteBuffer.allocate(keySize);
                if (channel.read(keyBuffer) < keySize) {
                    return false; // Not enough data for a key
                }
                Bytes key = Bytes.wrap(keyBuffer.array());

                // Next hint position.
                position = channel.position();

                nextHint = new Hint(header, valuePosition, key);
                return true;
            } catch (IOException ex) {
                throw new KiwiReadException("Failed to read next record from log segment", ex);
            }
        }

        @Override
        public Hint next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Hint hint = nextHint;
            nextHint = null;
            return hint;
        }
    }
}
