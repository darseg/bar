package gp.training.kim.bar.dbo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class OfferDBO {
    private final Integer id;

    private final String name;

    private final String description;

    private final Map<String, String> params;

    private final BigDecimal price;

    private final List<IngredientDBO> ingredients;
}
