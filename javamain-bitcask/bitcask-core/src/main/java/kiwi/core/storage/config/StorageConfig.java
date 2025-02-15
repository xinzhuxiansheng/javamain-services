package kiwi.core.storage.config;

import com.typesafe.config.Config;
import kiwi.core.storage.bitcask.log.config.LogConfig;

public class StorageConfig {
    public final LogConfig log;

    public StorageConfig(Config config) {
        this.log = new LogConfig(config.getConfig("log"));
    }
}
