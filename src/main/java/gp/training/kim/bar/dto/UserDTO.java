package gp.training.kim.bar.dto;

import gp.training.kim.bar.constant.UserRole;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(includeFieldNames = true)
public class UserDTO {

	private String login;

	private String fio;

	private String phone;
}