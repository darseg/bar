package gp.training.kim.bar.converter;

import gp.training.kim.bar.dbo.IngredientDBO;
import gp.training.kim.bar.dto.IngredientDTO;
import org.springframework.stereotype.Service;

@Service
public class IngredientConverter extends AbstractConverter<IngredientDBO, IngredientDTO> {
	@Override
	public IngredientDTO convertToDto(final IngredientDBO IngredientDBO) {
		return new IngredientDTO(IngredientDBO.getName(), IngredientDBO.getBalance(), IngredientDBO.getCostPrice());
	}

	@Override
	public IngredientDBO convertToDbo(IngredientDTO IngredientDTO) {

		return null;
	}
}
