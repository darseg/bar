package gp.training.kim.bar.controller.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CheckRow {
    private final BigDecimal price;

    private final Integer count;

    private final BigDecimal sum;
}