package gp.training.kim.bar.service;

import gp.training.kim.bar.dto.entity.BookingRequest;
import gp.training.kim.bar.dto.entity.Orders;
import gp.training.kim.bar.dto.entity.Tables;
import gp.training.kim.bar.exception.CannotBookTableException;
import gp.training.kim.bar.exception.UserNotFoundException;

import java.time.LocalDateTime;

public interface TableService {
	Orders book(Long tableId, BookingRequest bookingRequest, String login) throws CannotBookTableException, UserNotFoundException;

	Tables tables(Integer capacity, LocalDateTime start, LocalDateTime end, boolean isPrivate);
}
