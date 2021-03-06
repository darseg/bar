package gp.training.kim.bar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Data
@ToString(includeFieldNames = true)
public class IngredientDTO {
	private Long id;

	private String name;

	private BigDecimal balance;

	private BigDecimal costPrice;

	@JsonInclude(NON_EMPTY)
	private Map<Long, BigDecimal> usedIn;
}
