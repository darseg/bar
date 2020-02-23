package gp.training.kim.bar.dbo;

import gp.training.kim.bar.dbo.superclass.AbstractImage;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity(name = "offer_image")
public class OfferImageDBO extends AbstractImage {
	@ManyToOne
	@JoinColumn(name = "offer_id")
	private OfferDBO offer;
}