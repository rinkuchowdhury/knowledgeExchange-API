package community.innovation.ecosystem.services;

import community.innovation.ecosystem.entities.Credential;
import community.innovation.ecosystem.entities.Token;
import community.innovation.ecosystem.entities.User;
import community.innovation.ecosystem.events.RegistrationEvent;
import community.innovation.ecosystem.repositories.CredentialRepository;
import community.innovation.ecosystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.WebRequest;
import javax.xml.bind.ValidationException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@Service
public class UserService {

    private UserRepository userRepository;
    private CredentialRepository credentialRepository;
    private ApplicationEventPublisher eventPublisher;
    private TokenService tokenService;
    @Autowired
    private PasswordEncoder pe;
    //private Logger myLogger = Logger.getLogger(getClass().getName());

    @Autowired
    public UserService(UserRepository userRepository, CredentialRepository credentialRepository, ApplicationEventPublisher eventPublisher, TokenService tokenService, PasswordEncoder pe) {
        this.userRepository = userRepository;
        this.credentialRepository = credentialRepository;
        this.eventPublisher = eventPublisher;
        this.tokenService = tokenService;
        this.pe = pe;
    }

    //GET all users
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    //GET a user
    public User getUser(String id){
        return userRepository.findById(id).get();
    }

    // create or edit user
    public String createOrUpdateUser(User user, BindingResult result, WebRequest request, Error error) throws ValidationException {

        // check first the email existence
        Boolean emailExist= userRepository.existsByEmail(user.getEmail());
        Boolean idExist= userRepository.existsByUserId(user.userId);

        if(result.hasErrors()){
            throw new ValidationException(("invalid payload,please provide accurate payload"));
        }

        // PostMapping: if email does not exist, create user and send email for verification
        if(!idExist && !emailExist) {
            User newUser = new User();
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setAddress(user.getAddress());
            newUser.setTelephone(user.getTelephone());
            newUser.setEmail(user.getEmail());
            newUser.setRole(user.getRole());// set "ROLE_" or input via payload
            newUser.setVerification(false);

            // create user's credentials
            Credential credential=new Credential();
            credential.setUsername(newUser.getEmail());
            //credential.setPassword(user.getPassword());
            credential.setPassword(pe.encode(user.getPassword()));
            credential.setVerification(newUser.getVerification());

            // create user and it's credentials
            userRepository.save(newUser);
            credential.setUserId(newUser.getUserId());
            credentialRepository.save(credential);

            // send email for verification
            try {
                String appUrl=request.getContextPath();
                RegistrationEvent event=new RegistrationEvent(appUrl,request.getLocale(),newUser);
                eventPublisher.publishEvent(event);
                return "An email has been sent to your email.";
                //myLogger.info("======= inside try block"+ bindingResult+ request + errors);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // PostMapping: if userId exist then edit the data
        else if(idExist){

            Credential credential=credentialRepository.findByUserId(user.getUserId());

            if(credential.getVerification()){
                // if user change the password
                // need to modify code for email change
                if(user.getPassword()!=null){
                    credential.setPassword(pe.encode(user.getPassword()));
                    //credential.setPassword(user.getPassword());
                    credentialRepository.save(credential);
                }
                userRepository.save(user);
                return "Successfully edited.";
            }
            else return "The account still not verified";
        }
        return "This email is exist in the database.";
    }

    //DELETE a user
    public String deleteUser(String id){
        userRepository.deleteById(id);
        try {
            credentialRepository.deleteByUserId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "This user with an id: "+id+" has been deleted.";
    }

    // user's email verification
    public String activateUser(WebRequest request, String verificationToken) {

        // when token is null
        if(verificationToken==null){
            return "The token value is null.";
        }

        // check the token existence
        Boolean tokenExist=tokenService.existToken(verificationToken);

        if(tokenExist){
            Locale locale=request.getLocale();
            Calendar calendar=Calendar.getInstance();
            Token token=tokenService.getTokenObject(verificationToken);

            Boolean tokenAlive;

            // if 24hrs expire
            if((token.getExpiryDate().getTime()-calendar.getTime().getTime())<=0){
                tokenAlive=false;

                // delete the user account: not implemented yet

                return "The token is expired. please contact with service desk.";
            }
            else {
                tokenAlive=true;
            }
            if(tokenAlive){
                String email=token.getUser().getEmail();
                User user=userRepository.findByEmail(email);
                user.setVerification(true);
                userRepository.save(user);

                Credential credential=credentialRepository.findByUsername(user.getEmail());
                credential.setVerification(true);
                credentialRepository.save(credential);

                tokenService.deleteToken(token);
                return "The account has been activated. Please login to get access.";
                // redirect to login url
            }
        }
        return "The url is broken. please register again or contact to service desk.";
    }

    public String verificationStatus(String id) {
        Boolean status= credentialRepository.findByUserId(id).getVerification();
        if(status){
            return "The user account is verified";
        }
        return "The user account still not verified";
    }
}
