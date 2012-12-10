import java.util.HashMap;
import communicators.Email;
import communicators.FacebookUpdate;
import communicators.HttpRequest;
import communicators.Tweet;


public class WebCommunicator {

    public boolean sendEmail (String recipient, String subject, String message, String from,
                              String smtpHost, String username, String password) {
        Email mail = new Email(recipient, subject, message, from, smtpHost, username, password);
        boolean success = mail.sendEmail();
        return success;
    }

    public boolean sendEmail (String recipient, String subject, String message, String from,
                              String smtpHost) {
        Email mail = new Email(recipient, subject, message, from, smtpHost);
        boolean success = mail.sendEmail();
        return success;
    }

    public String httpGET (String url, HashMap<String, String> params) {
        HttpRequest get = new HttpRequest(url, params, "GET");
        String response = get.send();
        return response;
    }

    public String httpPOST (String url, HashMap<String, String> params) {
        HttpRequest post = new HttpRequest(url, params, "POST");
        String response = post.send();
        return response;
    }
    
    public String httpPUT (String url, HashMap<String, String> params) {
        HttpRequest post = new HttpRequest(url, params, "PUT");
        String response = post.send();
        return response;
    }

    public boolean postFacebookUpdate (String fbMessage, String fbLogin, String fbPswd) {
        FacebookUpdate fb = new FacebookUpdate(fbMessage, fbLogin, fbPswd);
        boolean success = fb.postUpdate();
        return success;
    }

    public boolean tweet (String twMessage, String twLogin, String twPswd) {
        Tweet tw = new Tweet(twMessage, twLogin, twPswd);
        boolean success = tw.tweet();
        return success;
    }

    /**
     * @param args
     */
    public static void main (String[] args) {
        WebCommunicator c = new WebCommunicator();

        // smtp server doesn't require authentication
        String recipient = "repicient@example.com";
        String subject = "My subject";
        String message = "My message body";
        String from = "sender@example.com";
        String smptHost = "smtp.mycompany.com";
        c.sendEmail(recipient, subject, message, from, smptHost);

        // smtp server requires authentication
        smptHost = "smtp.google.com";
        String username = "me@google.com";
        String pswd = "myGooglePswd";
        c.sendEmail(recipient, subject, message, from, smptHost, username, pswd);

        // sending HTTP GET and POST requests
        String url = "http://myservice.com/";
        HashMap<String, String> params = new HashMap<String, String>() {
            {
                put("param1", "val1");
                put("param2", "val2");
                put("param3", "val3");
            }
        };
        String getResponse = c.httpGET(url, params);
        String postResponse = c.httpPOST(url, params);
        String putResponse = c.httpPUT(url, params);
        
        // sending FB status update
        String fbMessage = "OMG it was so hilarious i literally died laughing #LOL #SWAG";
        String fbLogin = "megachick@hotmail.com";
        String fbPswd = "cutie777babe";
        c.postFacebookUpdate(fbMessage, fbLogin, fbPswd);
        
        // sending tweet
        String twMessage = "Eating breakfast! #yummy (@ IHOP) [pic]: http://4sq.com/rcgmnH ";
        String twLogin = "cool_john@aol.com";
        String twPswd = "qwerty";
        c.tweet(twMessage, twLogin, twPswd);
    }
}
