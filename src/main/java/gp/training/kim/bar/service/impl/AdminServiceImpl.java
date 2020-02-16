package gp.training.kim.bar.service.impl;

import gp.training.kim.bar.controller.entity.StoreHouseReport;
import gp.training.kim.bar.converter.IngredientConverter;
import gp.training.kim.bar.dbo.IngredientDBO;
import gp.training.kim.bar.dbo.OfferDBO;
import gp.training.kim.bar.dto.IngredientDTO;
import gp.training.kim.bar.service.AdminService;
import gp.training.kim.bar.utils.BarUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final IngredientConverter ingredientConverter;

    @Override
    public StoreHouseReport getIngredientsReport() {
        final List<IngredientDTO> storeHouse = TempData.getIngredients().values().stream()
                .map(ingredientConverter::convertToDto).collect(Collectors.toList());

        final List<OfferDBO> allOffers = TempData.getTables().values().stream()
                .flatMap(tableDBO -> tableDBO.getGuests().stream())
                .flatMap(guestDBO -> guestDBO.getOrder().stream()).collect(Collectors.toList());

        final BigDecimal costPrice = allOffers.stream().flatMap(offerDBO -> offerDBO.getIngredients().stream())
                .map(IngredientDBO::getCostPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        return new StoreHouseReport(storeHouse, costPrice,
                BarUtils.getSumPriceFromDBOs(allOffers).subtract(costPrice));
    }
}
