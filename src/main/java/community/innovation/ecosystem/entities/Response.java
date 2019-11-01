package community.innovation.ecosystem.entities;

import org.springframework.stereotype.Component;

@Component
public class Response {

    private String id;
    private String userId;
    private String title;
    private String fileName;
    private String fileType;
    private String fileDownloadUri;
    private long size;


    public Response() { }

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

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getFileName() { return fileName; }

    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getFileType() { return fileType; }

    public void setFileType(String fileType) {this.fileType = fileType; }

    public String getFileDownloadUri() { return fileDownloadUri; }

    public void setFileDownloadUri(String fileDownloadUri) { this.fileDownloadUri = fileDownloadUri; }

    public long getSize() { return size; }

    public void setSize(long size) { this.size = size; }


}
