package gp.training.kim.bar.dbo;

import gp.training.kim.bar.dbo.superclass.AbstractBarEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Data
@Entity(name = "storehouse")
public class IngredientStoreHouseDBO extends AbstractBarEntity {

	@OneToOne(mappedBy = "storehouse")
	private IngredientDBO ingredient;

	private BigDecimal balance;

	private BigDecimal startBalance;
}
