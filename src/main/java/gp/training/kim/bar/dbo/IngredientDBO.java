package gp.training.kim.bar.dbo;

import gp.training.kim.bar.dbo.superclass.AbstractBarEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "ingredient")
public class IngredientDBO extends AbstractBarEntity {
	private String name;

	private BigDecimal costPrice;

	@OneToOne(optional = false)
	@JoinColumn(name = "id", referencedColumnName = "ingredient_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private IngredientStoreHouseDBO storehouse;

	@OneToMany(mappedBy = "ingredient", fetch = FetchType.LAZY)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<RecipeRowDBO> recipeRows = new ArrayList<>();
}
