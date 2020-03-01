package gp.training.kim.bar.dbo.embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class RecipeRowId implements Serializable {

	@Column(name = "offer_id")
	private Long offer;

	@Column(name = "ingredient_id")
	private Long ingredient;
}