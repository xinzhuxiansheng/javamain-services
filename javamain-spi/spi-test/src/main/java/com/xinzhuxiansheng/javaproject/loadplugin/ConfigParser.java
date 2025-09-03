package com.xinzhuxiansheng.javaproject.loadplugin;

import com.alibaba.fastjson2.JSONObject;
import com.xinzhuxiansheng.javaproject.exception.FrameworkErrorCode;
import com.xinzhuxiansheng.javaproject.exception.ZouWuException;

import java.io.File;
import java.util.*;

public class ConfigParser {

    public static Configuration parse() {
        Configuration configuration = Configuration.newDefault();

        configuration.merge(parseReaderPlugin("impl1reader"), true);
        configuration.merge(parseWriterPlugin("impl2writer"), true);
        return configuration;
    }

    private static Configuration parseReaderPlugin(String syncTypeName) {
        return parseSyncTypeName("reader", syncTypeName);
    }

    private static Configuration parseWriterPlugin(String syncTypeName) {
        return parseSyncTypeName("writer", syncTypeName);
    }

    private static Configuration parseSyncTypeName(String type, String syncTypeName) {
        Configuration configuration = Configuration.newDefault();
        String lowerSyncTypeName = syncTypeName.toLowerCase();
        if (lowerSyncTypeName.endsWith(type)) {
            String pluginDirName = lowerSyncTypeName.substring(0, syncTypeName.length() - type.length());
            List<String> dirAsList = getDirAsList(Contants.ZOUWU_PLUGIN_HOME);

            Optional<String> pluginPath = dirAsList.stream().filter(dir -> dir.endsWith(pluginDirName)).findFirst();
            if (pluginPath.isPresent()) {
                JSONObject vObj = new JSONObject();
                vObj.put("pluginName", pluginDirName);
                vObj.put("pluginPath", pluginPath.get());
                vObj.put("syncTypeName", syncTypeName);
                configuration.set(type, vObj);
                return configuration;
            }
            throw ZouWuException.asDataXException("没有找到该插件路径.");
        }
        throw ZouWuException.asDataXException(FrameworkErrorCode.PLUGIN_NAME_ERROR.getDescription());
    }


    private static List<String> getDirAsList(String path) {
        List<String> result = new ArrayList<String>();
        String[] paths = new File(path).list();
        if (null == paths) {
            return result;
        }
        for (final String each : paths) {
            result.add(path + File.separator + each);
        }
        return result;
    }

    public static String getPluginPath(String pluginName) {
        return Contants.ZOUWU_PLUGIN_HOME + File.separator + pluginName;
    }
}
