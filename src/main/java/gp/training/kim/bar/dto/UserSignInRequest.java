package gp.training.kim.bar.dto;

import lombok.Data;

@Data
public class UserSignInRequest {

    private final String login;
    private final String password;
}