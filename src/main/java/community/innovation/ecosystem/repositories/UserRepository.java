package community.innovation.ecosystem.repositories;

import community.innovation.ecosystem.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

    User findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByUserId(String id);
}
