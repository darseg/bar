package gp.training.kim.bar.service;

import gp.training.kim.bar.dto.TableDTO;
import gp.training.kim.bar.dto.entity.BookingRequest;
import gp.training.kim.bar.dto.entity.Tables;

import java.time.LocalDateTime;

public interface UserService {
	TableDTO book(BookingRequest bookingRequest);

	Tables tables(Integer capacity, LocalDateTime from, LocalDateTime to, boolean isPrivate);
}
