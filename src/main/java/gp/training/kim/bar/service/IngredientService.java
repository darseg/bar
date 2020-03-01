package gp.training.kim.bar.service;

import gp.training.kim.bar.dto.IngredientDTO;
import gp.training.kim.bar.dto.entity.StoreHouseReport;

public interface IngredientService {
	StoreHouseReport getIngredientsReport();

    IngredientDTO createIngredient(IngredientDTO ingredient);
}
