package communicators;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;


/**
 * Updates status of our Twitter application.
 * Uses twitter4j and Twitter OAuth 2.0.
 * https://dev.twitter.com/docs/auth/oauth
 * 
 * @author Volodymyr Zavidovych
 * 
 */
public class Tweet {
    private static final String BAD_CREDENTIALS = "Credentials unrecognized.";
    private static final String BAD_STATUS = "Status update is too long.";

    private Twitter myTwitter;
    private String myMessage;
    private AccessToken myAccessToken;

    /**
     * Constructor for tweet communicator.
     * 
     * @param twMessage Status update
     * @param twConsumerKey App consumer key
     * @param twConsumerSecret App consumer secret
     * @param twPin App pin, if any
     */
    public Tweet (String twMessage, String twConsumerKey, String twConsumerSecret, String twPin) {
        myMessage = twMessage;
        try {
            myTwitter = TwitterFactory.getSingleton();
            myTwitter.setOAuthConsumer(twConsumerKey, twConsumerSecret);
            RequestToken requestToken = myTwitter.getOAuthRequestToken();
            if (twPin.equals("")) {
                myAccessToken = myTwitter.getOAuthAccessToken(requestToken);
            }
            else {
                myAccessToken = myTwitter.getOAuthAccessToken(requestToken, twPin);
            }
        }
        catch (TwitterException e) {
            throw new WebCommunicatorException(BAD_CREDENTIALS, e.getCause());
        }
    }

    /**
     * Attempts to send Twitter status update.
     * Notifies if either credentials are bad, or status message is invalid.
     * 
     * @return Returns true if status was changed successfully.
     */
    public boolean tweet () {
        if (myAccessToken != null) {
            try {
                Status status = myTwitter.updateStatus(myMessage);
                return true;
            }
            catch (TwitterException e) {
                throw new WebCommunicatorException(BAD_STATUS, e.getCause());
            }
        }
        else {
            throw new WebCommunicatorException(BAD_CREDENTIALS);
        }
    }
}
