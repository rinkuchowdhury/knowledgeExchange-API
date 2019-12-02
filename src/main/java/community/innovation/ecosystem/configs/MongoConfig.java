package community.innovation.ecosystem.configs;

import community.innovation.ecosystem.entities.Credential;
import community.innovation.ecosystem.repositories.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MongoConfig {

    private CredentialRepository credentialRepository;
    private PasswordEncoder pe;

    @Autowired
    public MongoConfig(CredentialRepository credentialRepository, PasswordEncoder pe) {
        this.credentialRepository = credentialRepository;
        this.pe = pe;
    }

    @Value("${demo.user}")
    String demoUser;

    @Value("${demo.password}")
    String demoPassword;

    @PostConstruct
    public void createDemoUser(){
        Boolean johnExist = credentialRepository.existsByUsername(demoUser);
        if(!johnExist){
            Credential credential = new Credential();
            credential.setUsername(demoUser);
            credential.setPassword(pe.encode(demoPassword));
            credential.setVerification(true);
            credentialRepository.save(credential);
        }
    }
}

/*private static final String MONGO_DB_URL = "localhost";
    private static final int MONGO_DB_PORT = 27017;
    private static final String MONGO_DB_NAME = "InnoFes";
    @Bean
    public MongoTemplate mongoTemplate() throws IOException {
        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
        mongo.setBindIp(MONGO_DB_URL);
        mongo.setPort(MONGO_DB_PORT);
        MongoClient mongoClient = mongo.getObject();
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, MONGO_DB_NAME);
        return mongoTemplate;
    }*//*



*/
