package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.exception.sandbox.SandboxClientException;
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
                                @Value("${sandbox.java.url}") String javaSandboxBaseUrl,
                                @Value("${sandbox.python.url}") String pythonSandboxBaseUrl
    ) {
        String JAVA_SANDBOX_API_ENDPOINT = "/java-sandbox-service";
        String PYTHON_SANDBOX_API_ENDPOINT = "/python-sandbox-service";

        this.javaSandboxClient = webClientBuilder.baseUrl(javaSandboxBaseUrl + JAVA_SANDBOX_API_ENDPOINT).build();
        this.pythonSandboxClient = webClientBuilder.baseUrl(pythonSandboxBaseUrl + PYTHON_SANDBOX_API_ENDPOINT).build();
    }

    public RunTestResponse runTests(RunTestRequest submissionRequest, String language) {
        WebClient webClient = getWebClientForLanguage(language);

        return webClient.post()
                .uri("/sandbox/submit")
                .bodyValue(submissionRequest)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new SandboxClientException(errorBody))))
                .bodyToMono(RunTestResponse.class)
                .onErrorMap(Exception.class, e -> new SandboxClientException(e.getMessage()))
                .block();
    }

    private WebClient getWebClientForLanguage(String language) {
        return switch (language.toUpperCase()) {
            case "JAVA" -> javaSandboxClient;
            case "PYTHON" -> pythonSandboxClient;
            default -> throw new UnsupportedLanguageException(language);
        };
    }
}
