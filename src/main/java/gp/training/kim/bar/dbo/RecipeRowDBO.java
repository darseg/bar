package gp.training.kim.bar.dbo;

import gp.training.kim.bar.dbo.embeddable.RecipeRowId;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Data
@Entity(name = "recipe_row")
@NoArgsConstructor
public class RecipeRowDBO {

    @EmbeddedId
    private RecipeRowId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("offer")
    private OfferDBO offer;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ingredient")
    private IngredientDBO ingredient;

    @Column(name = "count")
    private Integer count;

    public RecipeRowDBO(OfferDBO offer, IngredientDBO ingredient, Integer count) {
        this.offer = offer;
        this.ingredient = ingredient;
        this.count = count;
        this.id = new RecipeRowId(offer.getId(), ingredient.getId());
    }
}