package gp.training.kim.bar.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class OfferDTO {
	final private Long id;

	final private String name;

	final private String description;

	final private Map<String, String> params;

	final private BigDecimal price;
}
