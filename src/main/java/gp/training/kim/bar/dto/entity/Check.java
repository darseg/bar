package gp.training.kim.bar.dto.entity;

import gp.training.kim.bar.constant.BarConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
@ToString(includeFieldNames = true)
public class Check {
	private Map<String, CheckRow> details;

	@NumberFormat(pattern = BarConstants.PRICE_FORMAT)
	private BigDecimal price;
}