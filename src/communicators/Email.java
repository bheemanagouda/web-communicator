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


public class Email implements Sendable<Boolean> {
    private final Integer SMTP_PORT = 587;
    private final Boolean USE_TLS = true;
    private final String SMTP_FAIL_MSG =
            "Could not send the message. Check SMTP host and authentication settings.";
    private final String BAD_PARAMS_MSG = "Bad message parameters.";

    private Properties myProps;
    private Session mySession;
    private Message myMessage;

    public Email (String recipient, String subject, String message, String from, String smtpHost) {
        boolean useAuth = false;
        initProperties(smtpHost, useAuth);
        mySession = Session.getInstance(myProps, null);
        try {
            makeMessage(recipient, subject, message, from);
        }
        catch (EmailException e) {
            // handled inside e
        }
    }

    public Email (String recipient, String subject, String message, String from, String smtpHost,
                  final String username, final String password) {
        boolean useAuth = true;
        initProperties(smtpHost, useAuth);
        mySession = Session.getInstance(myProps, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication () {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            makeMessage(recipient, subject, message, from);
        }
        catch (EmailException e) {
            // handled inside e
        }
    }

    private void initProperties (String smtpHost, Boolean useAuth) {
        myProps = new Properties();
        myProps.put("mail.smtp.starttls.enable", USE_TLS.toString());
        myProps.put("mail.smtp.host", smtpHost);
        myProps.put("mail.smtp.port", SMTP_PORT.toString());
        myProps.put("mail.smtp.auth", useAuth.toString());
    }

    private void makeMessage (String recipient, String subject, String message, String from)
                                                                                            throws EmailException {
        try {
            myMessage = new MimeMessage(mySession);
            myMessage.setFrom(new InternetAddress(from));
            myMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            myMessage.setSubject(subject);
            myMessage.setText(message);
        }
        catch (MessagingException e) {
            throw new EmailException(BAD_PARAMS_MSG, e.getCause());
        }
    }

    @Override
    public Boolean send () {
        try {
            Transport.send(myMessage);
            return true;
        }
        catch (MessagingException e) {
            throw new EmailException(SMTP_FAIL_MSG, e.getCause());
        }
    }
}
