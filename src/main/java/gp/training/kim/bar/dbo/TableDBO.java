package gp.training.kim.bar.dbo;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import java.util.List;

@Data
@Entity(name = "table")
public class TableDBO extends AbstractBarEntity {
    private String table;

    private List<GuestDBO> guests;
}