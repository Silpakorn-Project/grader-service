package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.entity.UsersEntity;
import com.su.ac.th.project.grader.exception.authentication.AuthenticationException;
import com.su.ac.th.project.grader.model.request.authentication.UsersLoginRequest;
import com.su.ac.th.project.grader.model.request.authentication.UsersRegRequest;
import com.su.ac.th.project.grader.model.response.UserTokenResponse;
import com.su.ac.th.project.grader.repository.jpa.AuthenticationRepository;
import com.su.ac.th.project.grader.util.DtoEntityMapper;
import com.su.ac.th.project.grader.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final AuthenticationRepository authenticationRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthenticationService(AuthenticationRepository authenticationRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.authenticationRepository = authenticationRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public UsersEntity register(UsersRegRequest usersRegRequest) {
        String passwordHashed = passwordEncoder.encode(usersRegRequest.getPassword());
        usersRegRequest.setPassword(passwordHashed);

        UsersEntity u = DtoEntityMapper.mapToEntity(usersRegRequest, UsersEntity.class);

        return authenticationRepository.save(u);
    }

    public UserTokenResponse login(UsersLoginRequest usersLoginRequest) {
        UsersEntity u = DtoEntityMapper.mapToEntity(usersLoginRequest, UsersEntity.class);

        Optional<UsersEntity> userEntity = authenticationRepository.findByUsername(u.getUsername());

        if (userEntity.isEmpty()) {
            AuthenticationException.invalidUsernameOrPassword();
        }

        if (passwordEncoder.matches(u.getPassword(), userEntity.get().getPassword())) {
            String token = jwtUtil.generateToken(usersLoginRequest.getUsername());

            return UserTokenResponse.builder()
                    .username(usersLoginRequest.getUsername())
                    .token(token)
                    .build();
        }

        AuthenticationException.invalidUsernameOrPassword();
        return null;
    }
}
