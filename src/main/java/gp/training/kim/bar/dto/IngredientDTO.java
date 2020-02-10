package gp.training.kim.bar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class IngredientDTO {
    private final String name;

    private final Integer balance;

    private final BigDecimal costPrice;
}
