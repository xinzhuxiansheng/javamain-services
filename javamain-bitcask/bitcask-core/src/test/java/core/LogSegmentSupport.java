package core;

import kiwi.core.storage.bitcask.log.Hint;
import kiwi.core.storage.bitcask.log.Record;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class LogSegmentSupport {

    @TempDir
    protected Path root;

    public void writeRecords(String filename, List<Record> records) throws IOException {
        try (FileChannel channel = FileChannel.open(root.resolve(filename), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            records.forEach((record) -> {
                try {
                    channel.write(record.toByteBuffer());
                } catch (IOException ex) {
                    fail(ex);
                }
            });
        }
    }

    public void writeHints(String filename, List<Hint> hints) throws IOException {
        try (FileChannel channel = FileChannel.open(root.resolve(filename), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            hints.forEach((hint) -> {
                try {
                    channel.write(hint.toByteBuffer());
                } catch (IOException ex) {
                    fail(ex);
                }
            });
        }
    }
}
