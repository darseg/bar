package gp.training.kim.bar.dbo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class IngredientDBO {
    private final Integer id;

    private final String name;

    private final BigDecimal costPrice;

    private final Integer balance;

    private final Integer startBalance;
}
