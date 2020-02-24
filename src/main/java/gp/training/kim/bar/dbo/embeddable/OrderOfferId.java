package gp.training.kim.bar.dbo.embeddable;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class OrderOfferId implements Serializable {

	@Column(name = "order_id")
	private Long orderId;

	@Column(name = "offer_id")
	private Long offerId;

	public OrderOfferId() {}

	public OrderOfferId(final Long orderId, final Long offerId) {
		this.orderId = orderId;
		this.offerId = offerId;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass())
			return false;

		final OrderOfferId that = (OrderOfferId) o;
		return Objects.equals(orderId, that.orderId) &&
				Objects.equals(offerId, that.offerId);
	}
}