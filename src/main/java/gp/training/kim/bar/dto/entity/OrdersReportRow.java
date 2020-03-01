package gp.training.kim.bar.dto.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import gp.training.kim.bar.constant.BarConstants;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static gp.training.kim.bar.constant.BarConstants.DATE_TIME_FORMAT;

@Data
public class OrdersReportRow {
	private Long id;

	private String table;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String user;

	@NumberFormat(pattern = BarConstants.PRICE_FORMAT)
	private BigDecimal price;

	@JsonFormat(pattern = DATE_TIME_FORMAT)
	private LocalDateTime start;

	@JsonFormat(pattern = DATE_TIME_FORMAT)
	private LocalDateTime end;
}
