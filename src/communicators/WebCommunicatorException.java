package communicators;

/**
 * Based on http://goo.gl/UoAS6 by Robert Duvall
 * 
 * General exception representing all possible exceptions of this API.
 * 
 * @author Volodymyr Zavidovych
 * 
 */
@SuppressWarnings("serial")
public class WebCommunicatorException extends RuntimeException {
    public WebCommunicatorException (String s) {
        super(s);
    }

    public WebCommunicatorException (String s, Throwable cause) {
        super(s, cause);
    }
}
