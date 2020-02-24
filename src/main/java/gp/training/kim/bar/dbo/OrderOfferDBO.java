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
import javax.persistence.Table;
import java.util.Objects;

@Data
@Entity(name = "order_offer")
@NoArgsConstructor
public class OrderOfferDBO {

	@EmbeddedId
	private OrderOfferId id = new OrderOfferId();

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("orderId")
	private OrderDBO order;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("offerId")
	private OfferDBO offer;

	@Column(name = "amount")
	private Integer amount;

	public OrderOfferDBO(final OrderDBO order, final OfferDBO offer) {
		this.order = order;
		this.offer = offer;
		this.id = new OrderOfferId(order.getId(), offer.getId());
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass())
			return false;

		final OrderOfferDBO that = (OrderOfferDBO) o;
		return Objects.equals(order, that.order) &&
				Objects.equals(offer, that.offer);
	}

	@Override
	public int hashCode() {
		return Objects.hash(order, offer);
	}
}