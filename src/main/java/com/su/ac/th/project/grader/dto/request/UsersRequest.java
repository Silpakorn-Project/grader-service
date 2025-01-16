package com.su.ac.th.project.grader.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersRequest {

    private Long id;
    private String username;
    private String password;
    private String email;

}
