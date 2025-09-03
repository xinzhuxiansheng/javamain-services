package com.xinzhuxiansheng.javaproject.exception;

public enum FrameworkErrorCode implements ErrorCode {

    PLUGIN_INIT_ERROR("Framework-1", "ZouWu插件初始化错误, 该问题通常是由于ZouWu安装错误引起，请联系您的运维解决 ."),
    PLUGIN_NAME_ERROR("Framework-2", "该插件名称不合法，请以 reader 或 writer 结尾 .");

    private final String code;

    private final String description;

    private FrameworkErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return String.format("Code:[%s], Description:[%s]. ", this.code,
                this.description);
    }

}

