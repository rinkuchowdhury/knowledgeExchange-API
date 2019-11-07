package community.innovation.ecosystem.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Component
@Document(collection = "institute")
public class Institute {

    @Id
    public String instituteId;

    public String instituteName;
    public String instituteType;
    public String instituteAddress;
    public String instituteTel;
    @NotBlank
    @Email
    public String instituteEmail;
    public String instituteWebsite;
    public String instituteStatus;

   // getters and setters by lombok
}
