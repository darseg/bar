package gp.training.kim.bar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@ToString(includeFieldNames = true)
public class BookingDTO {
	private Integer count;

	private List<Long> registeredUsers;

	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate date;

	@JsonFormat(pattern="HH:mm")
	private LocalTime from;

	@JsonFormat(pattern="HH:mm")
	private LocalTime to;

	private Boolean barRack;
}