package gp.training.kim.bar.controller.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class TableCheck {
    final private Check table;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    final private Map<Integer, Check> visitors;
}