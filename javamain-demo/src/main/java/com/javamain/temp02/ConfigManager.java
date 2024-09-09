package com.javamain.temp02;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {
    private  List<Listener> listeners;

    public ConfigManager(){
        listeners = new ArrayList<>();
    }

    public  List<Listener> getListeners() {
        return listeners;
    }
}
