package gp.training.kim.bar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import gp.training.kim.bar.constant.BarConstants;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Data
@NoArgsConstructor
public class OfferDTO {
	private Long id;

	private String type;

	private String name;

	private String description;

	@NumberFormat(pattern = BarConstants.PRICE_FORMAT)
	private BigDecimal price;

	private Map<String, String> params;

	private List<String> images;

	@JsonInclude(NON_EMPTY)
	private Map<Long, BigDecimal> ingredients;
}
