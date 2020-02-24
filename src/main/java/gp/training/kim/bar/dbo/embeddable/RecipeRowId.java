package gp.training.kim.bar.dbo.embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class RecipeRowId implements Serializable {

	@Column(name = "offer_id")
	private Long offer;

	@Column(name = "ingredient_id")
	private Long ingredient;

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass())
			return false;

		final RecipeRowId that = (RecipeRowId) o;
		return Objects.equals(offer, that.offer) &&
				Objects.equals(ingredient, that.ingredient);
	}
}