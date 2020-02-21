package gp.training.kim.bar.service.impl;

import gp.training.kim.bar.converter.IngredientConverter;
import gp.training.kim.bar.dto.entity.StoreHouseReport;
import gp.training.kim.bar.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final IngredientConverter ingredientConverter;

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
}
