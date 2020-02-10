package gp.training.kim.bar.dbo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TableDBO {
    final Integer id;

    final String table;

    final List<VisitorDBO> visitors;
}