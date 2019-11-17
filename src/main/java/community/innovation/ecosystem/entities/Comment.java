package community.innovation.ecosystem.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Data
@Component
@Document(collection = "comment")
public class Comment {

    @Id
    public String commentId;

    public String userId;
    public String knowledgeId;

    public String comment;
    public String postedOn;
}
