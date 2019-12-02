package community.innovation.ecosystem.security;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
public class JWTProperties {

    public static final String SECRET= "innovation_ecosystem_fes";

    public static final int EXPIRATION_TIME= 3600000; // 1 hour

    public static final String TOKEN_PREFIX= "Bearer ";

    public static final String HEADER_STRING= "Authorization";
}
