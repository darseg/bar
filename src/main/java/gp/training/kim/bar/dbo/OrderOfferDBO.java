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
	private OrderOfferId id = new OrderOfferId();

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("orderId")
	private OrderDBO order;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("offerId")
	private OfferDBO offer;

	@Column(name = "amount")
	private Integer amount;
}