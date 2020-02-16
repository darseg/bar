package gp.training.kim.bar.dbo;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Data
@Entity(name = "ingredient")
@Builder
public class IngredientDBO extends AbstractBarEntity {
    private String name;

    private BigDecimal costPrice;

    private Integer balance;

    private Integer startBalance;
}
