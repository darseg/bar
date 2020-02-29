package gp.training.kim.bar.mockData;

import gp.training.kim.bar.constant.OfferType;
import gp.training.kim.bar.dbo.IngredientDBO;
import gp.training.kim.bar.dbo.OfferDBO;
import gp.training.kim.bar.dbo.OfferImageDBO;
import gp.training.kim.bar.dbo.OfferParamDBO;
import gp.training.kim.bar.dbo.RecipeRowDBO;
import gp.training.kim.bar.dbo.embeddable.RecipeRowId;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Offer {
	private final static long beerCount = Ingredient.beerCount;
	private final static Map<Long, IngredientDBO> ingredients = Ingredient.ingredients;

	public final static Map<Long, OfferDBO> offers = new HashMap<>() {{
		long innerId = 1;
		for (long i = 1; i <= beerCount + 12; i++) {
			final OfferDBO offer = new OfferDBO();

			final List<OfferImageDBO> images = new ArrayList<>();
			for (long j = 1; j <= (i % 2) + 1; j++) {
				final OfferImageDBO image = new OfferImageDBO();
				image.setId(innerId);
				image.setImageURL("images/offer/" + i + "/" + j + ".jpg");
				image.setOffer(offer);

				images.add(image);
				innerId++;
			}
			offer.setImages(images);

			final List<OfferParamDBO> offerParams = new ArrayList<>();
			innerId = 1;
			for (long j = 1; j <= (i % 3) + 1; j++) {
				final OfferParamDBO offerParam = new OfferParamDBO();
				offerParam.setId(innerId);
				offerParam.setName("Param " + j);
				offerParam.setValue(innerId % 7 + "." + innerId % 3);
				offerParam.setOffer(offer);
				offerParams.add(offerParam);

				innerId++;
			}
			offer.setParams(offerParams);

			if (i <= beerCount) {
				offer.setType(OfferType.BEER);
				offer.setName("Beer " + i);
				offer.setDescription("Beer " + i + " description");

				final RecipeRowDBO recipeRow = new RecipeRowDBO();
				recipeRow.setAmount(BigDecimal.valueOf(0.5));
				recipeRow.setId(new RecipeRowId(i, i));
				recipeRow.setIngredient(ingredients.get(i));
				recipeRow.setOffer(offer);

				offer.setRecipeRows(Collections.singletonList(recipeRow));
			} else {
				offer.setType(OfferType.FOOD);
				offer.setName("Food " + (i - beerCount));
				offer.setDescription("Food " + (i - beerCount) + " description");

				final long count = i % 3 + 1;
				final List<RecipeRowDBO> recipeRows = new ArrayList<>();
				for (long j = 0; j < count; j++) {
					final RecipeRowDBO recipeRow = new RecipeRowDBO();
					recipeRow.setAmount(BigDecimal.valueOf(5).multiply(BigDecimal.valueOf(1 + i % 2)));
					recipeRow.setOffer(offer);
					final Long ingId = getIngredientId(i, count, j);
					recipeRow.setId(new RecipeRowId(i, ingId));
					recipeRow.setIngredient(ingredients.get(ingId));

					recipeRows.add(recipeRow);
				}
				offer.setRecipeRows(recipeRows);
			}

			offer.setPrice(offer.getRecipeRows().stream()
					.map(recipeRowDBO -> recipeRowDBO.getAmount().multiply(recipeRowDBO.getIngredient().getCostPrice()))
					.reduce(BigDecimal.ZERO, (bigDecimal, augend) -> bigDecimal.add(augend).multiply(BigDecimal.valueOf(0.4))));

			offer.setId(i);
			put(i, offer);
		}
	}};

	private static Long getIngredientId(final Long offerId, final long count, final long num) {
		final long nonBeerIngCount = ingredients.size() - beerCount;

		return beerCount + nonBeerIngCount % count * num;
	}
}
