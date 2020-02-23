package gp.training.kim.bar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
public class OfferDTO {
	private Long id;

	private String name;

	private String description;

	private Map<String, String> params;

	private BigDecimal price;
}
