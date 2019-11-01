package community.innovation.ecosystem.events;

import community.innovation.ecosystem.email.module.EmailServiceImpl;
import community.innovation.ecosystem.entities.User;
import community.innovation.ecosystem.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationEventListener implements ApplicationListener<RegistrationEvent> {

    @Autowired
    private TokenService tokenService;

    private EmailServiceImpl emailService;

    public RegistrationEventListener(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(RegistrationEvent registrationEvent) {
        this.confirmRegistration(registrationEvent);
    }

    private void confirmRegistration(RegistrationEvent registrationEvent) {
        User user= registrationEvent.getUser();
        String verificationToken= UUID.randomUUID().toString();

        tokenService.createToken(verificationToken,user);

        String recipient=user.getEmail();
        String url=registrationEvent.getAppUrl()+"/confirmRegistration?token="+verificationToken;

        emailService.sendMailWithVerification(recipient,url);
        //emailService.sendMail("rinku@ymail.com","Simple regular email","Hello world");
       /*emailService.sendMailWithAttachment("rinku_chy@ymail.com","email with attachment",
                "hello world attached","");*/
    }
}
