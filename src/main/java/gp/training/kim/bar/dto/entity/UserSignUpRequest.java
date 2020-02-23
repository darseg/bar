package gp.training.kim.bar.dto.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(includeFieldNames = true)
public class UserSignUpRequest {

	private String login;
	private String password;
	private String fio;
	private String phone;
}