package gp.training.kim.bar.dbo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "table")
public class TableDBO extends AbstractBarEntity {
    private String table;

    private List<GuestDBO> guests;
}