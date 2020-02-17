package gp.training.kim.bar.dbo;

import gp.training.kim.bar.enums.OfferType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Entity(name = "offer")
public class OfferDBO extends AbstractBarEntity {

    private OfferType type;

    private String name;

    private String description;

    @OneToMany(mappedBy = "offer")
    private List<OfferParamDBO> params;

    private BigDecimal price;

    @OneToMany(mappedBy = "offer")
    private List<RecipeRowDBO> recipeRows = new ArrayList<>();

    @ManyToMany(mappedBy = "offers")
    List<OrderDBO> orders;
}
