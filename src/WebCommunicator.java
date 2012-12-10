import java.util.HashMap;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import communicators.Email;
import communicators.FacebookUpdate;
import communicators.HTTPRequest;
import communicators.Tweet;


public class WebCommunicator {

    public boolean sendEmail (String recipient, String subject, String message, String from, String smptHost, String username, String password) {
    Email mail = new Email(recipient, subject, message, from, smptHost, username, password);
    boolean success = mail.send();
    return success;
    }
    
    public boolean sendEmail (String recipient, String subject, String message, String from, String smptHost) {
        Email mail = new Email(recipient, subject, message, from, smptHost);
        boolean success = mail.send();
        return success;
        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication () {
                return new PasswordAuthentication("vladimir.zavidovich@gmail.com", "565$frWdi");
            }
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            msg.setSubject(subject);
            msg.setText(message);

            Transport.send(msg);

            System.out.println("Done");

        }
        catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public String httpGET (String url, HashMap<String, String> params) {
        HTTPRequest get = new HTTPRequest(String url, HashMap<String, String> params, "GET");
        String response = get.send();
        return response;
    }

    public String httpPOST (String url, HashMap<String, String> params) {
        HTTPRequest post = new HTTPRequest(String url, HashMap<String, String> params, "POST");
        String response = post.send();
        return response;
    }
    
    public boolean postFacebookUpdate() {
        FacebookUpdate fb = new FacebookUpdate();
        boolean success = fb.send();
        return success;
    }

    public boolean tweet() {
        Tweet tw = new Tweet();
        boolean success = tw.send();
        return success;
    }
    /**
     * @param args
     */
    public static void main (String[] args) {
        WebCommunicator c = new WebCommunicator();
        c.sendEmail("vo.zavidovych@gmail.com", "test email volodymyr", "hey body sup",
                   "vladimir.zavidovich@gmail.com");
    }
}
