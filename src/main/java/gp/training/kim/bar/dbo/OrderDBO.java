package gp.training.kim.bar.dbo;

import gp.training.kim.bar.dbo.superclass.AbstractBarEntity;
import gp.training.kim.bar.repository.util.LocalDateTimeAttributeConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "table_id", nullable = false)
	private TableDBO table;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserDBO user;

	@OneToMany(mappedBy = "offer")
	@EqualsAndHashCode.Exclude @ToString.Exclude
	@NotNull
	private List<OrderOfferDBO> orderOffers = new ArrayList<>();

	@Convert(converter = LocalDateTimeAttributeConverter.class)
	private LocalDateTime start;

	@Convert(converter = LocalDateTimeAttributeConverter.class)
	private LocalDateTime end;

	private boolean paid;
}