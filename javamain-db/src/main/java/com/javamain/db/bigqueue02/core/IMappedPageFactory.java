package com.javamain.db.bigqueue02.core;

import java.io.IOException;

public interface IMappedPageFactory {
    IMappedPage acquireMappedPage(long index) throws IOException;
    void flush();
    void close();
}
