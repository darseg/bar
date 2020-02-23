package gp.training.kim.bar.dbo;

import gp.training.kim.bar.dbo.superclass.AbstractBarEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "ingredient")
public class IngredientDBO extends AbstractBarEntity {
	private String name;

	private BigDecimal costPrice;

	private Integer balance;

	private Integer startBalance;

	@OneToMany(mappedBy = "ingredient")
	@EqualsAndHashCode.Exclude @ToString.Exclude
	private List<RecipeRowDBO> recipeRowDBOS = new ArrayList<>();
}
