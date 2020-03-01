package gp.training.kim.bar.service.impl;

import gp.training.kim.bar.converter.OfferConverter;
import gp.training.kim.bar.dbo.IngredientDBO;
import gp.training.kim.bar.dbo.IngredientStoreHouseDBO;
import gp.training.kim.bar.dbo.OfferDBO;
import gp.training.kim.bar.dbo.RecipeRowDBO;
import gp.training.kim.bar.dbo.embeddable.RecipeRowId;
import gp.training.kim.bar.dto.OfferDTO;
import gp.training.kim.bar.dto.entity.Menu;
import gp.training.kim.bar.exception.BarCannotCreateEntity;
import gp.training.kim.bar.exception.BarOfferIsNotAvailableException;
import gp.training.kim.bar.repository.OfferRepository;
import gp.training.kim.bar.service.IngredientService;
import gp.training.kim.bar.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

	private final OfferConverter offerConverter;

	private final OfferRepository offerRepository;

	private final IngredientService ingredientService;

	@Override
	public Menu getMenu() {
		final Map<String, List<OfferDTO>> offers = offerRepository.findAll().stream()
				.filter(offer -> !areNotEnoughIngredientsForTheOffer(offer))
				.map(offerConverter::convertToDto)
				.collect(Collectors.groupingBy(OfferDTO::getType));

		return new Menu(offers);
	}

	@Transactional
	@Override
	public void releaseOffers(final Map<OfferDBO, Integer> offersForRelease) throws BarOfferIsNotAvailableException {
		for (final Map.Entry<OfferDBO, Integer> offerAmountEntry: offersForRelease.entrySet()) {
			final OfferDBO offer = offerAmountEntry.getKey();
			for (final RecipeRowDBO recipeRow: offer.getRecipeRows()) {
				final IngredientStoreHouseDBO ingredientStoreHouse = recipeRow.getIngredient().getStorehouse();
				final BigDecimal ingredientBalanceAfterRelease = ingredientStoreHouse.getBalance()
						.subtract(recipeRow.getAmount().multiply(BigDecimal.valueOf(offerAmountEntry.getValue())));

				if (ingredientBalanceAfterRelease.compareTo(BigDecimal.ZERO) < 0) {
					throw new BarOfferIsNotAvailableException(offer.getName() + " is over. Please order another one.");
				}

				ingredientStoreHouse.setBalance(ingredientBalanceAfterRelease);
			}
		}

		offerRepository.saveAll(offersForRelease.keySet());
	}

	@Override
	public List<OfferDTO> getOffersReport() {
		final List<OfferDBO> offers = offerRepository.findAll();

		return offers.stream()
				.map(offerDBO -> {
					final OfferDTO offerDTO = offerConverter.convertToDto(offerDBO);
					offerDTO.setIngredients(convertRecipeRowsToDTO(offerDBO.getRecipeRows()));

					return offerDTO;
				})
				.collect(Collectors.toList());
	}

	@Override
	public OfferDTO addOffer(final OfferDTO offerDTO) throws BarCannotCreateEntity {
		final OfferDBO offer = offerConverter.convertToDbo(offerDTO);
		final Map<Long, IngredientDBO> ingredientDBOs = ingredientService.getAllIngredientsByIds(offerDTO.getIngredients().keySet());
		for (final Map.Entry<Long, BigDecimal> ingredientRow: offerDTO.getIngredients().entrySet()) {
			final IngredientDBO ingredient = ingredientDBOs.get(ingredientRow.getKey());
			final RecipeRowDBO recipeRow = new RecipeRowDBO();
			recipeRow.setAmount(ingredientRow.getValue());
			recipeRow.setIngredient(ingredient);
			recipeRow.setOffer(offer);
			recipeRow.setId(new RecipeRowId(offer.getId(), ingredient.getId()));

			offer.getRecipeRows().add(recipeRow);
		}

		final OfferDBO savedOffer = offerRepository.save(offer);
		final OfferDTO resultDTO = offerConverter.convertToDto(savedOffer);
		resultDTO.setIngredients(convertRecipeRowsToDTO(savedOffer.getRecipeRows()));

		return resultDTO;
	}

	final Map<Long, BigDecimal> convertRecipeRowsToDTO(final List<RecipeRowDBO> recipeRows) {
		final Map<Long, BigDecimal> ingredients = new HashMap<>();
		for (final RecipeRowDBO recipeRowDBO : recipeRows) {
			ingredients.put(recipeRowDBO.getIngredient().getId(), recipeRowDBO.getAmount());
		}

		return ingredients;
	}

	private boolean areNotEnoughIngredientsForTheOffer(final OfferDBO offer) {
		for (final RecipeRowDBO recipeRowDBO : offer.getRecipeRows()) {
			if (recipeRowDBO.getAmount().compareTo(recipeRowDBO.getIngredient().getStorehouse().getBalance()) >= 0) {
				return true;
			}
		}
		return false;
	}
}
