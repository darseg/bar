package gp.training.kim.bar.dbo;

import gp.training.kim.bar.dbo.embeddable.OrderOfferId;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Data
@Entity(name = "order_offer")
@NoArgsConstructor
public class OrderOfferDBO {

	@EmbeddedId
	private OrderOfferId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("order")
	private OrderDBO order;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("offer")
	private OfferDBO offer;

	@Column(name = "amount")
	private Integer amount;

	public OrderOfferDBO(final OrderDBO order, final OfferDBO offer, final Integer amount) {
		this.order = order;
		this.offer = offer;
		this.amount = amount;
		this.id = new OrderOfferId(order.getId(), offer.getId());
	}
}