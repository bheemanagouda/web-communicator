package communicators;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * Sends emails via TLS-enabled connection. Supports SMTP servers
 * with or without authentication.
 * 
 * @author Volodymyr Zavidovych
 *
 */
public class Email {
    private static final Integer SMTP_PORT = 587;
    private static final Boolean USE_TLS = true;
    private static final String SMTP_FAIL_MSG =
            "Could not send the message. Check SMTP host and authentication settings.";
    private static final String BAD_PARAMS_MSG = "Bad message parameters.";

    private Properties myProps;
    private Session mySession;
    private Message myMessage;

    /**
     * Send email through TLS-enabled connection using
     * SMTP server that doesn't require authentication.
     * 
     * @param recipient Recipient's email
     * @param subject Email subject
     * @param message Email body
     * @param from Sender email
     * @param smtpHost SMTP host address
     */
    public Email (String recipient, String subject, String message, String from, String smtpHost) {
        boolean useAuth = false;
        initProperties(smtpHost, useAuth);
        mySession = Session.getInstance(myProps, null);
        makeMessage(recipient, subject, message, from);
    }

    /**
     * Send email through TLS-enabled connection using
     * SMTP server that requires authentication.
     * 
     * @param recipient Recipient's email
     * @param subject Email subject
     * @param message Email body
     * @param from Sender email
     * @param smtpHost SMTP host address
     * @param username SMTP authentication username
     * @param password SMTP authentication password
     */
    public Email (String recipient, String subject, String message, String from, String smtpHost,
                  final String username, final String password) {
        boolean useAuth = true;
        initProperties(smtpHost, useAuth);
        mySession = Session.getInstance(myProps, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication () {
                return new PasswordAuthentication(username, password);
            }
        });
        makeMessage(recipient, subject, message, from);
    }

    /**
     * Send specified email.
     * 
     * @return Returns true if email was sent successfully.
     */
    public boolean sendEmail () {
        try {
            Transport.send(myMessage);
            return true;
        }
        catch (MessagingException e) {
            throw new WebCommunicatorException(SMTP_FAIL_MSG, e.getCause());
        }
    }
    
    private void initProperties (String smtpHost, Boolean useAuth) {
        myProps = new Properties();
        myProps.put("mail.smtp.starttls.enable", USE_TLS.toString());
        myProps.put("mail.smtp.host", smtpHost);
        myProps.put("mail.smtp.port", SMTP_PORT.toString());
        myProps.put("mail.smtp.auth", useAuth.toString());
    }

    private void makeMessage (String recipient, String subject, String message, String from) {
        try {
            myMessage = new MimeMessage(mySession);
            myMessage.setFrom(new InternetAddress(from));
            myMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            myMessage.setSubject(subject);
            myMessage.setText(message);
        }
        catch (MessagingException e) {
            throw new WebCommunicatorException(BAD_PARAMS_MSG, e.getCause());
        }
    }
}
