package community.innovation.ecosystem.entities;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.io.InputStream;

// this class has no impact in terms of current development approach
@Component
@Document(collection = "knowledge.file")
public class File {

    @Id
    public String knowledgeFileId;

    public String fsFileId; // if the file size is over 16MB
    public String knowledgeId;
    public String fileName;
    public String fileType;
    public Binary file;
    public InputStream stream;

    public String getKnowledgeFileId() {
        return knowledgeFileId;
    }

    public void setKnowledgeFileId(String knowledgeFileId) {
        this.knowledgeFileId = knowledgeFileId;
    }

    public String getFsFileId() {
        return fsFileId;
    }

    public void setFsFileId(String fsFileId) {
        this.fsFileId = fsFileId;
    }

    public String getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(String knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Binary getFile() {
        return file;
    }

    public void setFile(Binary file) {
        this.file = file;
    }

    public InputStream getStream() {
        return stream;
    }

    public void setStream(InputStream stream) {
        this.stream = stream;
    }
}