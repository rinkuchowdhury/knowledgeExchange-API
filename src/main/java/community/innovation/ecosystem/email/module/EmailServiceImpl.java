package community.innovation.ecosystem.email.module;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class EmailServiceImpl implements EmailService{

    JavaMailSenderImpl mailSender=new JavaMailSenderImpl();

    EmailProperties emailProperties;

    public EmailServiceImpl( EmailProperties emailProperties) {
        this.emailProperties = emailProperties;

        // create mail sender instance
        this.mailSender.setHost(emailProperties.getHost());
        this.mailSender.setPort(emailProperties.getPort());
        this.mailSender.setUsername(emailProperties.getUsername());
        this.mailSender.setPassword(emailProperties.getPassword());
    }

    // sendMail()
    @Override
    public void sendMail(String recipient, String subject, String text) {
        try {
            SimpleMailMessage mailMessage=new SimpleMailMessage();
            mailMessage.setTo(recipient);
            mailMessage.setSubject(subject);
            mailMessage.setText(text);

            mailSender.send(mailMessage);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }

    // sendMailWithVerification() : user registration
    @Override
    public void sendMailWithVerification(String recipient, String url) {

        String subject=emailProperties.getEmailSubject();
        String text=emailProperties.getEmailMessage()+"\n"+"http://localhost:8080/api"+url;
        this.sendMail(recipient,subject,text);
    }

    //sendMailWithAttachment()
    @Override
    public void sendMailWithAttachment(String recipient, String subject, String text, String pathToAttachment){
        try {
            MimeMessage mimeMailMessage = mailSender.createMimeMessage();

            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper mailSenderHelper = new MimeMessageHelper(mimeMailMessage, true);

            // file path
            FileSystemResource file=new FileSystemResource(new File(pathToAttachment));

            mailSenderHelper.setTo(recipient);
            mailSenderHelper.setSubject(subject);
            mailSenderHelper.setText(text);
            mailSenderHelper.addAttachment("Invoice",file);

            mailSender.send(mimeMailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
