package community.innovation.ecosystem;

import community.innovation.ecosystem.services.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileStorageProperties.class)
public class InnoEcoApplication {

    public static void main(String[] args) {
        SpringApplication.run(InnoEcoApplication.class, args);
    }
}
