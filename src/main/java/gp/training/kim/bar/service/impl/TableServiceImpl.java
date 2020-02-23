package gp.training.kim.bar.service.impl;

import gp.training.kim.bar.converter.TableConverter;
import gp.training.kim.bar.converter.UserConverter;
import gp.training.kim.bar.dbo.OrderDBO;
import gp.training.kim.bar.dbo.TableDBO;
import gp.training.kim.bar.dbo.UserDBO;
import gp.training.kim.bar.dto.TableDTO;
import gp.training.kim.bar.dto.entity.BookingRequest;
import gp.training.kim.bar.dto.entity.Tables;
import gp.training.kim.bar.exception.CannotBookTableException;
import gp.training.kim.bar.repository.OrderRepository;
import gp.training.kim.bar.repository.TableRepository;
import gp.training.kim.bar.repository.UserRepository;
import gp.training.kim.bar.service.OrderService;
import gp.training.kim.bar.service.TableService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TableServiceImpl implements TableService {

	final UserRepository userRepository;

	final UserConverter userConverter;

	final TableRepository tableRepository;

	final OrderRepository orderRepository;

	final TableConverter tableConverter;

	final OrderService orderService;

	@Override
	@Transactional
	public TableDTO book(final Long tableId, final BookingRequest bookingRequest) throws CannotBookTableException {
		final Optional<TableDBO> table = tableRepository.findById(tableId);
		// Not sure if this check is necessary
		if (table.isEmpty()) {
			throw new CannotBookTableException("Table does not exist");
		}
		final LocalDateTime from = bookingRequest.getFrom();
		final LocalDateTime to = bookingRequest.getTo();

		if (orderRepository.existsByTableAndToAfterAndFromBefore(tableId, from, to)) {
			throw new CannotBookTableException("Table is already reserved");
		}

		final List<OrderDBO> orders = new ArrayList<>();

		if (table.get().isPrivate()) {
			orders.add(orderService.createOrder(table.get(), null, from, to));
		} else if (bookingRequest.getGuestsCount() != bookingRequest.getRegisteredGuests().size()) {
			throw new CannotBookTableException("All guests at the public table must be registered");
		}
		orders.addAll(userRepository.findByLoginIn(bookingRequest.getRegisteredGuests())
				.stream().map(user -> orderService.createOrder(table.get(), user, from, to)).collect(Collectors.toList()));
		orderRepository.saveAll(orders);

		return tableConverter.convertToDto(table.get());
	}

	public Tables tables(final Integer capacity, final LocalDateTime from, final LocalDateTime to, final boolean isPrivate) {
		final List<TableDBO> tableDBOs = tableRepository.getNotReservedPrivateTablesForTime(capacity, from, to);
		if (!isPrivate) {
			tableDBOs.addAll(tableRepository.getPublicTablesWithEnoughPlacesForTime(capacity, from, to));
		}

		return new Tables(tableDBOs.stream().map(tableConverter::convertToDto).collect(Collectors.toList()));
	}
}
