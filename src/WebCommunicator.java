import java.util.HashMap;
import communicators.Email;
import communicators.FacebookUpdate;
import communicators.HttpRequest;
import communicators.Tweet;


/**
 * Java web communication API that currently supports:
 * - Sending emails via TLS-enabled connection
 * - Sending HTTP requests (GET, POST, PUT)
 * - Posting Facebook updates to your public wall using Graph API
 * - Sending Twitter updates using Twitter OAuth
 * 
 * GitHub: https://github.com/zavidovych/web-communicator
 * 
 * @author Volodymyr Zavidovych
 * 
 */
public class WebCommunicator {

    /**
     * Send email via TSL-enabled connections. SMTP server requires
     * authentication.
     * 
     * @param recipient Recipient email
     * @param subject Email subject
     * @param message Email body
     * @param from Sender email
     * @param smtpHost SMTP host address
     * @param username SMTP authentication username
     * @param password SMTP authentication password
     * @return returns true if email was sent successfully
     */
    public boolean sendEmail (String recipient, String subject, String message, String from,
                              String smtpHost, String username, String password) {
        Email mail = new Email(recipient, subject, message, from, smtpHost, username, password);
        boolean success = mail.sendEmail();
        return success;
    }

    /**
     * Send email via TSL-enabled connections. SMTP server doesn't require
     * authentication.
     * 
     * @param recipient Recipient email
     * @param subject Email subject
     * @param message Email body
     * @param from Sender email
     * @param smtpHost SMTP host address
     * @return returns true if email was sent successfully
     */
    public boolean sendEmail (String recipient, String subject, String message, String from,
                              String smtpHost) {
        Email mail = new Email(recipient, subject, message, from, smtpHost);
        boolean success = mail.sendEmail();
        return success;
    }

    /**
     * Send GET request
     * 
     * @param url Request URL
     * @param params GET request parameters
     * @return Request response
     */
    public String httpGET (String url, HashMap<String, String> params) {
        HttpRequest get = new HttpRequest(url, params, "GET");
        String response = get.send();
        return response;
    }

    /**
     * Send POST request
     * 
     * @param url Request URL
     * @param params POST request parameters
     * @return Request response
     */
    public String httpPOST (String url, HashMap<String, String> params) {
        HttpRequest post = new HttpRequest(url, params, "POST");
        String response = post.send();
        return response;
    }

    /**
     * Send PUT request
     * 
     * @param url Request URL
     * @param params PUT request parameters
     * @return Request response
     */
    public String httpPUT (String url, HashMap<String, String> params) {
        HttpRequest post = new HttpRequest(url, params, "PUT");
        String response = post.send();
        return response;
    }

    /**
     * Post status update to your public Facebook wall.
     * Details about token: http://goo.gl/NUyc2
     * 
     * @param fbMessage Status update message.
     * @param fbAccessToken Facebook access token.
     * @return returns ID of Facebook entity onto which update was posted.
     */
    public String postFacebookUpdate (String fbMessage, String fbAccessToken) {
        FacebookUpdate fb = new FacebookUpdate(fbMessage, fbAccessToken);
        String response = fb.postUpdate();
        return response;
    }

    /**
     * Post Twitter status update using OAuth. Pin not required
     * Details about credentials: https://dev.twitter.com/docs/auth/oauth/faq
     * 
     * @param twMessage Tweet message
     * @param twConsumerKey App consumer key
     * @param twConsumerSecret App consumer secret
     * @return true if status was updated successfully
     */
    public boolean tweet (String twMessage, String twConsumerKey, String twConsumerSecret) {
        Tweet tw = new Tweet(twMessage, twConsumerKey, twConsumerSecret, "");
        boolean success = tw.tweet();
        return success;
    }

    /**
     * Post Twitter status update using OAuth. Pin required
     * Details about credentials: https://dev.twitter.com/docs/auth/oauth/faq
     * 
     * @param twMessage Tweet message
     * @param twConsumerKey App consumer key
     * @param twConsumerSecret App consumer secret
     * @param twPin App pin
     * @return returns true if status was updated successfully
     */
    public boolean tweet (String twMessage, String twConsumerKey, String twConsumerSecret,
                          String twPin) {
        Tweet tw = new Tweet(twMessage, twConsumerKey, twConsumerSecret, twPin);
        boolean success = tw.tweet();
        return success;
    }
}
