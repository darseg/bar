package gp.training.kim.bar.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(includeFieldNames = true)
public class TableDTO {
	private String name;

	private String description;

	private List<String> images;

	private Integer capacity;

	private boolean isPrivate;
}
