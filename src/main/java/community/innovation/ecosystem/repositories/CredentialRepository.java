package community.innovation.ecosystem.repositories;

import community.innovation.ecosystem.entities.Credential;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialRepository extends MongoRepository<Credential,String> {
    // convention methods defined by mongo , keyword: lessThan/ greaterThan etc.
    // find<Entity: optional>By<entity.propertyname><condition>
    Credential findByUsername(String username);
    Credential findByUserId(String id);
    Long deleteByUserId(String id);
    Boolean existsByUsername(String demoUser);
}