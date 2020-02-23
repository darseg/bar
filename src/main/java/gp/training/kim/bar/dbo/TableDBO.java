package gp.training.kim.bar.dbo;

import gp.training.kim.bar.dbo.superclass.AbstractBarEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity(name = "tables")
public class TableDBO extends AbstractBarEntity {
	private String name;

	private String description;

	@OneToMany(mappedBy = "table")
	@EqualsAndHashCode.Exclude @ToString.Exclude
	private List<TableImageDBO> images;

	private Integer capacity;

	private boolean isPrivate;
}