package kiwi.core.error;

public class KiwiWriteException extends KiwiException {
    public KiwiWriteException(String message, Throwable cause) {
        super(message, cause);
    }

    public KiwiWriteException(String message) {
        super(message);
    }

    public KiwiWriteException(Throwable cause) {
        super(cause);
    }
}
