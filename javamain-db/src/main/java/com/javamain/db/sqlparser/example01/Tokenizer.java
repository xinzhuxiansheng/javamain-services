package com.javamain.db.sqlparser.example01;

import lombok.Data;

/**
 * @author yzhou
 * @date 2022/12/21
 */
@Data
public class Tokenizer {
    private byte[] stat;
    private int pos;
    private String currentToken;
    private boolean flushToken;
    private Exception err;

    public Tokenizer(byte[] stat) {
        this.stat = stat;
        this.pos = 0;
        this.currentToken = "";
        this.flushToken = true;
    }

    public String peek() throws Exception {
        if (flushToken) {
            currentToken = nextMetaState();
            flushToken = false;
        }
        return currentToken;
    }

    public void pop() {
        flushToken = true;
    }

//    public String next(){
//
//    }

    private String nextMetaState() throws Exception {
        while (true) {
            Byte b = peekByte(); // 读出 pos下标的字节
            if (b == null) {
                return "";
            }
            if (!isBlank(b)) {    // 不是 换行，空格，制表符 就退出循环
                break;
            }
            increasePos(); // pos +1 偏移量
        }
        byte b = peekByte();
        if (isSymbol(b)) {
            increasePos();
            return new String(new byte[]{b});
        } else if (b == '"' || b == '\'') {   // 遇到 "，' 就取 [ ]的值
            return nextQuoteState();
        } else if (isAlphaBeta(b) || isDigit(b)) {
            return nextTokenState();
        } else {
            throw new IllegalArgumentException("异常");
        }
    }

    private String nextQuoteState() throws Exception {
        byte quote = peekByte(); // 拿出当前下标数据  这只是为了拼接 " " , ' '
        increasePos();
        StringBuilder sb = new StringBuilder();
        while (true) {
            Byte b = peekByte();
            if (b == null) {
                throw new IllegalArgumentException("异常");
            }
            if (b == quote) {
                increasePos();
                break;
            }
            sb.append(new String(new byte[]{b}));
            increasePos();
        }
        return sb.toString();
    }

    private String nextTokenState() throws Exception {  // 取下一部分的环节
        StringBuilder sb = new StringBuilder();
        while (true) {
            Byte b = peekByte();
            if (b == null || !(isAlphaBeta(b) || isDigit(b) || b == '_')) {
                if (b != null && isBlank(b)) {
                    increasePos();
                }
                return sb.toString();
            }
            sb.append(new String(new byte[]{b}));
            increasePos();
        }
    }

    private void increasePos() {
        pos++;
        if (pos > stat.length) {
            pos = stat.length;
        }
    }

    private Byte peekByte() {
        if (pos == stat.length) {
            return null;
        }
        return stat[pos];
    }

    static boolean isSymbol(byte b) {
        return (b == '>' || b == '<' || b == '=' || b == '*' ||
                b == ',' || b == '(' || b == ')');
    }

    static boolean isBlank(byte b) {
        return (b == '\n' || b == ' ' || b == '\t');
    }

    static boolean isDigit(byte b) {
        return (b >= '0' && b <= '9');
    }

    static boolean isAlphaBeta(byte b) {
        return ((b >= 'a' && b <= 'z') || (b >= 'A' && b <= 'Z'));
    }

}
