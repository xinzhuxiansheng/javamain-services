package kiwi.core.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import kiwi.core.storage.config.StorageConfig;

public class Options {

    public static final Options defaults = new Options();

    public final StorageConfig storage;

    public Options() {
        Config config = ConfigFactory.load();
        this.storage = new StorageConfig(config.getConfig("kiwi.storage"));
    }
}
