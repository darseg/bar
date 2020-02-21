package gp.training.kim.bar.dbo;

import gp.training.kim.bar.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "user")
@AllArgsConstructor
public class UserDBO extends AbstractBarEntity {
	private String fio;
	private String phone;
	private UserRole role;
}