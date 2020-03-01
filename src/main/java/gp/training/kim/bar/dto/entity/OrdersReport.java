package gp.training.kim.bar.dto.entity;

import lombok.Data;

import java.util.List;

@Data
public class OrdersReport {
	private List<OrdersReportRow> orders;
}
