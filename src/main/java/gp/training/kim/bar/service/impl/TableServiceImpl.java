package gp.training.kim.bar.service.impl;

import gp.training.kim.bar.converter.TableConverter;
import gp.training.kim.bar.dbo.TableDBO;
import gp.training.kim.bar.dto.entity.BookingRequest;
import gp.training.kim.bar.dto.entity.Orders;
import gp.training.kim.bar.dto.entity.Tables;
import gp.training.kim.bar.exception.BarCannotBookTableException;
import gp.training.kim.bar.exception.BarOrderNotFoundException;
import gp.training.kim.bar.exception.BarUserNotFoundException;
import gp.training.kim.bar.repository.TableRepository;
import gp.training.kim.bar.service.AuthService;
import gp.training.kim.bar.service.OrderService;
import gp.training.kim.bar.service.TableService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class TableServiceImpl implements TableService {

	final AuthService authService;

	final TableRepository tableRepository;

	final TableConverter tableConverter;

	final OrderService orderService;

	@Override
	public Orders book(final Long tableId, final BookingRequest bookingRequest, final String login) throws BarCannotBookTableException, BarUserNotFoundException, BarOrderNotFoundException {
		final TableDBO table = tableRepository.findById(tableId)
				.orElseThrow(() -> new BarCannotBookTableException("Table does not exist"));

		final LocalDateTime start = bookingRequest.getStart();
		final LocalDateTime end = bookingRequest.getEnd();

		if (!table.isPrivate()) {
			if (bookingRequest.getGuestsCount() != bookingRequest.getRegisteredGuests().size() + 1) {
				throw new BarCannotBookTableException("All guests at the public table must be registered");
			}

			if (!tableRepository.getPublicTablesWithEnoughPlacesForTime(bookingRequest.getGuestsCount(), start, end).contains(table)) {
				throw new BarCannotBookTableException("Not enough places at table " + table.getName());
			}
		}

		final List<String> allUserLogins = Stream.concat(bookingRequest.getRegisteredGuests().stream(),
				Stream.of(login)).collect(Collectors.toList());
		orderService.createOrders(table, allUserLogins, start, end);

		return orderService.myOrders(login);
	}

	public Tables tables(final Integer capacity, final LocalDateTime start, final LocalDateTime end, final boolean isPrivate) {
		final List<TableDBO> tableDBOs = tableRepository.getNotReservedPrivateTablesForTime(capacity, start, end);
		if (!isPrivate) {
			tableDBOs.addAll(tableRepository.getPublicTablesWithEnoughPlacesForTime(capacity, start, end));
		}

		return new Tables(tableDBOs.stream().map(tableConverter::convertToDto).collect(Collectors.toList()));
	}
}
