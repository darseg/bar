package gp.training.kim.bar.dbo.embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class OrderOfferId implements Serializable {

	@Column(name = "order_id")
	private Long order;

	@Column(name = "offer_id")
	private Long offer;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass())
			return false;

		OrderOfferId that = (OrderOfferId) o;
		return Objects.equals(order, that.order) &&
				Objects.equals(offer, that.offer);
	}
}