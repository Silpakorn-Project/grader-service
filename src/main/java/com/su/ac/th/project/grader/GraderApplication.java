package com.su.ac.th.project.grader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class GraderApplication implements CommandLineRunner {

    private final Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(GraderApplication.class, args);
    }

    @Override
    public void run(String... args){
        String port = environment.getProperty("server.port", "8080");
        String contextPath = environment.getProperty("server.servlet.context-path", "");
        String swaggerUrl = "http://localhost:" + port + contextPath + "/swagger-ui.html";
        log.info("Swagger UI is available at: {}", swaggerUrl);
    }

}
