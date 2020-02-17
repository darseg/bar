package gp.training.kim.bar.dto.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class TableCheck {
    final private Check table;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    final private Map<Long, Check> visitors;
}