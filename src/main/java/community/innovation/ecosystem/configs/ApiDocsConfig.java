package community.innovation.ecosystem.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class ApiDocsConfig {

    // bean for swagger2: documentation
    @Bean
    public Docket swaggerConfiguration(){

        // Return a prepared Docket instance
        return new Docket(DocumentationType.SWAGGER_2) // create a Docket instance
                .select()  // ApiSelectorBuilder : to customer swagger behaviour : constraints are added below
                .paths(PathSelectors.any()) // path definition to include in the documentation
                .apis(RequestHandlerSelectors.basePackage("community.innovation.ecosystem")) // package based definition to include in the documentation
                .build()
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails() {
        return new ApiInfo(
                "Knowledge Exchange API",
                "Rest API for Knowledge Exchange Platform",
                "1.0",
                "https://www.uni-koblenz-landau.de/", // provide url for terms of service
                 new springfox.documentation.service.Contact("InnoEco","https://www.uni-koblenz-landau.de/","rinkuchowdhury@uni-koblenz.de"),
                "API License", "https://www.uni-koblenz-landau.de/",
                 Collections.emptyList());
    }
}
