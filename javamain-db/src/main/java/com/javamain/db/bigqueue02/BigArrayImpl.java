package com.javamain.db.bigqueue02;

import com.javamain.db.bigqueue02.core.IMappedPage;
import com.javamain.db.bigqueue02.core.IMappedPageFactory;
import com.javamain.db.bigqueue02.core.MappedPageFactoryImpl;
import com.javamain.db.bigqueue02.utils.Calculator;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;

public class BigArrayImpl implements IBigArray {

    // folder name for index page
    public static String INDEX_PAGE_FOLDER = "index";
    // folder name for data page
    public static String DATA_PAGE_FOLDER = "data";
    // folder name for meta data page
    public static String META_DATA_PAGE_FOLDER = "meta_data";

    // 2 ^ 17 = 1024 * 128
    final static int INDEX_ITEMS_PER_PAGE_BITS = 17; // 1024 * 128
    // number of items per page
    final static int INDEX_ITEMS_PER_PAGE = 1 << INDEX_ITEMS_PER_PAGE_BITS;
    // 2 ^ 5 = 32
    final static int INDEX_ITEM_LENGTH_BITS = 5;
    // length in bytes of an index item
    final static int INDEX_ITEM_LENGTH = 1 << INDEX_ITEM_LENGTH_BITS;
    // size in bytes of an index page
    final static int INDEX_PAGE_SIZE = INDEX_ITEM_LENGTH * INDEX_ITEMS_PER_PAGE;

    // size in bytes of a data page
    final int DATA_PAGE_SIZE;

    // default size in bytes of a data page
    final static int DEFAULT_DATA_PAGE_SIZE = 128 * 1024 * 1024;
    // minimum size in bytes of a data page
    final static int MINIMUM_DATA_PAGE_SIZE = 32 * 1024 * 1024;
    // 2 ^ 4 = 16
    final static int META_DATA_ITEM_LENGTH_BITS = 4;
    // size in bytes of a meta data page
    final static int META_DATA_PAGE_SIZE = 1 << META_DATA_ITEM_LENGTH_BITS;

    // only use the first page
    static final long META_DATA_PAGE_INDEX = 0;

    final AtomicLong arrayHeadIndex = new AtomicLong();
    // tail index of the big array,
    // readers can't read items before this tail
    final AtomicLong arrayTailIndex = new AtomicLong();

    // head index of the data page, this is the to be appended data page index
    long headDataPageIndex;
    // head offset of the data page, this is the to be appended data offset
    int headDataItemOffset;

    // timestamp offset of an data item within an index item
    final static int INDEX_ITEM_DATA_ITEM_TIMESTAMP_OFFSET = 16;


    private String storageDirectoryPath;
    // factory for index page management(acquire, release, cache)
    IMappedPageFactory indexPageFactory;
    // factory for data page management(acquire, release, cache)
    IMappedPageFactory dataPageFactory;
    // factory for meta data page management(acquire, release, cache)
    IMappedPageFactory metaPageFactory;

    public BigArrayImpl(String storageDirectoryPath, String arrayName) throws IOException {
        this(storageDirectoryPath, arrayName, DEFAULT_DATA_PAGE_SIZE);
    }

    /**
     * 支持自定义 mapped size
     */
    public BigArrayImpl(String arrayDir, String arrayName, int mappedSize) throws IOException {
        this.storageDirectoryPath = arrayDir;
        if (!storageDirectoryPath.endsWith(File.separator)) {
            storageDirectoryPath += File.separator;
        }
        // append array name as part of the directory
        this.storageDirectoryPath = storageDirectoryPath + arrayName + File.separator;

        // validate directory
//        if (!FileUtil.isFilenameValid(arrayDirectory)) {
//            throw new IllegalArgumentException("invalid array directory : " + arrayDirectory);
//        }

        if (mappedSize < MINIMUM_DATA_PAGE_SIZE) {
            throw new IllegalArgumentException("invalid mapped size, allowed minimum is : " + MINIMUM_DATA_PAGE_SIZE + " bytes.");
        }

        DATA_PAGE_SIZE = mappedSize;

        this.commonInit();
    }

