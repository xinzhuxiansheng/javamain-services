package com.javamain.netty.protocol.coder;

public class TcpMsg {
    //魔数
    private short magic;
    //表示请求包的具体含义
    private int code;
    //消息长度
    private int len;
    private byte[] body;

    public TcpMsg(int code, byte[] body) {
        this.magic = 17671;
        this.code = code;
        this.body = body;
        this.len = body.length;
    }

    public short getMagic() {
        return magic;
    }

    public void setMagic(short magic) {
        this.magic = magic;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
