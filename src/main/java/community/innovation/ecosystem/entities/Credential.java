package community.innovation.ecosystem.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Document(collection = "credential")
public class Credential {

    @Id
    public String credentialId;

    public String userId;
    public String username;
    public String password;
    public Boolean verification;

    // setters and getters by lombok
}
