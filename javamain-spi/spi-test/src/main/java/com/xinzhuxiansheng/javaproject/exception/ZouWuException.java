package com.xinzhuxiansheng.javaproject.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ZouWuException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ErrorCode errorCode;

    public ZouWuException(ErrorCode errorCode, String errorMessage) {
        super(errorCode.toString() + " - " + errorMessage);
        this.errorCode = errorCode;
    }

    public ZouWuException(String errorMessage) {
        super(errorMessage);
    }

    private ZouWuException(ErrorCode errorCode, String errorMessage, Throwable cause) {
        super(errorCode.toString() + " - " + getMessage(errorMessage) + " - " + getMessage(cause), cause);

        this.errorCode = errorCode;
    }

    public static ZouWuException asDataXException(ErrorCode errorCode, String message) {
        return new ZouWuException(errorCode, message);
    }

    public static ZouWuException asDataXException(String message) {
        return new ZouWuException(message);
    }

    public static ZouWuException asDataXException(ErrorCode errorCode, String message, Throwable cause) {
        if (cause instanceof ZouWuException) {
            return (ZouWuException) cause;
        }
        return new ZouWuException(errorCode, message, cause);
    }

    public static ZouWuException asDataXException(ErrorCode errorCode, Throwable cause) {
        if (cause instanceof ZouWuException) {
            return (ZouWuException) cause;
        }
        return new ZouWuException(errorCode, getMessage(cause), cause);
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

    private static String getMessage(Object obj) {
        if (obj == null) {
            return "";
        }

        if (obj instanceof Throwable) {
            StringWriter str = new StringWriter();
            PrintWriter pw = new PrintWriter(str);
            ((Throwable) obj).printStackTrace(pw);
            return str.toString();
            // return ((Throwable) obj).getMessage();
        } else {
            return obj.toString();
        }
    }
}

