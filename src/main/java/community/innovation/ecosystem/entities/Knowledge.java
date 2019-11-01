package community.innovation.ecosystem.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

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

    public Knowledge() {
    }

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

    // getters & setters

    public String getKnowledgeId() { return knowledgeId; }

    public void setKnowledgeId(String knowledgeId) { this.knowledgeId = knowledgeId; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getKnowledgeType() { return knowledgeType; }

    public void setKnowledgeType(String knowledgeType) { this.knowledgeType = knowledgeType; }

    public String[] getKnowledgeMember() { return knowledgeMember; }

    public void setKnowledgeMember(String[] knowledgeMember) { this.knowledgeMember = knowledgeMember; }

    public String getFileName() { return fileName; }

    public void setFileName(String fileName) { this.fileName = fileName;}

    public String getFileDownloadUri() { return fileDownloadUri; }

    public void setFileDownloadUri(String fileDownloadUri) { this.fileDownloadUri = fileDownloadUri; }

    public String getLookingFor() { return lookingFor; }

    public void setLookingFor(String lookingFor) { this.lookingFor = lookingFor; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getCreatedUser() { return createdUser; }

    public void setCreatedUser(String createdUser) { this.createdUser = createdUser; }

    public String getPostedOn() { return postedOn; }

    public void setPostedOn(String postedOn) { this.postedOn = postedOn; }

    public String getUpdatedUser() { return updatedUser; }

    public void setUpdatedUser(String updatedUser) { this.updatedUser = updatedUser; }

    public String getUpdatedOn() { return updatedOn; }

    public void setUpdatedOn(String updatedOn) { this.updatedOn = updatedOn; }

    public String getAffiliation() { return affiliation; }

    public void setAffiliation(String affiliation) { this.affiliation = affiliation;}
}
