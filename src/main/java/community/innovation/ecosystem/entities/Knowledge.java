package community.innovation.ecosystem.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
@Document(collection = "knowledge")
public class Knowledge {

    @Id
    public String knowledgeId;

    @TextIndexed
    public String title;

    @TextIndexed
    public String description;

    @TextIndexed
    public String knowledgeType; // pitch/publication/project/post
    public String[] knowledgeMember; // userId as current contributors

    @TextIndexed
    public String fileName;
    public String fileDownloadUri;

    public String lookingFor;
    public String status;  // stage: pitch-initial,project: funded

    public String createdUser; // created userId
    public String postedOn;

    public String updatedUser; // updated userId
    public String updatedOn;

    @TextIndexed
    public String affiliation; // example: university of Koblenz

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
