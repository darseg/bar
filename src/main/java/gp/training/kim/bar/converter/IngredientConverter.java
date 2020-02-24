package gp.training.kim.bar.converter;

import gp.training.kim.bar.dbo.IngredientDBO;
import gp.training.kim.bar.dbo.IngredientStoreHouseDBO;
import gp.training.kim.bar.dto.IngredientDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class IngredientConverter extends AbstractConverter<IngredientDBO, IngredientDTO> {
	public IngredientConverter() {
		super(new String[]{"balance", "recipeRows", "storehouse"});
	}

	@Override
	protected IngredientDTO constructDto() {
		return new IngredientDTO();
	}

	@Override
	protected IngredientDBO constructDbo() {
		return new IngredientDBO();
	}

	@Override
	public IngredientDTO convertToDto(final IngredientDBO ingredientDBO) {
		final IngredientDTO ingredientDTO = super.convertToDto(ingredientDBO);

		ingredientDTO.setBalance(ingredientDBO.getStorehouse().getBalance());

		return ingredientDTO;
	}

	@Override
	public IngredientDBO convertToDbo(final IngredientDTO ingredientDTO) {
		final IngredientDBO ingredientDBO = super.convertToDbo(ingredientDTO);

		final IngredientStoreHouseDBO ingredientStoreHouseDBO = new IngredientStoreHouseDBO();
		final BigDecimal balance = ingredientDTO.getBalance();
		ingredientStoreHouseDBO.setBalance(balance);
		ingredientStoreHouseDBO.setStartBalance(balance);
		ingredientDBO.setStorehouse(ingredientStoreHouseDBO);

		return ingredientDBO;
	}
}
