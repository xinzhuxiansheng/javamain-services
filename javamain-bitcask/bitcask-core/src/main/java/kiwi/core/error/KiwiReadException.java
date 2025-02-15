package kiwi.core.error;

public class KiwiReadException extends KiwiException {

    public KiwiReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public KiwiReadException(String message) {
        super(message);
    }

    public KiwiReadException(Throwable cause) {
        super(cause);
    }
}
