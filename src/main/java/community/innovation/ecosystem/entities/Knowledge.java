package community.innovation.ecosystem.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Getter
@Setter

@Component
@Document(collection = "knowledge")
public class Knowledge {

    @Id
    public String knowledgeId;

    public String title;
    public String description;

    public String knowledgeType; // pitch/publication/project/post
    public String[] knowledgeMember; // userId as current contributors
    public String fileName;
    public String fileDownloadUri;

    public String lookingFor;
    public String status;  // stage: pitch-initial,project: funded

    public String createdUser; // created userId
    public String postedOn;

    public String updatedUser; // updated userId
    public String updatedOn;

    public String affiliation; // example: university of Koblenz

    // @NoArgsConstructor annotation failing application to start. reason not identified yet
    public Knowledge(){}

    public Knowledge(String title, String description, String knowledgeType, String[] knowledgeMember,String fileName, String fileDownloadUri, String lookingFor, String status, String affiliation) {
        this.title = title;
        this.description = description;
        this.knowledgeType = knowledgeType;
        this.knowledgeMember = knowledgeMember;
        this.fileName=fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.lookingFor = lookingFor;
        this.status = status;
        this.affiliation = affiliation;
    }
    // getters & setters by lombok
}
