package gp.training.kim.bar.dto.entity;

import gp.training.kim.bar.dto.TableDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@ToString(includeFieldNames = true)
public class Tables {
	private List<TableDTO> tables;
}
