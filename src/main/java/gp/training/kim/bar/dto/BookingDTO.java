package gp.training.kim.bar.dto;

import lombok.Data;

@Data
public class BookingDTO {
	final private Integer count;

	final private String date;

	final private String from;

	final private String to;

	final private Boolean barRack;
}