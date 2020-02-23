package gp.training.kim.bar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@ToString(includeFieldNames = true)
public class IngredientDTO {
	private String name;

	private Integer balance;

	private BigDecimal costPrice;
}
