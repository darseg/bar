package gp.training.kim.bar.dto.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

import static gp.training.kim.bar.constant.BarConstants.DATE_TIME_FORMAT;

@Data
@ToString(includeFieldNames = true)
public class BookingRequest {
	private Integer guestsCount;

	private List<String> registeredGuests;

	@JsonFormat(pattern = DATE_TIME_FORMAT)
	private LocalDateTime start;

	@JsonFormat(pattern = DATE_TIME_FORMAT)
	private LocalDateTime end;
}