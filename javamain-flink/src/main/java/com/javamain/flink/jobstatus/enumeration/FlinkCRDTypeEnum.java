package com.javamain.flink.jobstatus.enumeration;

public enum FlinkCRDTypeEnum {
    FLINKDEPLOYMENT("flinkdeployment","flinkdeployment"),
    FLINKSESSIONJOB("flinksessionjob","flinksessionjob");
    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    private FlinkCRDTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
