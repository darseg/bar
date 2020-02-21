package gp.training.kim.bar.dto.entity;

import gp.training.kim.bar.dto.IngredientDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class StoreHouseReport {
	final private List<IngredientDTO> storeHouse;

	final private BigDecimal costPrice;

	final private BigDecimal profit;
}