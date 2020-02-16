package gp.training.kim.bar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Data;

@Data
public class UserSignUpRequest {

    private String login;
    private String password;
    private String fio;
    private String phone;
}