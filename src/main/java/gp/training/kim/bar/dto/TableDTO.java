package gp.training.kim.bar.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TableDTO {
    final String table;

    final List<Integer> visitors;
}
