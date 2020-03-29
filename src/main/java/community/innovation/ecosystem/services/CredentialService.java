package community.innovation.ecosystem.services;

import community.innovation.ecosystem.repositories.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {
    private CredentialRepository credentialRepository;

    @Autowired
    public CredentialService(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    public String login(String username, String password) {
        String status;

        if(credentialRepository.existsByUsername(username)){
            status= "User exist";
            if(credentialRepository.findByUsername(username).getVerification()){
                // can be extracted into a method
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String bCryptPass=credentialRepository.findByUsername(username).getPassword();
                if(encoder.matches(password,bCryptPass)){
                    // initialize jwt : 1hr
                    status= "login successful";
                }
                else status="wrong password";
            }
            else status.concat(" but account not verified");
        }
        else status= "user does not exist, please register and verify your email address";

        return status;
    }
}
