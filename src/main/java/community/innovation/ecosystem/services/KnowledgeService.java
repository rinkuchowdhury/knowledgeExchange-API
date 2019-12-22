package community.innovation.ecosystem.services;

import community.innovation.ecosystem.entities.Knowledge;
import community.innovation.ecosystem.entities.Response;
import community.innovation.ecosystem.repositories.KnowledgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class KnowledgeService {

    private KnowledgeRepository knowledgeRepository;
    private FileHandlingService fileHandlingService;

    String fileName;
    String fileDownloadUri;
    // date and formatter to String
    SimpleDateFormat dateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    @Autowired
    public KnowledgeService(KnowledgeRepository knowledgeRepository,FileHandlingService fileHandlingService) {
        this.knowledgeRepository=knowledgeRepository;
        this.fileHandlingService = fileHandlingService;
    }

    // store knowledge
    public Response storeKnowledge(String title, String description, String knowledgeType, String[] knowledgeMember,
                                   String lookingFor, String status, String affiliation, MultipartFile file, String createdUserId) {

        //  check file size req. needs to be added
        fileName=fileHandlingService.storeFile(file);
        fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/knowledge/files/").path(fileName).toUriString();

       Knowledge knowledge=new Knowledge(title,description,knowledgeType,knowledgeMember,fileName,fileDownloadUri,lookingFor,status,affiliation);
       // apply user existing : not yet implemented
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
            fileName=fileHandlingService.storeFile(file);
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

    public String deleteKnowledge(String knowledgeId) {

        fileName=knowledgeRepository.findByKnowledgeId(knowledgeId).getFileName();
        fileHandlingService.deleteFile(fileName);
        knowledgeRepository.deleteById(knowledgeId);

        return fileName;
    }
}
