package gp.training.kim.bar.dbo;

import gp.training.kim.bar.dbo.superclass.AbstractImage;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity(name = "offer_image")
@NoArgsConstructor
public class OfferImageDBO extends AbstractImage {
	@ManyToOne
	@JoinColumn(name = "offer_id")
	private OfferDBO offer;

	public OfferImageDBO(final OfferDBO offerDBO, final String imageURL) {
		setImageURL(imageURL);
		this.offer = offerDBO;
	}
}