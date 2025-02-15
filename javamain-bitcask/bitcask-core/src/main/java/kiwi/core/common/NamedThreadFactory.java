package kiwi.core.common;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {
    private final String prefix;
    private final AtomicInteger threadNumber = new AtomicInteger(0);

    public static NamedThreadFactory create(String prefix) {
        return new NamedThreadFactory(prefix);
    }

    public NamedThreadFactory(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        return new Thread(runnable, prefix + "-" + threadNumber.getAndIncrement());
    }
}
