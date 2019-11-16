package community.innovation.ecosystem.repositories;

import community.innovation.ecosystem.entities.Knowledge;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends MongoRepository<Knowledge,String> {

    List<Knowledge> findAllBy(TextCriteria criteria);
}
