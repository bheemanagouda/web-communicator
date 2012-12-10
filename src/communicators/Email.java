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
    private final String SUCCESS_MESSAGE = "Email sent";
    private Properties myProps;
    private Session mySession;
    private Message myMessage;

    public Email (String recipient, String subject, String message, String from, String smptHost) {
        myProps = new Properties();
        myProps.put("mail.smtp.starttls.enable", USE_TLS.toString());
        myProps.put("mail.smtp.host", smptHost);
        myProps.put("mail.smtp.port", SMTP_PORT.toString());
        mySession = Session.getInstance(myProps, null);

    }

    public Email (String recipient, String subject, String message, String from, String smptHost,
                  final String username, final String password) {
        this(recipient, subject, message, from, smptHost);
        myProps.put("mail.smtp.auth", "true");
        mySession = Session.getInstance(myProps, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication () {
                return new PasswordAuthentication(username, password);
            }
        });
    }
    

    @Override
    public Boolean send () {
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            msg.setSubject(subject);
            msg.setText(message);

            Transport.send(msg);

            System.out.println(SUCCESS_MESSAGE);
            return true;

        }
        catch (MessagingException e) {
            throw new EmailException(e);
            return false;
        }
    }

}
