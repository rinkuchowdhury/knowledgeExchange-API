package community.innovation.ecosystem.repositories;

import community.innovation.ecosystem.entities.Knowledge;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KnowledgeRepository extends MongoRepository<Knowledge,String> {

    Knowledge findByKnowledgeId(String knowledgeId);

    /*//@Query(value="{ 'knowledgeId' : ?0 }", fields="{'knowledgeId':0, 'fileName' : 1}")
    String findFileNamebyId(String knowledgeId);*/

    /*@Query("{ 'fileDownloadUri' : 1 }")
    List<String> findAllFileDownloadUri();*/

}
