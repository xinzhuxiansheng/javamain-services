package com.javamain.demo.nio.upload;

public class FileDto {

    private String fileName;    //文件名称

    private Integer command; // 1请求创建文件 2传输文件

    private byte[] bytes;       //文件字节；再实际应用中可以使用非对称加密，以保证传输信息安全

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getCommand() {
        return command;
    }

    public void setCommand(Integer command) {
        this.command = command;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
