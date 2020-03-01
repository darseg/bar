package gp.training.kim.bar.service;

import gp.training.kim.bar.dbo.IngredientDBO;
import gp.training.kim.bar.dto.entity.StoreHouseReport;
import gp.training.kim.bar.exception.BarCannotCreateEntity;

import java.util.Map;

public interface IngredientService {
	StoreHouseReport getIngredientsReport();

	Map<Long, IngredientDBO> getAllIngredientsByIds(Iterable<Long> keySet) throws BarCannotCreateEntity;
}
