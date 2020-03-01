package gp.training.kim.bar.dbo.embeddable;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class OrderOfferId implements Serializable {

	@Column(name = "order_id")
	private Long orderId;

	@Column(name = "offer_id")
	private Long offerId;

	public OrderOfferId() {}
}