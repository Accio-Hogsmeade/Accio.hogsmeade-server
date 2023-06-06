package accio.hogsmeade.store.common.exception;

public class DuplicateException extends IllegalArgumentException {

    public DuplicateException() {
    }

    public DuplicateException(String s) {
        super(s);
    }

    public DuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateException(Throwable cause) {
        super(cause);
    }
}
