package gp.training.kim.bar.dbo.superclass;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class AbstractImage extends AbstractBarEntity {
	@Column(name = "image_url")
	private String imageURL;
}