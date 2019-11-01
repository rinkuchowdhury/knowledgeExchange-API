package community.innovation.ecosystem.services;

import community.innovation.ecosystem.entities.Knowledge;
import community.innovation.ecosystem.entities.Response;
import community.innovation.ecosystem.exceptionHandling.FileNotFoundException;
import community.innovation.ecosystem.exceptionHandling.FileStorageException;
import community.innovation.ecosystem.repositories.KnowledgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KnowledgeService {

    @Autowired
    private KnowledgeRepository knowledgeRepository;
    private final Path fileStorageLocation;

    Path filePath;

    String fileName;
    String fileDownloadUri;
    // date and formatter to String
    SimpleDateFormat dateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    @Autowired
    public KnowledgeService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation =Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", e);
        }
    }

    // store knowledge
    public Response storeKnowledge(String title, String description, String knowledgeType, String[] knowledgeMember,
                                   String lookingFor, String status, String affiliation, MultipartFile file, String createdUserId) {

        //  check file size req. needs to be added
        fileName=storeFile(file);
        fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/knowledge/files/").path(fileName).toUriString();

       Knowledge knowledge=new Knowledge(title,description,knowledgeType,knowledgeMember,fileName,fileDownloadUri,lookingFor,status,affiliation);
       knowledge.setCreatedUser(createdUserId);
       knowledge.setPostedOn(dateTime.format(new Date()));

       // save knowledge object into database
       knowledgeRepository.save(knowledge);

        return new Response(title,fileName,file.getContentType(),fileDownloadUri,file.getSize());
    }

    // update knowledge
    public Response updateKnowledge(String knowledgeId, String title, String description, String knowledgeType, String[] knowledgeMember, String lookingFor, String status, String affiliation, MultipartFile file, String updatedUserId) {
        Knowledge knowledge= knowledgeRepository.findByKnowledgeId(knowledgeId);

        if(!title.equals(knowledge.getTitle())){
            knowledge.setTitle(title);
        }
        if(!description.equals(knowledge.getDescription())){
            knowledge.setDescription(description);
        }
        if(!knowledgeType.equals(knowledge.getKnowledgeType())){
            knowledge.setKnowledgeType(knowledgeType);
        }
        if(!knowledgeMember.equals(knowledge.getKnowledgeMember())){
           knowledge.setKnowledgeMember(knowledgeMember);
        }
        if(!lookingFor.equals(knowledge.getLookingFor())){
            knowledge.setLookingFor(lookingFor);
        }
        if(!status.equals(knowledge.getStatus())){
            knowledge.getStatus();
        }
        if(!affiliation.equals(knowledge.getAffiliation())){
            knowledge.setAffiliation(affiliation);
        }

        if(!file.isEmpty()){
            // storing file
            fileName=storeFile(file);
            fileDownloadUri= ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/knowledge/files/").path(fileName).toUriString();
            knowledge.setFileName(fileName);
            knowledge.setFileDownloadUri(fileDownloadUri);
        }

        knowledge.setUpdatedUser(updatedUserId);
        knowledge.setUpdatedOn(dateTime.format(new Date()));

        knowledgeRepository.save(knowledge);

        return new Response(knowledgeId,updatedUserId,knowledge.getFileName(),knowledge.getFileDownloadUri());
    }

    // get all knowledge entry
    public List<Knowledge> getAllKnowledge() {
        return knowledgeRepository.findAll();
    }

    // get a knowledge entry
    public Knowledge getKnowledge(String id) {
        return knowledgeRepository.findByKnowledgeId(id);
    }

    /*// get all fileDownloadUri
    public List<String> findAllFileDownloadUri() {
        return knowledgeRepository.findAllFileDownloadUri();
    }*/

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

    // store multiple files to file system: possible location in wildfly datasource location
    public List<String> storeMultiFiles(MultipartFile[] files) {
        List<String> fileName= Arrays.asList(files)
                .stream()
                .map(file -> storeFile(file))
                .collect(Collectors.toList());
        return fileName;
    }

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

    public String deleteKnowledge(String knowledgeId) {

        Knowledge knowledge=knowledgeRepository.findByKnowledgeId(knowledgeId);
        fileName=knowledge.getFileName();
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

        knowledgeRepository.deleteById(knowledgeId);

        return fileName;
    }

    /*public String createOrUpdateKnowledge(Knowledge knowledge, @RequestParam("file") MultipartFile file, BindingResult result, WebRequest request, Error error) throws IOException,ValidationException {

        if(result.hasErrors()){
            throw new ValidationException(("invalid payload,please provide accurate payload"));
        }

        return "hello testing";
    }*/

    // get all Knowledge

    // get a knowledge

    // delete a knowledge


}
