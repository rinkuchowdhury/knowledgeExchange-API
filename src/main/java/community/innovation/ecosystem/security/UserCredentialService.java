package community.innovation.ecosystem.security;

import community.innovation.ecosystem.entities.Credential;
import community.innovation.ecosystem.repositories.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserCredentialService implements UserDetailsService {
    private CredentialRepository credentialRepository;

    @Autowired
    public UserCredentialService(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Credential userCredential= credentialRepository.findByUsername(username);

        if(userCredential==null) {
            throw new UsernameNotFoundException("User not found");
        }

        // returning only username, password and verification status: authorities will come while role implementation needed
        return new UserCredentials(userCredential.getUsername(),userCredential.getPassword(),userCredential.getVerification());
    }
}
