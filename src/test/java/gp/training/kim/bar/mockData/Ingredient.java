package gp.training.kim.bar.mockData;

import gp.training.kim.bar.dbo.IngredientDBO;
import gp.training.kim.bar.dbo.IngredientStoreHouseDBO;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Ingredient {
	public final static long beerCount = 6L;

	public final static Map<Long, IngredientDBO> ingredients = new HashMap<>() {{
		for (long i = 1; i <= beerCount; i++) {
			final IngredientDBO ing = new IngredientDBO();
			ing.setName("Beer " + i);
			ing.setCostPrice(BigDecimal.valueOf(10).multiply(BigDecimal.valueOf((i % 3)).add(BigDecimal.valueOf(9))));

			final IngredientStoreHouseDBO stored = new IngredientStoreHouseDBO();
			final BigDecimal balance = BigDecimal.valueOf(5 + 10 / (i % 4 + 1));
			stored.setBalance(balance);
			stored.setStartBalance(balance);
			stored.setIngredient(ing);

			ing.setStorehouse(stored);

			put(i, ing);
		}

		for (long i = beerCount + 1; i <= beerCount + 16; i++) {
			final IngredientDBO ing = new IngredientDBO();
			ing.setName("Ingredient " + i);
			ing.setCostPrice(BigDecimal.valueOf(7).multiply(BigDecimal.valueOf((i % 4)).add(BigDecimal.valueOf(9))));

			final IngredientStoreHouseDBO stored = new IngredientStoreHouseDBO();
			final BigDecimal balance = BigDecimal.valueOf(8 + 10 / (i % 3 + 1));
			stored.setBalance(balance);
			stored.setStartBalance(balance);
			stored.setIngredient(ing);

			ing.setStorehouse(stored);

			put(i, ing);
		}
	}};

}
