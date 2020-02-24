package gp.training.kim.bar.dbo;

import gp.training.kim.bar.constant.OfferType;
import gp.training.kim.bar.dbo.superclass.AbstractBarEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "offer")
public class OfferDBO extends AbstractBarEntity {

	private OfferType type;

	private String name;

	private String description;

	@OneToMany(mappedBy = "offer")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<OfferParamDBO> params;

	@OneToMany(mappedBy = "offer")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<OfferImageDBO> images;

	private BigDecimal price;

	@OneToMany(mappedBy = "offer",
			fetch = FetchType.EAGER,
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<RecipeRowDBO> recipeRows = new ArrayList<>();
}
