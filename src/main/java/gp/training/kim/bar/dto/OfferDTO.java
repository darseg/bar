package gp.training.kim.bar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
public class OfferDTO {
	private Long id;

	private String name;

	private String description;

	private Map<String, String> params;

	@NumberFormat(pattern="#0,00")
	private BigDecimal price;
}
