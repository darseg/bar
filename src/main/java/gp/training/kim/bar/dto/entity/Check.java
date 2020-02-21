package gp.training.kim.bar.dto.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class Check {
	final private Map<String, CheckRow> details;

	final private BigDecimal price;
}