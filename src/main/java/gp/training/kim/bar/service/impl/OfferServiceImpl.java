package gp.training.kim.bar.service.impl;

import gp.training.kim.bar.constant.OfferType;
import gp.training.kim.bar.converter.OfferConverter;
import gp.training.kim.bar.dbo.IngredientStoreHouseDBO;
import gp.training.kim.bar.dbo.OfferDBO;
import gp.training.kim.bar.dbo.RecipeRowDBO;
import gp.training.kim.bar.dto.OfferDTO;
import gp.training.kim.bar.dto.entity.Menu;
import gp.training.kim.bar.exception.OfferIsNotAvailableException;
import gp.training.kim.bar.repository.OfferRepository;
import gp.training.kim.bar.service.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OfferServiceImpl implements OfferService {

	final private OfferConverter offerConverter;

	final private OfferRepository offerRepository;

	@Override
	public Menu getMenu() {
		final List<OfferDBO> offers = offerRepository.findAll()
				.stream().filter(this::areNotEnoughIngredientsForTheOffer).collect(Collectors.toList());

		return new Menu(
				getOffersWithSingleOfferTypeAndConvertToDTO(offers, OfferType.BEER),
				getOffersWithSingleOfferTypeAndConvertToDTO(offers, OfferType.FOOD));
	}

	@Transactional
	@Override
	public void releaseOffers(final Map<OfferDBO, Integer> offersForRelease) throws OfferIsNotAvailableException {
		for (final Map.Entry<OfferDBO, Integer> offerAmountEntry: offersForRelease.entrySet()) {
			final OfferDBO offer = offerAmountEntry.getKey();
			for (final RecipeRowDBO recipeRow: offer.getRecipeRows()) {
				final IngredientStoreHouseDBO ingredientStoreHouse = recipeRow.getIngredient().getStorehouse();
				final BigDecimal ingredientBalanceAfterRelease = ingredientStoreHouse.getBalance()
						.subtract(recipeRow.getAmount().multiply(BigDecimal.valueOf(offerAmountEntry.getValue())));

				if (ingredientBalanceAfterRelease.compareTo(BigDecimal.ZERO) < 0) {
					throw new OfferIsNotAvailableException(offer.getName() + " is over. Please order another one.");
				}

				ingredientStoreHouse.setBalance(ingredientBalanceAfterRelease);
			}
		}

		offerRepository.saveAll(offersForRelease.keySet());
	}

	private boolean areNotEnoughIngredientsForTheOffer(final OfferDBO offer) {
		for (final RecipeRowDBO recipeRowDBO : offer.getRecipeRows()) {
			if (recipeRowDBO.getAmount().compareTo(recipeRowDBO.getIngredient().getStorehouse().getBalance()) < 0) {
				return true;
			}
		}
		return false;
	}

	private List<OfferDTO> getOffersWithSingleOfferTypeAndConvertToDTO(final List<OfferDBO> offers, final OfferType offerType) {
		return offers.stream()
				.filter(offerDBO -> offerDBO.getType().equals(offerType))
				.map(offerConverter::convertToDto).collect(Collectors.toList());
	}
}
