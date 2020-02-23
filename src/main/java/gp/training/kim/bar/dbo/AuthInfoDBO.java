package gp.training.kim.bar.dbo;

import gp.training.kim.bar.dbo.superclass.AbstractBarEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "auth_info")
public class AuthInfoDBO extends AbstractBarEntity {

	private String login;

	private String password;

	@OneToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private UserDBO user;
}