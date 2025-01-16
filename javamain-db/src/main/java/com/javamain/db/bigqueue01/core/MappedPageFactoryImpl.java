package com.javamain.db.bigqueue01.core;

import com.javamain.db.bigqueue01.constants.BrokerConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MappedPageFactoryImpl implements IMappedPageFactory {

    /**
     * 存储目录
     */
    private String storageDirectoryPath;
    private File storageDirectoryFile;
    private String filePrefix;
    private int mappedSize;

    private Map<Long, MappedPageImpl> cache;

    public MappedPageFactoryImpl(String storageDirectoryPath, int mappedSize) throws FileNotFoundException {
        this.storageDirectoryPath = storageDirectoryPath;
        this.mappedSize = mappedSize;

        storageDirectoryFile = new File(storageDirectoryPath);
        if (!storageDirectoryFile.exists()) {
            storageDirectoryFile.mkdirs();
        }
        if (!this.storageDirectoryPath.endsWith(File.separator)) {
            this.storageDirectoryPath += File.separator;
        }
        this.filePrefix = this.storageDirectoryPath + BrokerConstants.PAGE_FILE_NAME + "-";
        cache = new HashMap<>();
    }

    public IMappedPage acquireMappedPage(long index) throws IOException {
        MappedPageImpl mappedFile = cache.get(index);
        if (mappedFile == null) {
            String fileFullPath = getFullPathByIndex(index);
            FileChannel fileChannel = new RandomAccessFile(new File(fileFullPath), "rw").getChannel();
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, mappedSize);
            mappedFile = new MappedPageImpl(fileFullPath, fileChannel, mappedByteBuffer);
            cache.put(index, mappedFile);
            return mappedFile;
        }
        return mappedFile;
    }

    @Override
    public void flush() {
        Collection<MappedPageImpl> values = cache.values();
        for (MappedPageImpl mp : values) {
            mp.flush();
        }
    }

    @Override
    public void close() {
        Collection<MappedPageImpl> values = cache.values();
        for (MappedPageImpl mp : values) {
            mp.clean();
        }
    }

    /**
     * file 完整路径
     *
     * @param index
     * @return
     */
    private String getFullPathByIndex(long index) {
        return this.filePrefix + index + BrokerConstants.PAGE_FILE_SUFFIX;
    }
}
