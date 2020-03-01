package gp.training.kim.bar.service.impl;

import gp.training.kim.bar.converter.IngredientConverter;
import gp.training.kim.bar.dbo.IngredientDBO;
import gp.training.kim.bar.dto.IngredientDTO;
import gp.training.kim.bar.dto.entity.StoreHouseReport;
import gp.training.kim.bar.repository.IngredientRepository;
import gp.training.kim.bar.service.IngredientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IngredientServiceImpl implements IngredientService {

	private final IngredientConverter ingredientConverter;
	private final IngredientRepository ingredientRepository;

	@Override
	public StoreHouseReport getIngredientsReport() {
        /*final List<IngredientDTO> storeHouse = TempData.getIngredients().values().stream()
                .map(ingredientConverter::convertToDto).collect(Collectors.toList());

        final List<OfferDBO> allOffers = TempData.getTables().values().stream()
                .flatMap(tableDBO -> tableDBO.getGuests().stream())
                .flatMap(guestDBO -> guestDBO.getOrder().stream()).collect(Collectors.toList());

        final BigDecimal costPrice = allOffers.stream().flatMap(offerDBO -> offerDBO.getIngredients().stream())
                .map(IngredientDBO::getCostPrice).reduce(BigDecimal.ZERO, BigDecimal::add);*/

		return null;
	}

	@Override
	public IngredientDTO createIngredient(IngredientDTO ingredient) {
		final IngredientDBO request = ingredientConverter.convertToDbo(ingredient);
		final IngredientDBO save = ingredientRepository.save(request);
		ingredient.setId(save.getId());
		return ingredient;
	}
}
