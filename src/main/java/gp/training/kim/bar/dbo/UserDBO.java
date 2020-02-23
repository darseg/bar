package gp.training.kim.bar.dbo;

import gp.training.kim.bar.constant.UserRole;
import gp.training.kim.bar.dbo.superclass.AbstractBarEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class UserDBO extends AbstractBarEntity {

	private String login;

	private String fio;

	private String phone;

	private UserRole role;
}