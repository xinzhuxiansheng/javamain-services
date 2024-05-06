package com.javamain.flink.jobstatus;


import com.javamain.flink.jobstatus.enumeration.FlinkCRDTypeEnum;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.WatcherException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 通过回调函数机制，将 event 传递出去
 */
public class JobStatusWatch<T> implements Watcher<T> {
    private static final Logger logger = LoggerFactory.getLogger(JobStatusManager.class);
    private FlinkCRDTypeEnum flinkCRDTypeEnum;

    private BiConsumer<Action, T> onEventReceived;
    private Consumer<FlinkCRDTypeEnum> onClose;

    public JobStatusWatch(FlinkCRDTypeEnum flinkCRDTypeEnum,BiConsumer<Watcher.Action, T> onEventReceived,
                          Consumer<FlinkCRDTypeEnum> onClose) {
        this.flinkCRDTypeEnum = flinkCRDTypeEnum;
        this.onEventReceived = onEventReceived;
        this.onClose = onClose;
    }

    @Override
    public void eventReceived(Action action, T resource) {
        if (onEventReceived != null) {
            onEventReceived.accept(action, resource);
        }
    }

    @Override
    public void onClose(WatcherException cause) {
        logger.error("{} jobWatcher has WatcherException, exception msg: {} ",flinkCRDTypeEnum.getCode(),cause.getMessage());
        if (onClose != null) {
            onClose.accept(flinkCRDTypeEnum);
        }
    }
}
