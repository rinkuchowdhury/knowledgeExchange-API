package community.innovation.ecosystem.repositories;

import community.innovation.ecosystem.entities.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends MongoRepository<Token,String> {

    Token findByVerificationToken(String verificationToken);

    Boolean existsByVerificationToken(String verificationToken);
}
