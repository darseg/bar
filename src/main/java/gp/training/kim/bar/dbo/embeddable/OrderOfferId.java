package gp.training.kim.bar.dbo.embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderOfferId implements Serializable {

	@Column(name = "order_id")
	private Long order;

	@Column(name = "offer_id")
	private Long offer;

	private OrderOfferId() {}

	public OrderOfferId(final Long order, final Long offer) {
		this.order = order;
		this.offer = offer;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass())
			return false;

		final OrderOfferId that = (OrderOfferId) o;
		return Objects.equals(order, that.order) &&
				Objects.equals(offer, that.offer);
	}
}