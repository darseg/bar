package gp.training.kim.bar.service;

import gp.training.kim.bar.dto.TableDTO;
import gp.training.kim.bar.dto.entity.BookingRequest;
import gp.training.kim.bar.dto.entity.Tables;
import gp.training.kim.bar.exception.CannotBookTableException;

import java.time.LocalDateTime;

public interface TableService {
	TableDTO book(Long tableId, BookingRequest bookingRequest) throws CannotBookTableException;

	Tables tables(Integer capacity, LocalDateTime from, LocalDateTime to, boolean isPrivate);
}
