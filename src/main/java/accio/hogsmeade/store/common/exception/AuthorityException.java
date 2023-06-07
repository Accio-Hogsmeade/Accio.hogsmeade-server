package accio.hogsmeade.store.common.exception;

public class AuthorityException extends IllegalArgumentException {

    public AuthorityException() {
    }

    public AuthorityException(String s) {
        super(s);
    }

    public AuthorityException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorityException(Throwable cause) {
        super(cause);
    }
}
