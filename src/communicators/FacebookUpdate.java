package communicators;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import com.restfb.types.User;


/**
 * Post status update to your Facebook wall. Uses FB Graph API and RESTfb.
 * Instructions on how to generate token: http://goo.gl/NUyc2
 * 
 * @author Volodymyr Zavidovych
 *
 */
public class FacebookUpdate {
    private static final String FAILED_UPDATE_MSG =
            "Coundn't post to wall. Either token or message is invalid, or wall isn't public.";
    private FacebookClient myFacebookClient;
    private User myUser;
    private String myMessage;

    /**
     * Constructor for class.
     * 
     * @param fbMessage Message to put in status update.
     * @param fbAccessToken Token of your app. See http://goo.gl/NUyc2 for details.
     */
    public FacebookUpdate (String fbMessage, String fbAccessToken) {
        myFacebookClient = new DefaultFacebookClient(fbAccessToken);
        myUser = myFacebookClient.fetchObject("me", User.class);
        myMessage = fbMessage;
    }

    /**
     * Attempts to post an update.
     * 
     * @return ID of Facebook entity to which update was posted.
     */
    public String postUpdate () {
        String connection = "me/feed";
        FacebookType response =
                myFacebookClient.publish(connection, FacebookType.class,
                                         Parameter.with("message", myMessage));
        if (response.getId() != null) {
            return response.getId();
        }
        else {
            throw new WebCommunicatorException(FAILED_UPDATE_MSG);
        }
    }
}