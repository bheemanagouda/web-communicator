package communicators;

@SuppressWarnings("serial")
public final class EmailException extends RuntimeException {

    public EmailException (String s) {
        super(s);
    }

    public EmailException (String s, Throwable cause) {
        super(s, cause);
    }
}
