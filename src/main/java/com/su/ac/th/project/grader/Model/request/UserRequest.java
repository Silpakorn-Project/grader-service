package com.su.ac.th.project.grader.Model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private Long id;
    private String username;
    private String password;
    private String email;

}
