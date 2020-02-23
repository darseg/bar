package gp.training.kim.bar.service.impl;

import gp.training.kim.bar.constant.OfferType;
import gp.training.kim.bar.converter.OfferConverter;
import gp.training.kim.bar.dbo.OfferDBO;
import gp.training.kim.bar.dbo.RecipeRowDBO;
import gp.training.kim.bar.dto.OfferDTO;
import gp.training.kim.bar.dto.entity.Menu;
import gp.training.kim.bar.repository.OfferRepository;
import gp.training.kim.bar.service.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OfferServiceImpl implements OfferService {

	final private OfferConverter offerConverter;

	final private OfferRepository offerRepository;

	@Override
	public Menu getMenu() {
		final List<OfferDBO> offers = offerRepository.findAll().stream().filter(this::areNotEnoughIngredientsForTheOffer).collect(Collectors.toList());
		return new Menu(getOffersWithSingleOfferTypeAndConvertToDTO(offers, OfferType.BEER), getOffersWithSingleOfferTypeAndConvertToDTO(offers, OfferType.FOOD));
	}

	private boolean areNotEnoughIngredientsForTheOffer(final OfferDBO offer) {
		for (final RecipeRowDBO recipeRowDBO : offer.getRecipeRows()) {
			if (recipeRowDBO.getAmount().compareTo(recipeRowDBO.getIngredient().getBalance()) < 0) {
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