    void commonInit() throws IOException {
        // initialize page factories
        this.indexPageFactory = new MappedPageFactoryImpl(
                this.storageDirectoryPath + INDEX_PAGE_FOLDER, INDEX_PAGE_SIZE);
        this.dataPageFactory = new MappedPageFactoryImpl(
                this.storageDirectoryPath + DATA_PAGE_FOLDER, DATA_PAGE_SIZE);
        // the ttl does not matter here since meta data page is always cached
        this.metaPageFactory = new MappedPageFactoryImpl(
                this.storageDirectoryPath + META_DATA_PAGE_FOLDER, META_DATA_PAGE_SIZE);

        // initialize array indexes
        initArrayIndex();

        // initialize data page indexes
        initDataPageIndex();
    }

    // find out array head/tail from the meta data
    void initArrayIndex() throws IOException {
        IMappedPage metaDataFile = this.metaPageFactory.acquireMappedPage(META_DATA_PAGE_INDEX);
        ByteBuffer metaBuf = metaDataFile.setPosReturnSliceBuffer(0);
        long head = metaBuf.getLong(); // 头
        long tail = metaBuf.getLong(); // 尾

        arrayHeadIndex.set(head);
        arrayTailIndex.set(tail);
    }

    // find out data page head index and offset
    void initDataPageIndex() throws IOException {

        if (this.isEmpty()) {
            headDataPageIndex = 0L;
            headDataItemOffset = 0;
        } else {
            IMappedPage previousIndexPage = null;
            long previousIndexPageIndex = -1;
            try {
                long previousIndex = this.arrayHeadIndex.get() - 1;
                previousIndexPageIndex = Calculator.div(this.arrayHeadIndex.get(), INDEX_ITEMS_PER_PAGE_BITS); // shift optimization
                previousIndexPage = this.indexPageFactory.acquireMappedPage(previousIndexPageIndex);
                int previousIndexPageOffset = (int) (Calculator.mul(Calculator.mod(previousIndex, INDEX_ITEMS_PER_PAGE_BITS), INDEX_ITEM_LENGTH_BITS));
                ByteBuffer previousIndexItemBuffer = previousIndexPage.setPosReturnSliceBuffer(previousIndexPageOffset);
                long previousDataPageIndex = previousIndexItemBuffer.getLong(); // 数据 index file
                int previousDataItemOffset = previousIndexItemBuffer.getInt();  // 数据 offset
                int perviousDataItemLength = previousIndexItemBuffer.getInt();  // 数据 length

                headDataPageIndex = previousDataPageIndex;
                headDataItemOffset = previousDataItemOffset + perviousDataItemLength; // 下一次数据写入 Offset
            } finally {
                if (previousIndexPage != null) {
                    //this.indexPageFactory.releasePage(previousIndexPageIndex);
                }
            }
        }
    }

    @Override
    public long append(byte[] data) throws IOException {

        // 获取 data file 写入数据的起始 offset
        // prepare the data pointer
        if (this.headDataItemOffset + data.length > DATA_PAGE_SIZE) { // not enough space
            this.headDataPageIndex++;
            this.headDataItemOffset = 0;
        }

        long toAppendDataPageIndex  = this.headDataPageIndex;
        int toAppendDataItemOffset  = this.headDataItemOffset;
        long toAppendArrayIndex = this.arrayHeadIndex.get();

        // data
        IMappedPage toAppendDataPage = this.dataPageFactory.acquireMappedPage(toAppendDataPageIndex);
        ByteBuffer toAppendDataPageBuffer = toAppendDataPage.setPosReturnSelfBuffer(toAppendDataItemOffset);
        toAppendDataPageBuffer.put(data);
        // update to next
        this.headDataItemOffset += data.length;

        // index
        long toAppendIndexPageIndex = Calculator.div(toAppendArrayIndex, INDEX_ITEMS_PER_PAGE_BITS);
        IMappedPage toAppendIndexPage = this.indexPageFactory.acquireMappedPage(toAppendIndexPageIndex);
        int toAppendIndexItemOffset = (int) (Calculator.mul(Calculator.mod(toAppendArrayIndex, INDEX_ITEMS_PER_PAGE_BITS), INDEX_ITEM_LENGTH_BITS));

        ByteBuffer toAppendIndexPageBuffer = toAppendIndexPage.setPosReturnSelfBuffer(toAppendIndexItemOffset);
        toAppendIndexPageBuffer.putLong(toAppendDataPageIndex);
        toAppendIndexPageBuffer.putInt(toAppendDataItemOffset);
        toAppendIndexPageBuffer.putInt(data.length);
        long currentTime = System.currentTimeMillis();
        toAppendIndexPageBuffer.putLong(currentTime);

        // 增加数据 size
        arrayHeadIndex.incrementAndGet();

        // meta_data
        IMappedPage metaDataPage = this.metaPageFactory.acquireMappedPage(META_DATA_PAGE_INDEX);
        ByteBuffer metaDataByteBuffer = metaDataPage.setPosReturnSliceBuffer(0);
        metaDataByteBuffer.putLong(arrayHeadIndex.get());
        metaDataByteBuffer.putLong(arrayTailIndex.get());

        return toAppendArrayIndex;
    }

