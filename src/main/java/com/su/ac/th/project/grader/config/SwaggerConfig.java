package com.su.ac.th.project.grader.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().info(new Info()
                .title("Spring Boot REST API")
                .description("this is a REST API for grading")
                .contact(new Contact()
                        .name("Sutthirak")
                        .email("sutsaenya_s@su.ac.th"))
                .version("v1.0.0"));
    }
}
