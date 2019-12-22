package community.innovation.ecosystem.services;

import community.innovation.ecosystem.configs.FileStorageConfig;
import community.innovation.ecosystem.exceptionHandling.FileNotFoundException;
import community.innovation.ecosystem.exceptionHandling.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileHandlingService {

    private final Path fileStorageLocation;
    Path filePath;

    @Autowired
    public FileHandlingService(FileStorageConfig fileStorageConfig) {
        this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", e);
        }
    }

    // store file to file system: possible location in wildfly datasource location
    public String storeFile(MultipartFile file) {

        // Normalize the file name
        String fileName1 = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // Check if the fileName contains invalid characters
            if (fileName1.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid character " + fileName1);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName1);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName1;

        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName1 + ". Please try again!", ex);
        }
    }

    // store multiple files to file system: possible location in aws s3 file system
    /*public List<String> storeMultiFiles(MultipartFile[] files) {
        List<String> fileName= Arrays.asList(files)
                .stream()
                .map(file -> storeFile(file))
                .collect(Collectors.toList());
        return fileName;
    }*/

    public Resource loadFileAsResource(String fileName) {
        try {
            filePath= this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName, ex);
        }
    }

    public void deleteFile(String fileName){
        filePath = this.fileStorageLocation.resolve(fileName).normalize();
        try {
            if(Files.deleteIfExists(filePath)){
                fileName=fileName + " > " + "has been deleted successfully";
            }
            else{
                fileName=fileName + " > " + "has not been deleted";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
