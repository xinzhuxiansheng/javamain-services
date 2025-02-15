package core.storage.bitcask;

import kiwi.core.common.Bytes;
import kiwi.core.common.KeyValue;
import kiwi.core.storage.bitcask.BitcaskStore;
import kiwi.core.storage.bitcask.log.Record;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BitcaskStoreTest {

    @TempDir
    Path root;

    @Test
    void testPutAndGet() {
        BitcaskStore store = BitcaskStore.open(root);
        store.put(Bytes.wrap("k1"), Bytes.wrap("v1"));
        store.put(Bytes.wrap("k2"), Bytes.wrap("v2"));
        store.put(Bytes.wrap("k3"), Bytes.wrap("v3"));
        store.put(Bytes.wrap("k1"), Bytes.wrap("v1-updated"));

        assertEquals(3, store.size());
        assertEquals(Bytes.wrap("v1-updated"), store.get(Bytes.wrap("k1")).orElseThrow());
        assertEquals(Bytes.wrap("v2"), store.get(Bytes.wrap("k2")).orElseThrow());
        assertEquals(Bytes.wrap("v3"), store.get(Bytes.wrap("k3")).orElseThrow());
    }

    @Test
    void testPutAndGetWithTTL() {
        BitcaskStore store = BitcaskStore.open(root);
        store.put(Bytes.wrap("k1"), Bytes.wrap("v1"));
        // Expired because TTL is in the past.
        store.put(Bytes.wrap("k2"), Bytes.wrap("v2"), -30 * 1000);

        assertEquals(2, store.size());
        assertEquals(Bytes.wrap("v1"), store.get(Bytes.wrap("k1")).orElseThrow());
        assertTrue(store.get(Bytes.wrap("k2")).isEmpty());

        // Expired record is removed from keydir on read.
        assertEquals(1, store.size());
    }

    @Test
    void testGetNonExistentKey() {
        BitcaskStore store = BitcaskStore.open(root);
        assertTrue(store.get(Bytes.wrap("k1")).isEmpty());
    }

    @Test
    void testDelete() {
        BitcaskStore store = BitcaskStore.open(root);
        store.put(Bytes.wrap("k1"), Bytes.wrap("v1"));
        store.put(Bytes.wrap("k2"), Bytes.wrap("v2"));
        store.put(Bytes.wrap("k3"), Bytes.wrap("v3"));
        store.delete(Bytes.wrap("k1"));

        assertEquals(2, store.size());
        assertFalse(store.get(Bytes.wrap("k1")).isPresent());
        assertEquals(Bytes.wrap("v2"), store.get(Bytes.wrap("k2")).orElseThrow());
        assertEquals(Bytes.wrap("v3"), store.get(Bytes.wrap("k3")).orElseThrow());
    }

    @Test
    void testContains() {
        BitcaskStore store = BitcaskStore.open(root);
        store.put(Bytes.wrap("k1"), Bytes.wrap("v1"));
        store.put(Bytes.wrap("k2"), Bytes.wrap("v2"));
        store.put(Bytes.wrap("k3"), Bytes.wrap("v3"));

        assertTrue(store.contains(Bytes.wrap("k1")));
        assertTrue(store.contains(Bytes.wrap("k2")));
        assertTrue(store.contains(Bytes.wrap("k3")));
        assertFalse(store.contains(Bytes.wrap("k4")));
    }

    @Test
    void testSize() {
        BitcaskStore store = BitcaskStore.open(root);
        store.put(Bytes.wrap("k1"), Bytes.wrap("v1"));
        store.put(Bytes.wrap("k2"), Bytes.wrap("v2"));
        store.put(Bytes.wrap("k3"), Bytes.wrap("v3"));
        store.delete(Bytes.wrap("k1"));

        assertEquals(2, store.size());
    }

    @Test
    void testRebuildPopulatesKeydir() throws IOException {
        prepareSegment("000.log", List.of(
                KeyValue.of("k1", "v1"),
                KeyValue.of("k2", "v2"),
                KeyValue.of("k3", "v3"),
                KeyValue.of("k1", null),
                KeyValue.of("k2", "v3-updated")
        ));
        prepareSegment("001.log", List.of(
                KeyValue.of("k1", "v1-new"),
                KeyValue.of("k2", "v2-updated"),
                KeyValue.of("k3", null),
                KeyValue.of("k4", "v4")
        ));
        BitcaskStore store = BitcaskStore.open(root);

        assertEquals(3, store.size());
        assertEquals(Bytes.wrap("v1-new"), store.get(Bytes.wrap("k1")).orElseThrow());
        assertEquals(Bytes.wrap("v2-updated"), store.get(Bytes.wrap("k2")).orElseThrow());
        assertTrue(store.get(Bytes.wrap("k3")).isEmpty());
        assertEquals(Bytes.wrap("v4"), store.get(Bytes.wrap("k4")).orElseThrow());
    }

    @Test
    void testRollActiveSegment() throws IOException {
        // Active segment has 2 * (Header.BYTES (32) + 2) = 68 bytes.
        prepareSegment("00000000000000000000.log", List.of(
                KeyValue.of("a", "1"),
                KeyValue.of("b", "2")
        ));

        List<Path> files = listLogFiles();
        assertEquals(1, files.size());

        Path expectedFirst = Paths.get("00000000000000000000.log");
        Path expectedSecond = Paths.get("00000000000000000001.log");

        assertEquals(expectedFirst, files.getFirst().getFileName());

        // Next put operation should roll new segment because active segment is full.
        BitcaskStore store = BitcaskStore.Builder(root)
                .withLogSegmentBytes(100)
                .build();

        // PUT adds 32 + 4 = 36 bytes to the active segment and triggers roll.
        store.put(Bytes.wrap("c"), Bytes.wrap("3"));

        files = listLogFiles();
        assertEquals(2, files.size());
        assertEquals(expectedFirst, files.getFirst().getFileName());
        assertEquals(expectedSecond, files.getLast().getFileName());

        // Inactive segment should serve reads.
        assertEquals("1", store.get(Bytes.wrap("a")).orElseThrow().toString());
        assertEquals("2", store.get(Bytes.wrap("b")).orElseThrow().toString());
        assertEquals("3", store.get(Bytes.wrap("c")).orElseThrow().toString());
    }

    @Test
    void testPurge() {
        BitcaskStore store = BitcaskStore.open(root);
        store.put(Bytes.wrap("k1"), Bytes.wrap("v1"));
        store.put(Bytes.wrap("k2"), Bytes.wrap("v2"));
        store.put(Bytes.wrap("k3"), Bytes.wrap("v3"));
        store.delete(Bytes.wrap("k1"));

        assertEquals(2, store.size());

        store.purge();

        assertEquals(0, store.size());
    }

    @Test
    void testFileClose() throws IOException {
        FileChannel channel = FileChannel.open(root.resolve("test.log"), StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
        ByteBuffer src = ByteBuffer.wrap("Hello, World!".getBytes());
        channel.write(src);

        channel.close();

        try {
            ByteBuffer dst = ByteBuffer.allocate(1024);
            channel.read(dst);
            fail("Channel should be closed");
        } catch (IOException ex) {
            // Expected.
        }
    }

    void prepareSegment(String name, List<KeyValue<String, String>> entries) throws IOException {
        try (FileChannel channel = FileChannel.open(root.resolve(name), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            entries.forEach((entry) -> {
                try {
                    Bytes key = Bytes.wrap(entry.key());
                    Bytes value = entry.value() == null ? Record.TOMBSTONE : Bytes.wrap(entry.value());
                    channel.write(Record.of(key, value, 0).toByteBuffer());
                } catch (IOException ex) {
                    fail(ex);
                }
            });
        }
    }

    List<Path> listLogFiles() throws IOException {
        try (Stream<Path> stream = Files.walk(root)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".log"))
                    .sorted()
                    .toList();
        }
    }
}