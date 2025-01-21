package com.javamain.db.bigqueue02.core;

import java.nio.ByteBuffer;

public interface IMappedPage {
    ByteBuffer setPosReturnSliceBuffer(int pos);
    ByteBuffer setPosReturnSelfBuffer(int pos);
    byte[] readContent(int pos, int length);
}
