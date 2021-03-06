package gp.training.kim.bar.dbo;

import gp.training.kim.bar.dbo.superclass.AbstractBarEntity;
import gp.training.kim.bar.repository.util.LocalDateTimeAttributeConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "orders")
public class OrderDBO extends AbstractBarEntity {
	@OneToOne(optional = false)
	@JoinColumn(name = "table_id", nullable = false)
	private TableDBO table;

	@OneToOne
	@JoinColumn(name = "user_id")
	private UserDBO user;

	private boolean paid;

	@Convert(converter = LocalDateTimeAttributeConverter.class)
	private LocalDateTime start;

	@Convert(converter = LocalDateTimeAttributeConverter.class)
	private LocalDateTime end;

	@OneToMany(mappedBy = "order",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@NotNull
	private List<OrderOfferDBO> orderOffers = new ArrayList<>();
}