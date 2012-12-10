package communicators;

@SuppressWarnings("serial")
public class HttpRequestException extends RuntimeException {
    public HttpRequestException (String s) {
        super(s);
    }

    public HttpRequestException (String s, Throwable cause) {
        super(s, cause);
    }
}
