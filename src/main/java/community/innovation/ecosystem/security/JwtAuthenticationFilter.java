package community.innovation.ecosystem.security;

import com.auth0.jwt.JWT;
import community.innovation.ecosystem.repositories.CredentialRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private CredentialRepository credentialRepository;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, CredentialRepository credentialRepository) {
        this.authenticationManager = authenticationManager;
        this.credentialRepository=credentialRepository;
    }

    // trigger when request to /login :  still need mapping to credentialrepository
    // go through again this method
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // create login Token
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            credentialRepository.findByUserId("id").getUsername(),
                credentialRepository.findByUserId("id").getPassword(),
                new ArrayList<>()
        );
        // Authenticate user
        Authentication auth = authenticationManager.authenticate(authenticationToken);

        return auth;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // retrieve user credentials
        UserCredentials userCredentials= (UserCredentials) authResult.getPrincipal();

        // Create JWT Token
        String token = JWT.create()
                .withSubject(userCredentials.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+JWTProperties.EXPIRATION_TIME))
                .sign(HMAC512(JWTProperties.SECRET.getBytes()));

        // Add token in response
        response.addHeader(JWTProperties.HEADER_STRING, JWTProperties.TOKEN_PREFIX +  token);
    }
}
