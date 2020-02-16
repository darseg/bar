package gp.training.kim.bar.dbo;

import gp.training.kim.bar.enums.UserRole;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity(name = "user")
public class UserDBO extends AbstractBarEntity {
    private String fio;
    private String phone;
    private UserRole userRole;
}