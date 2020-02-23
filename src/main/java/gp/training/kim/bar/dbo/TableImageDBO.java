package gp.training.kim.bar.dbo;

import gp.training.kim.bar.dbo.superclass.AbstractImage;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity(name = "table_image")
public class TableImageDBO extends AbstractImage {
	@ManyToOne
	@JoinColumn(name = "table_id")
	private TableDBO table;
}