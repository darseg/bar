package gp.training.kim.bar.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(includeFieldNames = true)
public class UserDTO {

	private String login;
	private String password;
	private String fio;
	private String phone;
}