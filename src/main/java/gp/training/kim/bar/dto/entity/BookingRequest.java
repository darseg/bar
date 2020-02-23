package gp.training.kim.bar.dto.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import gp.training.kim.bar.constant.BarConstants;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
@ToString(includeFieldNames = true)
public class BookingRequest {
	private Integer count;

	private List<Long> registeredUsers;

	@JsonFormat(pattern=BarConstants.DATE_TIME_FORMAT)
	private LocalDateTime from;

	@JsonFormat(pattern=BarConstants.DATE_TIME_FORMAT)
	private LocalDateTime to;

	@JsonProperty(value = "barRack", required = false)
	private Boolean barRack;

	@JsonIgnore
	public Optional<Boolean> getBarRack() {
		return Optional.ofNullable(barRack);
	}
}