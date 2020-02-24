package gp.training.kim.bar.service.impl;

import gp.training.kim.bar.converter.TableConverter;
import gp.training.kim.bar.dbo.OrderDBO;
import gp.training.kim.bar.dbo.TableDBO;
import gp.training.kim.bar.dbo.UserDBO;
import gp.training.kim.bar.dto.entity.BookingRequest;
import gp.training.kim.bar.dto.entity.Orders;
import gp.training.kim.bar.dto.entity.Tables;
import gp.training.kim.bar.exception.CannotBookTableException;
import gp.training.kim.bar.exception.UserNotFoundException;
import gp.training.kim.bar.repository.OrderRepository;
import gp.training.kim.bar.repository.TableRepository;
import gp.training.kim.bar.repository.UserRepository;
import gp.training.kim.bar.service.AuthService;
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

	final AuthService authService;

	final UserRepository userRepository;

	final TableRepository tableRepository;

	final OrderRepository orderRepository;

	final TableConverter tableConverter;

	final OrderService orderService;

	@Override
	@Transactional
	public Orders book(final Long tableId, final BookingRequest bookingRequest, final String login) throws CannotBookTableException, UserNotFoundException {
		final Optional<TableDBO> table = tableRepository.findById(tableId);

		// Not sure if this check is necessary
		if (table.isEmpty()) {
			throw new CannotBookTableException("Table does not exist");
		}

		final LocalDateTime start = bookingRequest.getStart();
		final LocalDateTime end = bookingRequest.getEnd();

		if (orderRepository.existsByTableAndEndAfterAndStartBefore(table.get(), start, end)) {
			throw new CannotBookTableException("Table is already reserved");
		}

		final Orders response = new Orders();
		final List<OrderDBO> orders = new ArrayList<>();
		OrderDBO tableOrder = null;
		if (table.get().isPrivate()) {
			tableOrder = orderService.createOrder(table.get(), null, start, end);
			orders.add(tableOrder);
			response.setTableOrder(tableOrder.getId());
		} else if (bookingRequest.getGuestsCount() != bookingRequest.getRegisteredGuests().size() + 1) {
			throw new CannotBookTableException("All guests at the public table must be registered");
		}
		final UserDBO user = authService.getUserByLogin(login);
		final OrderDBO userOrder = orderService.createOrder(table.get(), user, start, end);
		orders.add(userOrder);

		for (UserDBO userDBO : userRepository.findByLoginIn(bookingRequest.getRegisteredGuests())) {
			final OrderDBO order = orderService.createOrder(table.get(), userDBO, start, end);
			orders.add(order);
		}
		orderRepository.saveAll(orders);

		response.setUserOrder(userOrder.getId());
		if (tableOrder != null) {
			response.setTableOrder(tableOrder.getId());
		}
		return response;
	}

	public Tables tables(final Integer capacity, final LocalDateTime start, final LocalDateTime end, final boolean isPrivate) {
		final List<TableDBO> tableDBOs = tableRepository.getNotReservedPrivateTablesForTime(capacity, start, end);
		if (!isPrivate) {
			tableDBOs.addAll(tableRepository.getPublicTablesWithEnoughPlacesForTime(capacity, start, end));
		}

		return new Tables(tableDBOs.stream().map(tableConverter::convertToDto).collect(Collectors.toList()));
	}
}
