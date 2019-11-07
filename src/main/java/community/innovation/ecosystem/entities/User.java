package community.innovation.ecosystem.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@Component
@Document(collection = "user")
public class User {

    @Id
    public String userId;

    public String firstName;
    public String lastName;
    public String address;
    public String telephone;
    public List role;
    public String password;

    @NotBlank
    @Email
    public String email;
    public Boolean verification;

    // getters and setters by lombok
}