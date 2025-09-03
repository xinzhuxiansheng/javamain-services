package com.xinzhuxiansheng.javaproject.loadplugin;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

public class Contants {
    /**
     * zouwu plugin home
     */
    public static final String ZOUWU_HOME = System.getProperty("zouwu.home");

    public static String ZOUWU_PLUGIN_HOME = StringUtils.join(
            new String[] { ZOUWU_HOME, "plugin" }, File.separator);

}
