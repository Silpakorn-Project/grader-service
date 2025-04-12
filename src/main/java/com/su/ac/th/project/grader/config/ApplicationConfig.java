package com.su.ac.th.project.grader.config;

import com.su.ac.th.project.grader.repository.jpa.AuthenticationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class ApplicationConfig {
    private final AuthenticationRepository authenticationRepository;

    public ApplicationConfig(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> authenticationRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
