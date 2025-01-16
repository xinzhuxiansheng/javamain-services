package com.javamain.db.bigqueue01.core;

import java.nio.ByteBuffer;

public interface IMappedPage {
    ByteBuffer setPosReturnSliceBuffer(int pos);
    ByteBuffer setPosReturnSelfBuffer(int pos);
    byte[] readContent(int pos, int length);
}
