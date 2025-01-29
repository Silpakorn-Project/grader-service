package com.su.ac.th.project.grader.config.security.entrypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.su.ac.th.project.grader.constant.HttpConstant;
import com.su.ac.th.project.grader.exception.BaseException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.su.ac.th.project.grader.util.CommonUtil.getDateTimeNow;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        BaseException exception = new BaseException(getDateTimeNow(), HttpConstant.Message.UNAUTHORIZED, HttpConstant.Status.UNAUTHORIZED);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), exception);
    }
}
