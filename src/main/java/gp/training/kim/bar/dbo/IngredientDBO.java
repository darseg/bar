package gp.training.kim.bar.dbo;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "ingredient")
@Builder
public class IngredientDBO extends AbstractBarEntity {
    private String name;

    private BigDecimal costPrice;

    private Integer balance;

    private Integer startBalance;
}
