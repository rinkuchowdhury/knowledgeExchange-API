package community.innovation.ecosystem.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class Response {

    private String id;
    private String userId;
    private String title;
    private String fileName;
    private String fileType;
    private String fileDownloadUri;
    private long size;

    public Response(String title, String fileName, String fileType, String fileDownloadUri, long size) {
        this.title = title;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileDownloadUri = fileDownloadUri;
        this.size = size;
    }
    public Response(String id,String userId, String fileName, String fileDownloadUri) {
        this.id = id;
        this.userId = userId;
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
    }

   // getters and setters by lombok
}
