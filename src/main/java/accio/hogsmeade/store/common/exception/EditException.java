package accio.hogsmeade.store.common.exception;

public class EditException extends IllegalArgumentException {

    public EditException() {
    }

    public EditException(String s) {
        super(s);
    }

    public EditException(String message, Throwable cause) {
        super(message, cause);
    }

    public EditException(Throwable cause) {
        super(cause);
    }
}
