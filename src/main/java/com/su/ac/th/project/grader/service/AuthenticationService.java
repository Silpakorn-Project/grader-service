package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.entity.UsersEntity;
import com.su.ac.th.project.grader.model.request.UsersRegRequest;
import com.su.ac.th.project.grader.repository.jpa.AuthenticationRepository;
import com.su.ac.th.project.grader.util.DtoEntityMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final AuthenticationRepository authenticationRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(AuthenticationRepository authenticationRepository, PasswordEncoder passwordEncoder) {
        this.authenticationRepository = authenticationRepository;
        this.passwordEncoder = passwordEncoder;
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

    public String login(UsersRegRequest usersRegRequest) {
        if (Objects.isNull(usersRegRequest.getEmail()) ||
                Objects.isNull(usersRegRequest.getPassword())) {
            return "Invalid request";
        }

        //transform
        UsersEntity u = DtoEntityMapper.mapToEntity(usersRegRequest, UsersEntity.class);

        //find database
        Optional<UsersEntity> userEntity = authenticationRepository.findByEmail(u.getEmail());

        //validate is Empty
        if (userEntity.isEmpty()) {
            return "Invalid email or password";
        }

        if (passwordEncoder.matches(u.getPassword(), userEntity.get().getPassword())) {
            return "Login successfully";
        }

        return "Invalid email or password";
    }
}