    @Override
    public byte[] get(long index) throws IOException {
        ByteBuffer sliceIndexByteBuffer = calcArrayIndexReturnSliceIndexByteBuffer(index);
        long dataPageIndex = sliceIndexByteBuffer.getLong();
        int dataItemOffset = sliceIndexByteBuffer.getInt();
        int dataLength = sliceIndexByteBuffer.getInt();
        IMappedPage dataMappedPage = this.dataPageFactory.acquireMappedPage(dataPageIndex);
        byte[] content = dataMappedPage.readContent(dataItemOffset, dataLength);
        return content;
    }

    /**
     * 根据数据下标，获取 item 追加时间
     *
     * @param index valid data index
     * @return
     * @throws IOException
     */
    @Override
    public long getTimestamp(long index) throws IOException {
        ByteBuffer sliceIndexByteBuffer = calcArrayIndexReturnSliceIndexByteBuffer(index);
        int position = sliceIndexByteBuffer.position();
        sliceIndexByteBuffer.position(position + INDEX_ITEM_DATA_ITEM_TIMESTAMP_OFFSET);
        long timestamp = sliceIndexByteBuffer.getLong();
        return timestamp;
    }

    @Override
    public long size() {
        return this.arrayHeadIndex.get() - this.arrayTailIndex.get();
    }

    /**
     * 获取 Data File 设置的 mapped size
     *
     * @return
     */
    @Override
    public int getDataPageSize() {
        return DATA_PAGE_SIZE;
    }

    @Override
    public long getHeadIndex() {
        return arrayHeadIndex.get();
    }

    @Override
    public long getTailIndex() {
        return arrayTailIndex.get();
    }

    @Override
    public boolean isEmpty() {
        return this.arrayHeadIndex.get() == this.arrayTailIndex.get();
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public void flush() {
        dataPageFactory.flush();
        indexPageFactory.flush();
        metaPageFactory.flush();
    }

    @Override
    public void close() throws IOException {
        this.dataPageFactory.close();
        this.indexPageFactory.close();
        this.metaPageFactory.close();
    }

    /**
     * 根据 arrayIndex 获取 对应的 ByteBuffer
     * 该方法会根据 index 计算出 indexFileOffset，并且根据 indexFileOffset 设置 pos
     */
    private ByteBuffer calcArrayIndexReturnSliceIndexByteBuffer(long arrayIndex) throws IOException {
        long indexPageIndex = 0L;
        IMappedPage indexMappedPage = this.indexPageFactory.acquireMappedPage(indexPageIndex);
        int indexFileOffset = (int) (Calculator.mul(Calculator.mod(arrayIndex, INDEX_ITEMS_PER_PAGE_BITS), INDEX_ITEM_LENGTH_BITS));
        ByteBuffer mappedBuffer = indexMappedPage.setPosReturnSliceBuffer(indexFileOffset);
        return mappedBuffer;
    }

}
