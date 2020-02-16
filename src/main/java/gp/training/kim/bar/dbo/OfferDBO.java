package gp.training.kim.bar.dbo;

import gp.training.kim.bar.enums.OfferType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "offer")
public class OfferDBO extends AbstractBarEntity {

    private OfferType type;

    private String name;

    private String description;

    private Map<String, String> params;

    private BigDecimal price;

    private List<IngredientDBO> ingredients;
}
