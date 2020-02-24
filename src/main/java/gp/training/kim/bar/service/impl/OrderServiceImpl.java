package gp.training.kim.bar.service.impl;

import gp.training.kim.bar.converter.OfferConverter;
import gp.training.kim.bar.dbo.OfferDBO;
import gp.training.kim.bar.dbo.OrderDBO;
import gp.training.kim.bar.dbo.OrderOfferDBO;
import gp.training.kim.bar.dbo.TableDBO;
import gp.training.kim.bar.dbo.UserDBO;
import gp.training.kim.bar.dto.entity.Check;
import gp.training.kim.bar.dto.entity.CheckRow;
import gp.training.kim.bar.dto.entity.Orders;
import gp.training.kim.bar.exception.CannotBookTableException;
import gp.training.kim.bar.exception.OrderNotFoundException;
import gp.training.kim.bar.exception.UserNotFoundException;
import gp.training.kim.bar.repository.OrderRepository;
import gp.training.kim.bar.service.AuthService;
import gp.training.kim.bar.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

	final AuthService authService;

	final OrderRepository orderRepository;

	final OfferConverter offerConverter;

	@Override
	public Check getCheck(final Long orderId) throws OrderNotFoundException {
		final OrderDBO order = getOrder(orderRepository.getOrderDBOById(orderId));
		final Map<String, CheckRow> details = new HashMap<>();

		BigDecimal price = BigDecimal.ZERO;
		final Map<Long, List<OrderOfferDBO>> groups = order.getOrderOffers()
				.stream().collect(Collectors.groupingBy(orderOfferDBO -> orderOfferDBO.getOffer().getId()));

		for (List<OrderOfferDBO> orderOffers : groups.values()) {
			final OfferDBO first = orderOffers.get(0).getOffer();
			final Integer sumAmount = orderOffers.stream().map(OrderOfferDBO::getAmount).reduce(0, Integer::sum);
			final BigDecimal offersPrice = first.getPrice().multiply(BigDecimal.valueOf(sumAmount));
			final CheckRow checkRow = new CheckRow(first.getPrice(), sumAmount, offersPrice);
			price = price.add(offersPrice);

			details.put(first.getName(), checkRow);
		}

		return new Check(details, price);
	}

	@Override
	public OrderDBO createOrder(final TableDBO table, final UserDBO user, final LocalDateTime start, final LocalDateTime end) throws CannotBookTableException {
		if (orderRepository.existsByUserAndPaidFalse(user)) {
			throw new CannotBookTableException("Not paid user order already exist");
		}

		final OrderDBO order = new OrderDBO();
		order.setStart(start);
		order.setEnd(end);
		order.setPaid(false);
		order.setTable(table);
		order.setUser(user);

		return order;
	}

	@Override
	public Orders myOrders(final String login) throws UserNotFoundException, OrderNotFoundException {
		final UserDBO user = authService.getUserByLogin(login);
		final Orders response = new Orders();
		final OrderDBO userOrder = getOrder(orderRepository.findByUserAndPaidFalse(user));

		response.setUserOrder(userOrder.getId());

		final Optional<OrderDBO> tableOrder = orderRepository.findByTableAndUserIsNullAndPaidFalse(userOrder.getTable());

		tableOrder.ifPresent(orderDBO -> response.setTableOrder(orderDBO.getId()));

		return response;
	}

	private OrderDBO getOrder(final Optional<OrderDBO> orderOptional) throws OrderNotFoundException {
		if (orderOptional.isEmpty()) {
			throw new OrderNotFoundException("Order does not exist");
		}
		return orderOptional.get();
	}
}
