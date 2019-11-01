package community.innovation.ecosystem.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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

    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getInstituteType() {
        return instituteType;
    }

    public void setInstituteType(String instituteType) {
        this.instituteType = instituteType;
    }

    public String getInstituteAddress() {
        return instituteAddress;
    }

    public void setInstituteAddress(String instituteAddress) {
        this.instituteAddress = instituteAddress;
    }

    public String getInstituteTel() {
        return instituteTel;
    }

    public void setInstituteTel(String instituteTel) {
        this.instituteTel = instituteTel;
    }

    public String getInstituteEmail() {
        return instituteEmail;
    }

    public void setInstituteEmail(String instituteEmail) {
        this.instituteEmail = instituteEmail;
    }

    public String getInstituteWebsite() {
        return instituteWebsite;
    }

    public void setInstituteWebsite(String instituteWebsite) {
        this.instituteWebsite = instituteWebsite;
    }

    public String getInstituteStatus() {
        return instituteStatus;
    }

    public void setInstituteStatus(String instituteStatus) {
        this.instituteStatus = instituteStatus;
    }
}
