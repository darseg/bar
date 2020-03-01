package gp.training.kim.bar.service;

import gp.training.kim.bar.dto.entity.BookingRequest;
import gp.training.kim.bar.dto.entity.Orders;
import gp.training.kim.bar.dto.entity.Tables;
import gp.training.kim.bar.exception.BarCannotBookTableException;
import gp.training.kim.bar.exception.BarOrderNotFoundException;
import gp.training.kim.bar.exception.BarUserNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface TableService {
	@Transactional
	Orders book(Long tableId, BookingRequest bookingRequest, String login) throws BarCannotBookTableException, BarUserNotFoundException, BarOrderNotFoundException;

	Tables tables(Integer capacity, LocalDateTime start, LocalDateTime end, boolean isPrivate);
}
