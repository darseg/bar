package gp.training.kim.bar.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString(includeFieldNames = true)
public class IngredientDTO {
	private String name;

	private BigDecimal balance;

	private BigDecimal costPrice;
}
