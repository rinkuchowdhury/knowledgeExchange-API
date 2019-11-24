package community.innovation.ecosystem.entities;

import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.io.InputStream;

// this class has no impact in terms of current development approach
@Data
@Component
public class File {

    @Id
    public String knowledgeFileId;

    public String fsFileId; // if the file size is over 16MB
    public String knowledgeId;
    public String fileName;
    public String fileType;
    public Binary file;
    public InputStream stream;

}