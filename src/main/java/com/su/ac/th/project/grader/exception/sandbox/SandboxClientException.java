package com.su.ac.th.project.grader.exception.sandbox;

public class SandboxClientException extends RuntimeException {
    public SandboxClientException(String message) {
        super("Request to the Sandbox API occurred an error: " + message);
    }
}
