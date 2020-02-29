package gp.training.kim.bar.dto.entity;

import gp.training.kim.bar.constant.BarConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@ToString(includeFieldNames = true)
public class CheckRow {
	@NumberFormat(pattern = BarConstants.PRICE_FORMAT)
	private BigDecimal price;

	private Integer count;

	@NumberFormat(pattern = BarConstants.PRICE_FORMAT)
	private BigDecimal sum;
}