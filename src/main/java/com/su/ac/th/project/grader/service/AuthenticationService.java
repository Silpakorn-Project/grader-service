package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.entity.UsersEntity;
import com.su.ac.th.project.grader.exception.authentication.InvalidRefreshTokenException;
import com.su.ac.th.project.grader.exception.authentication.InvalidUsernameOrPasswordException;
import com.su.ac.th.project.grader.model.request.authentication.UsersLoginRequest;
import com.su.ac.th.project.grader.model.request.authentication.UsersRegRequest;
import com.su.ac.th.project.grader.model.response.UserTokenResponse;
import com.su.ac.th.project.grader.repository.jpa.AuthenticationRepository;
import com.su.ac.th.project.grader.util.DtoEntityMapper;
import com.su.ac.th.project.grader.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

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

    public UserTokenResponse login(UsersLoginRequest usersLoginRequest, HttpServletResponse response) {
        UsersEntity userEntity = authenticationRepository
                .findByUsername(usersLoginRequest.getUsername())
                .orElseThrow(InvalidUsernameOrPasswordException::new);

        if (!passwordEncoder.matches(usersLoginRequest.getPassword(), userEntity.getPassword())) {
            throw new InvalidUsernameOrPasswordException();
        }

        Map<String, Object> claims = Map.of("userId", userEntity.getId());
        String accessToken = jwtUtil.generateAccessToken(claims, userEntity.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(claims, userEntity.getUsername());

        jwtUtil.setRefreshTokenCookie(response, refreshToken);

        return UserTokenResponse.builder()
                .userId(userEntity.getId())
                .username(userEntity.getUsername())
                .token(accessToken)
                .build();
    }

    public UserTokenResponse refreshAccessToken(String refreshToken) {
        if (refreshToken == null || !jwtUtil.validateToken(refreshToken)) {
            throw new InvalidRefreshTokenException();
        }

        Long userId = jwtUtil.extractUserId(refreshToken);
        String username = jwtUtil.extractUsername(refreshToken);
        Map<String, Object> claims = Map.of("userId", userId);

        String newAccessToken = jwtUtil.generateAccessToken(claims, username);

        return UserTokenResponse.builder()
                .userId(userId)
                .username(username)
                .token(newAccessToken)
                .build();
    }

    public void logout(HttpServletResponse response) {
        jwtUtil.setRefreshTokenCookie(response, "");
    }

}
