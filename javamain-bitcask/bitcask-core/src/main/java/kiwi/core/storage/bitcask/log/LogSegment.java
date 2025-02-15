package kiwi.core.storage.bitcask.log;

import kiwi.core.common.Bytes;
import kiwi.core.error.KiwiException;
import kiwi.core.error.KiwiReadException;
import kiwi.core.error.KiwiWriteException;
import kiwi.core.storage.Utils;
import kiwi.core.storage.bitcask.Header;
import kiwi.core.storage.bitcask.KeyHeader;
import kiwi.core.storage.bitcask.ValueReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Clock;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class LogSegment {
    private static final Logger logger = LoggerFactory.getLogger(LogSegment.class);

    public static final String EXTENSION = ".log";

    private final Path file;
    private FileChannel channel;
    private final Clock clock;

    LogSegment(Path file, FileChannel channel) {
        this(file, channel, Clock.systemUTC());
    }

    LogSegment(Path file, FileChannel channel, Clock clock) {
        this.file = file;
        this.channel = channel;
        this.clock = clock;
    }

    public static LogSegment open(Path file) {
        return open(file, false, Clock.systemUTC());
    }

    public static LogSegment open(Path file, boolean readOnly) throws KiwiException {
        return open(file, readOnly, Clock.systemUTC());
    }

    public static LogSegment open(Path file, boolean readOnly, Clock clock) {
        try {
            FileChannel channel;
            if (readOnly) {
                channel = FileChannel.open(file, StandardOpenOption.READ);
            } else {
                channel = FileChannel.open(file, StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
                channel.position(channel.size());
            }
            return new LogSegment(file, channel, clock);
        } catch (Exception e) {
            throw new KiwiException("Failed to open log segment " + file, e);
        }
    }

    public int append(Record record) throws KiwiWriteException {
        try {
            return channel.write(record.toByteBuffer());
        } catch (IOException | IllegalStateException e) {
            throw new KiwiWriteException("Failed to append record to log segment " + file, e);
        }
    }

    public ByteBuffer read(long position, int size) throws KiwiReadException {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(size);
            channel.read(buffer, position);
            return buffer;
        } catch (IOException | IllegalStateException e) {
            throw new KiwiReadException("Failed to read from log segment " + file, e);
        }
    }

    public long position() throws KiwiReadException {
        try {
            return channel.position();
        } catch (IOException e) {
            throw new KiwiReadException("Failed to get valuePosition of log segment " + file, e);
        }
    }

    public long size() throws KiwiReadException {
        try {
            return channel.size();
        } catch (IOException e) {
            throw new KiwiReadException("Failed to get size of log segment " + file, e);
        }
    }

    public String name() {
        return file.getFileName().toString().replace(EXTENSION, "");
    }

    public Path baseDir() {
        return file.getParent();
    }

    public Path file() {
        return file;
    }

    public void sync() {
        try {
            if (channel.isOpen()) {
                channel.force(true);
            }
        } catch (IOException e) {
            logger.error("Failed to sync log segment {}", file, e);
        }
    }

    public void close() {
        try {
            if (channel.isOpen()) {
                sync();
                channel.close();
            }
        } catch (IOException e) {
            logger.error("Failed to close log segment {}", file, e);
        }
    }

    public void markAsReadOnly() {
        try {
            close();
            channel = FileChannel.open(file, StandardOpenOption.READ);
        } catch (IOException e) {
            throw new KiwiException(e);
        }
    }

    public void markAsDeleted() {
        close();

        String deletedFileName = file.getFileName().toString() + ".deleted";
        Path deletedFile = file.resolveSibling(deletedFileName); // 创建一个新路径

        Utils.renameFile(file, deletedFile);
        logger.info("Marked log segment {} for deletion", file);
    }

    public boolean equalsTo(LogSegment other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        return file.equals(other.file);
    }

    public boolean isSamePath(Path other) {
        return file.equals(other);
    }

    public double dirtyRatio(Map<Bytes, Long> keyTimestampMap) {
        long total = 0;
        long dirtyCount = 0;

        try {
            channel.position(0);
            ByteBuffer buffer = ByteBuffer.allocate(Header.BYTES);
            while (channel.read(buffer) != -1) {
//                flip() 是用于切换模式的，它将缓冲区从写模式切换到读模式。具体来说，flip() 做了以下两件事：
//                position 被设置为 0，表示从缓冲区的开头开始读取数据。
//                limit 被设置为当前的写入位置，表示可读取的数据的结束位置。
                buffer.flip();

                // Skip the checksum.
                buffer.position(buffer.position() + Long.BYTES);

                long timestamp = buffer.getLong();
                long ttl = buffer.getLong();
                int keySize = buffer.getInt();
                int valueSize = buffer.getInt();

                buffer.clear();

                if (ttl > 0 && clock.millis() > ttl) {
                    dirtyCount += 1;

                    // Skip the key and value
                    channel.position(channel.position() + keySize + valueSize);
                } else {
                    ByteBuffer keyBuffer = ByteBuffer.allocate(keySize);
                    channel.read(keyBuffer);

                    Bytes key = Bytes.wrap(keyBuffer.array());
                    // Stale records are considered dirty
                    if (keyTimestampMap.getOrDefault(key, Long.MAX_VALUE) == timestamp) {
                        dirtyCount += 1;
                    }
                    // Skip the value;
                    channel.position(channel.position() + valueSize);
                }
                total += 1;
            }
        } catch (IOException | IllegalStateException e) {
            throw new KiwiReadException("Failed to calculate dirty ratio for log segment " + file, e);
        }

        if (total == 0) {
            return 0;
        }
        return (double) dirtyCount / total;
    }

    public Iterable<Record> getRecords() {
        return () -> new RecordIterator(channel, keyHeader -> true);
    }

    public Iterable<Record> getActiveRecords(Map<Bytes, Long> keyTimestampMap) {
        return () -> new RecordIterator(channel, keyHeader -> isActiveRecord(keyHeader, keyTimestampMap));
    }

    public Map<Bytes, ValueReference> buildKeyDir() {
        String hintPath = file.getFileName().toString().replace(EXTENSION, HintSegment.EXTENSION);
        Path hintFile = file.resolveSibling(hintPath);

        if (Files.exists(hintFile)) {
            try {
                return buildKeyDirFromHint(hintFile);
            } catch (KiwiReadException e) {
                logger.warn("Failed to build keydir from segment hint file {}", hintFile, e);
            }
        }
        return buildKeyDirFromData();
    }

    private Map<Bytes, ValueReference> buildKeyDirFromData() throws KiwiReadException {
        logger.info("Building from segment data file {}", file);

        // Data file format: [checksum:8][timestamp:8][ttl:8][keySize:4][valueSize:4][key:keySize][value:valueSize]
        try {
            channel.position(0);

            Map<Bytes, ValueReference> keyDir = new HashMap<>();
            ByteBuffer buffer = ByteBuffer.allocate(Header.BYTES);
            while (channel.read(buffer) != -1) {
                buffer.flip();

                // Skip the checksum.
                // Checksum validation is done during background compaction.
                buffer.position(buffer.position() + Long.BYTES);

                long timestamp = buffer.getLong();
                long ttl = buffer.getLong();
                int keySize = buffer.getInt();
                int valueSize = buffer.getInt();

                buffer.clear();

                ByteBuffer keyBuffer = ByteBuffer.allocate(keySize);
                channel.read(keyBuffer);

                long valuePosition = channel.position();

                // Skip the value.
                channel.position(valuePosition + valueSize);

                // Skip the record if TTL has expired.
                boolean expired = ttl > 0 && System.currentTimeMillis() > ttl;

                Bytes key = Bytes.wrap(keyBuffer.array());
                if (valueSize > 0 && !expired) {
                    ValueReference valueRef = new ValueReference(this, valuePosition, valueSize, ttl, timestamp);
                    keyDir.put(key, valueRef);
                } else {
                    // Skip expired records and tombstone records.
                    keyDir.put(key, null);
                }
            }
            return keyDir;
        } catch (IOException | IllegalStateException e) {
            throw new KiwiReadException("Failed to build hash table from log segment " + file, e);
        }
    }

    private Map<Bytes, ValueReference> buildKeyDirFromHint(Path hintFile) throws KiwiReadException {
        logger.info("Building keydir from segment hint file {}", hintFile);

        HintSegment hintSegment = HintSegment.open(hintFile, true);
        Map<Bytes, ValueReference> keyDir = new HashMap<>();
        for (Hint hint : hintSegment.getHints()) {
            // Skip the record if TTL has expired
            boolean expired = hint.header().ttl() > 0 && System.currentTimeMillis() > hint.header().ttl();

            if (hint.header().valueSize() > 0 && !expired) {
                ValueReference valueRef = new ValueReference(
                        this,
                        hint.valuePosition(),
                        hint.header().valueSize(),
                        hint.header().ttl(),
                        hint.header().timestamp()
                );
                keyDir.put(hint.key(), valueRef);
            } else {
                // Skip expired record and tombstone
                keyDir.put(hint.key(), null);
            }
        }
        return keyDir;
    }

    private boolean isActiveRecord(KeyHeader keyHeader, Map<Bytes, Long> keyTimestampMap) {
        long ttl = keyHeader.header().ttl();
        if (ttl > 0 && clock.millis() > ttl) {
            return false;
        }
        long timestamp = keyHeader.header().timestamp();
        return keyTimestampMap.getOrDefault(keyHeader.key(), Long.MAX_VALUE) == timestamp;
    }

    private static class RecordIterator implements Iterator<Record> {
        private final FileChannel channel;
        private final Predicate<KeyHeader> predicate;
        private long position;
        private Record nextRecord;

        public RecordIterator(FileChannel channel, Predicate<KeyHeader> predicate) {
            this.channel = channel;
            this.predicate = predicate;
            this.position = 0;
            this.nextRecord = null;
        }


        @Override
        public boolean hasNext() {
            if (nextRecord != null) {
                return true;
            }
            try {
                while (position < channel.size()) {
                    channel.position(position);

                    ByteBuffer headerBuffer = ByteBuffer.allocate(Header.BYTES);
                    if (channel.read(headerBuffer) < Header.BYTES) {
                        return false; // Not enough data for a header
                    }
                    headerBuffer.flip();
                    Header header = Header.fromByteBuffer(headerBuffer);

                    // 下一个 Record 的 position
                    position = channel.position() + header.keySize() + header.valueSize();

                    ByteBuffer keyBuffer = ByteBuffer.allocate(header.keySize());
                    if (channel.read(keyBuffer) < header.keySize()) {
                        return false; // Not enough data for a key
                    }
                    Bytes key = Bytes.wrap(keyBuffer.array());

                    KeyHeader keyHeader = new KeyHeader(key, header);
                    if (predicate.test(keyHeader)) {
                        ByteBuffer valueBuffer = ByteBuffer.allocate(header.valueSize());
                        if (channel.read(valueBuffer) < header.valueSize()) {
                            return false; // Not enough data for a value
                        }
                        valueBuffer.flip();
                        Bytes value = Bytes.wrap(valueBuffer.array());
                        nextRecord = new Record(header, key, value);
                        return true;
                    }
                }
            } catch (IOException ex) {
                throw new KiwiReadException("Failed to read next record from log segment", ex);
            }
            return false;
        }

        @Override
        public Record next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Record record = nextRecord;
            nextRecord = null;
            return record;
        }
    }

}
