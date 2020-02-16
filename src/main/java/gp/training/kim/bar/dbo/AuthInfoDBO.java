package gp.training.kim.bar.dbo;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity(name = "auth_info")
public class AuthInfoDBO extends AbstractBarEntity {
    private String login;
    private String password;
    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserDBO user;

}