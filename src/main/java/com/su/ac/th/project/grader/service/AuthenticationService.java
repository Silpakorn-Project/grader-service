package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.entity.UsersEntity;
import com.su.ac.th.project.grader.model.request.authentication.UsersLoginRequest;
import com.su.ac.th.project.grader.model.request.authentication.UsersRegRequest;
import com.su.ac.th.project.grader.repository.jpa.AuthenticationRepository;
import com.su.ac.th.project.grader.util.DtoEntityMapper;
import com.su.ac.th.project.grader.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
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

    public String register(UsersRegRequest usersRegRequest) {
        //validate
        if (Objects.isNull(usersRegRequest.getUsername()) ||
                Objects.isNull(usersRegRequest.getPassword()) ||
                Objects.isNull(usersRegRequest.getEmail())) {

            return "Invalid request";
        }

        //hash password
        String passwordHashed = passwordEncoder.encode(usersRegRequest.getPassword());
        usersRegRequest.setPassword(passwordHashed);


        //transform data
        UsersEntity u = DtoEntityMapper.mapToEntity(usersRegRequest, UsersEntity.class);

        //save data
        UsersEntity usersEntity1 = authenticationRepository.save(u);

        return "successfully";
    }

    public String login(UsersLoginRequest usersLoginRequest) {
        if (Objects.isNull(usersLoginRequest.getUsername()) ||
                Objects.isNull(usersLoginRequest.getPassword())) {
            return "Invalid request";
        }

        //transform
        UsersEntity u = DtoEntityMapper.mapToEntity(usersLoginRequest, UsersEntity.class);

        //find database
        Optional<UsersEntity> userEntity = authenticationRepository.findByUsername(u.getUsername());

        //validate is Empty
        if (userEntity.isEmpty()) {
            return "Invalid email or password";
        }

        if (passwordEncoder.matches(u.getPassword(), userEntity.get().getPassword())) {
            String token = jwtUtil.generateToken(usersLoginRequest.getUsername());
            return "Login successfully" + " " + token;
        }

        return "Invalid email or password";
    }
}
