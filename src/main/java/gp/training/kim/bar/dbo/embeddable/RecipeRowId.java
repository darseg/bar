package gp.training.kim.bar.dbo.embeddable;

import lombok.AllArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
public class RecipeRowId implements Serializable {

    @Column(name = "offer_id")
    private Long offer;

    @Column(name = "ingredient_id")
    private Long ingredient;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        RecipeRowId that = (RecipeRowId) o;
        return Objects.equals(offer, that.offer) &&
                Objects.equals(ingredient, that.ingredient);
    }
}