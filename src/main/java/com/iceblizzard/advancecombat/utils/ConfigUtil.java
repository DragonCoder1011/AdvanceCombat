package com.iceblizzard.advancecombat.utils;

import com.iceblizzard.advancecombat.main.AdvanceCombat;

import java.util.Collections;
import java.util.List;

public class ConfigUtil {

    private static ConfigUtil instance = null;
    private final AdvanceCombat plugin = AdvanceCombat.getPlugin(AdvanceCombat.class);

    public static synchronized ConfigUtil getInstance() {
        if (instance == null) instance = new ConfigUtil();
        return instance;
    }

    public synchronized String getString(String path) {
        return plugin.getConfig().getString(path);
    }

    public synchronized int getInteger(String path) {
        return plugin.getConfig().getInt(path);
    }

    public synchronized double getDouble(String path) {
        return plugin.getConfig().getDouble(path);
    }

    public synchronized boolean getBoolean(String path) {
        return plugin.getConfig().getBoolean(path);
    }

    public List<String> getStringList(String path) {
        return Collections.synchronizedList(plugin.getConfig().getStringList(path));
    }
}
