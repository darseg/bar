package gp.training.kim.bar.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@ToString(includeFieldNames = true)
public class CheckRow {
	private BigDecimal price;

	private Integer count;

	private BigDecimal sum;
}