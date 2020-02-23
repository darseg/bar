package gp.training.kim.bar.dto.entity;

import gp.training.kim.bar.dto.TableDTO;
import gp.training.kim.bar.dto.UserDTO;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(includeFieldNames = true)
public class BookingResponse {
	private TableDTO table;

	private List<UserDTO> users;
}