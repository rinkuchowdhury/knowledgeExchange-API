package community.innovation.ecosystem.repositories;

import community.innovation.ecosystem.entities.Institute;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstituteRepository extends MongoRepository<Institute,String> {
    Boolean existsByInstituteId(String instituteId);
}
