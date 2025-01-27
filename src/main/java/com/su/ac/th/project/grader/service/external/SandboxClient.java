package com.su.ac.th.project.grader.service.external;

import com.su.ac.th.project.grader.service.external.request.RunTestRequest;
import com.su.ac.th.project.grader.service.external.response.RunTestResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SandboxClient {

    private final WebClient.Builder webClientBuilder;

    public SandboxClient(WebClient.Builder builder) {
        this.webClientBuilder = builder;
    }

    public Mono<RunTestResponse> runTests(RunTestRequest submissionRequest, String language) {
        String baseUri = getSandboxServiceUrl(language);
        WebClient webClient = webClientBuilder.baseUrl(baseUri).build();

        return webClient.post().uri("/submit")
                .bodyValue(submissionRequest)
                .retrieve()
                .bodyToMono(RunTestResponse.class);
    }

    private String getSandboxServiceUrl(String language) {
        return switch (language.toUpperCase()) {
            case "JAVA" -> "http://localhost:3000/java-sandbox-service/sandbox";
            case "PYTHON" -> "http://localhost:3000/python-sandbox-service/sandbox";
            default -> throw new IllegalArgumentException("Unsupported language: " + language);
        };
    }
}
