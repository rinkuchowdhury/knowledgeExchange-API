package community.innovation.ecosystem.controllers;

import community.innovation.ecosystem.entities.Knowledge;
import community.innovation.ecosystem.entities.Response;
import community.innovation.ecosystem.services.KnowledgeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@Api(value = "Knowledge", description = "Available endpoints of this API", tags = {"Knowledge"})
@RequestMapping("/api")
public class KnowledgeRestController {

    private static final Logger logger = LoggerFactory.getLogger(KnowledgeRestController.class);

    private KnowledgeService knowledgeService;

    public KnowledgeRestController(KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }

    // create knowledge entry
    @ApiOperation(value="Add knowledge", tags = { "Knowledge" })
    @PostMapping("/knowledge")
    public Response createKnowledge(@RequestParam("title") String title,
                                    @RequestParam("description") String description,
                                    @RequestParam("knowledgeType") String knowledgeType,
                                    @ApiParam("current contributors: userId")
                                    @RequestParam("knowledgeMember") String[] knowledgeMember,
                                    @RequestParam("lookingFor") String lookingFor,
                                    @RequestParam("status") String status,
                                    @RequestParam("affiliation") String affiliation,
                                    @RequestParam("knowledgeFile") MultipartFile file,
                                    @RequestParam("createdUserId") String createdUserId){

       return knowledgeService.storeKnowledge(title,description,knowledgeType,knowledgeMember,lookingFor,status,affiliation,file,createdUserId);
    }

    // edit knowledge entry
    @ApiOperation(value="Edit knowledge", tags = { "Knowledge" })
    @PutMapping("/knowledge")
    public Response updateKnowledge(@RequestParam("knowledgeId") String knowledgeId,
                                    @RequestParam("title") String title,
                                    @RequestParam("description") String description,
                                    @RequestParam("knowledgeType") String knowledgeType,
                                    @RequestParam("knowledgeMember") String[] knowledgeMember,
                                    @RequestParam("lookingFor") String lookingFor,
                                    @RequestParam("status") String status,
                                    @RequestParam("affiliation") String affiliation,
                                    @RequestParam("knowledgeFile") MultipartFile file,
                                    @RequestParam("updatedUserId") String updatedUserId){

        return knowledgeService.updateKnowledge(knowledgeId,title,description,knowledgeType,knowledgeMember,lookingFor,status,affiliation,file,updatedUserId);
    }

    // get all knowledge entry
    @ApiOperation(value="Get all knowledge", tags = { "Knowledge" })
    @GetMapping("/knowledge")
    public List<Knowledge> getAllKnowledge(){
        return knowledgeService.getAllKnowledge();
    }

    // get a knowledge entry
    @ApiOperation(value="Get knowledge", tags = { "Knowledge" })
    @GetMapping("/knowledge/{id}")
    public Knowledge getKnowledge(@PathVariable ("id") String id){
        return knowledgeService.getKnowledge(id);
    }

    /* to store multiple files
    @PostMapping("/knowledge/files")
    public List<String> storeMultiFiles(@RequestParam("knowledgeFiles") MultipartFile[] files) {
        return knowledgeService.storeMultiFiles(files);
    }
    */

    // get the file
    @ApiOperation(value="Get file", tags = { "Knowledge" })
    @GetMapping("/knowledge/files/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request){
        // load file as Resource
        Resource resource= knowledgeService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+resource.getFilename()+"\"")
                .body(resource);
    }

    /*@GetMapping("/knowledge/files")
    public List<String> getAllFileDownloadUri(){
        return knowledgeService.findAllFileDownloadUri();
    }*/

    // Delete
    @ApiOperation(value="Delete knowledge", tags = { "Knowledge" })
    @DeleteMapping("/knowledge/{id}")
    public String deleteKnowledge(@PathVariable ("id") String knowledgeId){
        return knowledgeService.deleteKnowledge(knowledgeId);
    }

}
