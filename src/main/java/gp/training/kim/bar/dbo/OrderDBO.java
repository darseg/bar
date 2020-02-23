package gp.training.kim.bar.dbo;

import gp.training.kim.bar.dbo.superclass.AbstractBarEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
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

	@ManyToMany
	@JoinTable(
			name = "order_offer",
			joinColumns = @JoinColumn(name = "order_id"),
			inverseJoinColumns = @JoinColumn(name = "offer_id"))
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<OfferDBO> offers;

	private LocalDateTime from;

	private LocalDateTime to;

	private boolean paid;
}