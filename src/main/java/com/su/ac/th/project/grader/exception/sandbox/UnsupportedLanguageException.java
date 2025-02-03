package com.su.ac.th.project.grader.exception.sandbox;

public class UnsupportedLanguageException extends RuntimeException{
    public UnsupportedLanguageException(String language) {
        super(language + " language is unsupported");
    }
}

