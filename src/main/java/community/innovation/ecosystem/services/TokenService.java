package community.innovation.ecosystem.services;

import community.innovation.ecosystem.entities.Token;
import community.innovation.ecosystem.entities.User;
import community.innovation.ecosystem.repositories.TokenRepository;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private TokenRepository tokenRepository;

    //constructor injection
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public void createToken(String verificationToken,User user){
        Token newUserToken=new Token(verificationToken,user);
        tokenRepository.save(newUserToken);
    }

    public Token getTokenObject(String verificationToken){
        return tokenRepository.findByVerificationToken(verificationToken);
    }

    public void deleteToken(Token token){tokenRepository.delete(token);}

    public Boolean existToken(String verificationToken){
        return tokenRepository.existsByVerificationToken(verificationToken);
    }
}
