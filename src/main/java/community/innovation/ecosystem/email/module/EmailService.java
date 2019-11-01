package community.innovation.ecosystem.email.module;

import javax.mail.MessagingException;

public interface EmailService {

    void sendMail(String recipient, String subject, String text);

    void sendMailWithVerification(String recipient, String url);

    void sendMailWithAttachment(String recipient, String subject, String text, String pathToAttachment) throws MessagingException;
}
