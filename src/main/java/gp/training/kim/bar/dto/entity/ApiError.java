package gp.training.kim.bar.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ApiError {

	@Getter
	private HttpStatus status;

	private Integer code;

	private String message;
}
