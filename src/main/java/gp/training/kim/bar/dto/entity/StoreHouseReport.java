package gp.training.kim.bar.dto.entity;

import gp.training.kim.bar.dto.IngredientDTO;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(includeFieldNames = true)
public class StoreHouseReport {
	private List<IngredientDTO> storeHouse;
}