package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.exception.sandbox.UnsupportedLanguageException;
import com.su.ac.th.project.grader.model.request.sandbox.RunTestRequest;
import com.su.ac.th.project.grader.model.response.RunTestResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SandboxClientService {

    private final WebClient javaSandboxClient;
    private final WebClient pythonSandboxClient;

    public SandboxClientService(WebClient.Builder webClientBuilder,
                                @Value("${sandbox.java.url}") String javaSandboxUrl,
                                @Value("${sandbox.python.url}") String pythonSandboxUrl
    ) {
        this.javaSandboxClient = webClientBuilder.baseUrl(javaSandboxUrl).build();
        this.pythonSandboxClient = webClientBuilder.baseUrl(pythonSandboxUrl).build();
    }

    public Mono<RunTestResponse> runTests(RunTestRequest submissionRequest, String language) {
        WebClient webClient = getWebClientForLanguage(language);

        return webClient.post()
                .uri("/submit")
                .bodyValue(submissionRequest)
                .retrieve()
                .bodyToMono(RunTestResponse.class);
    }

    private WebClient getWebClientForLanguage(String language) {
        return switch (language.toUpperCase()) {
            case "JAVA" -> javaSandboxClient;
            case "PYTHON" -> pythonSandboxClient;
            default -> throw new UnsupportedLanguageException(language);
        };
    }
}
