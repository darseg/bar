package gp.training.kim.bar.dto.entity;

import lombok.Data;

@Data
public class UserSignInRequest {

	private final String login;
	private final String password;
}