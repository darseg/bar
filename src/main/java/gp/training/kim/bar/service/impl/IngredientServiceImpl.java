package gp.training.kim.bar.service.impl;

import gp.training.kim.bar.converter.IngredientConverter;
import gp.training.kim.bar.dbo.IngredientDBO;
import gp.training.kim.bar.dbo.RecipeRowDBO;
import gp.training.kim.bar.dto.IngredientDTO;
import gp.training.kim.bar.dto.entity.StoreHouseReport;
import gp.training.kim.bar.repository.IngredientRepository;
import gp.training.kim.bar.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

	private final IngredientConverter ingredientConverter;

	private final IngredientRepository ingredientRepository;

	@Override
	public StoreHouseReport getIngredientsReport() {
		final List<IngredientDBO> ingredients = ingredientRepository.findAll();

		final StoreHouseReport storeHouseReport = new StoreHouseReport();
		storeHouseReport.setStoreHouse(ingredients.stream()
				.map(ingredientDBO -> {
					final IngredientDTO ingredientDTO = ingredientConverter.convertToDto(ingredientDBO);
					final Map<Long, BigDecimal> usedIn = new HashMap<>();
					for (RecipeRowDBO recipeRow : ingredientDBO.getRecipeRows()) {
						usedIn.put(recipeRow.getOffer().getId(), recipeRow.getAmount());
					}
					ingredientDTO.setUsedIn(usedIn);

					return ingredientDTO;
				})
				.collect(Collectors.toList()));

		return storeHouseReport;
	}
}
