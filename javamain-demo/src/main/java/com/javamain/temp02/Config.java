package com.javamain.temp02;

public class Config {
    private ConfigManager configManager;
    private String address = "anhui";
    private String className= "class a";

    public Config(ConfigManager configManager){
        this.configManager = configManager;
        configManager.getListeners().add(new Listener("listener a"));
    }
}
