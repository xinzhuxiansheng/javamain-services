package kiwi.core.error;

public class KiwiException extends RuntimeException {
    public KiwiException(String message, Throwable cause) {
        super(message, cause);
    }

    public KiwiException(String message) {
        super(message);
    }

    public KiwiException(Throwable cause) {
        super(cause);
    }

}
