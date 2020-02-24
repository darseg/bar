package gp.training.kim.bar.dto.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import gp.training.kim.bar.constant.BarConstants;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString(includeFieldNames = true)
public class BookingRequest {
	private Integer guestsCount;

	private List<String> registeredGuests;

	@JsonFormat(pattern=BarConstants.DATE_TIME_FORMAT)
	private LocalDateTime start;

	@JsonFormat(pattern=BarConstants.DATE_TIME_FORMAT)
	private LocalDateTime end;
}