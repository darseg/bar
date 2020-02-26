package gp.training.kim.bar.dbo;

import gp.training.kim.bar.dbo.superclass.AbstractImage;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity(name = "table_image")
@NoArgsConstructor
public class TableImageDBO extends AbstractImage {
	@ManyToOne
	@JoinColumn(name = "table_id")
	private TableDBO table;

	public TableImageDBO(final TableDBO tableDBO, final String imageURL) {
		setImageURL(imageURL);
		this.table = tableDBO;
	}
}