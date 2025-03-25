package com.su.ac.th.project.grader.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserTokenResponse {

    private Long userId;
    private String username;
    private String email;
    private String token;

}
