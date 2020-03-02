package gp.training.kim.bar.service.impl;

import gp.training.kim.bar.converter.OfferConverter;
import gp.training.kim.bar.dbo.OfferDBO;
import gp.training.kim.bar.dbo.OrderDBO;
import gp.training.kim.bar.dbo.OrderOfferDBO;
import gp.training.kim.bar.dbo.TableDBO;
import gp.training.kim.bar.dbo.UserDBO;
import gp.training.kim.bar.dto.entity.AddOffersRequest;
import gp.training.kim.bar.dto.entity.Check;
import gp.training.kim.bar.dto.entity.CheckRow;
import gp.training.kim.bar.dto.entity.Orders;
import gp.training.kim.bar.dto.entity.OrdersReport;
import gp.training.kim.bar.dto.entity.OrdersReportRow;
import gp.training.kim.bar.exception.BarCannotBookTableException;
import gp.training.kim.bar.exception.BarOfferIsNotAvailableException;
import gp.training.kim.bar.exception.BarOrderNotFoundException;
import gp.training.kim.bar.exception.BarUserNotFoundException;
import gp.training.kim.bar.repository.OfferRepository;
import gp.training.kim.bar.repository.OrderRepository;
import gp.training.kim.bar.service.AuthService;
import gp.training.kim.bar.service.OfferService;
import gp.training.kim.bar.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

	final AuthService authService;

	final OrderRepository orderRepository;

	final OfferRepository offerRepository;

	final OfferConverter offerConverter;

	final OfferService offerService;

	@Override
	public Check getCheck(final Long orderId) throws BarOrderNotFoundException {
		final OrderDBO order = getOrder(orderRepository.getOrderDBOByIdAndPaidFalse(orderId));

		return getCheckForOrderOffers(order.getOrderOffers());
	}

	@Override
	public Orders myOrders(final String login) throws BarUserNotFoundException, BarOrderNotFoundException {
		final UserDBO user = authService.getUserByLogin(login);
		final Orders response = new Orders();
		final OrderDBO userOrder = getOrder(orderRepository.findByUserAndPaidFalse(user));

		response.setUserOrder(userOrder.getId());

		if (userOrder.getTable().isPrivate()) {
			final Optional<OrderDBO> tableOrder = orderRepository.findByTableAndUserIsNullAndPaidFalse(userOrder.getTable());

			tableOrder.ifPresent(orderDBO -> response.setTableOrder(orderDBO.getId()));
		}

		return response;
	}

	@Transactional
	@Override
	public Check addOffersToCheck(final Long orderId, final AddOffersRequest addOffersRequest)
			throws BarOrderNotFoundException, BarOfferIsNotAvailableException {
		final OrderDBO order = getOrder(orderRepository.getOrderDBOByIdAndPaidFalse(orderId));

		final Map<Long, OfferDBO> requestedOffers = offerRepository.findByIdIn(addOffersRequest.getOffers().keySet())
				.stream().collect(Collectors.toMap(OfferDBO::getId, Function.identity()));

		final Map<OfferDBO, Integer> offersForRelease = new HashMap<>();
		final List<OrderOfferDBO> orderOffers = new ArrayList<>();

		for (final Map.Entry<Long, Integer> offersEntry : addOffersRequest.getOffers().entrySet()) {
			final OfferDBO offer = requestedOffers.get(offersEntry.getKey());
			final OrderOfferDBO orderOffer = createOrderOffer(order, offer, offersEntry.getValue());

			orderOffers.add(orderOffer);
			offersForRelease.put(offer, offersEntry.getValue());
		}

		offerService.releaseOffers(offersForRelease);
		order.getOrderOffers().addAll(orderOffers);
		orderRepository.save(order);

		return getCheckForOrderOffers(orderOffers);
	}

	@Override
	@Transactional
	public void createOrders(final TableDBO table,
							 final List<String> userLogins,
							 final LocalDateTime start,
							 final LocalDateTime end) throws BarCannotBookTableException, BarUserNotFoundException {

		if (orderRepository.existsByTableAndEndAfterAndStartBefore(table, start, end)) {
			throw new BarCannotBookTableException("Table is already reserved");
		}

		final List<UserDBO> users = authService.getUsersByLogins(userLogins);
		final List<OrderDBO> notPayedOrders = orderRepository.findAllByUserInAndPaidFalse(users);

		if (!notPayedOrders.isEmpty()) {
			final String logins = notPayedOrders.stream()
					.map(orderDBO -> orderDBO.getUser().getLogin()).collect(Collectors.joining(", "));
			throw new BarCannotBookTableException("Not paid order for users " + logins + " already exist");
		}

		final List<OrderDBO> orders = new ArrayList<>();

		if (table.isPrivate()) {
			orders.add(createOrder(table, null, start, end));
		}

		users.forEach(user -> orders.add(createOrder(table, user, start, end)));

		orderRepository.saveAll(orders);
	}

	@Override
	public OrdersReport getNotPayedOrders() {
		final OrdersReport ordersReport = new OrdersReport();
		final List<OrderDBO> orders = orderRepository.findAllByPaidFalse();

		ordersReport.setOrders(orders.stream().map(this::createOrderReportRow).collect(Collectors.toList()));

		return ordersReport;
	}

	@Override
	public void closeOrder(final Long orderId) throws BarOrderNotFoundException {
		final OrderDBO order = getOrder(orderRepository.getOrderDBOByIdAndPaidFalse(orderId));

		order.setPaid(true);

		orderRepository.save(order);
	}

	private OrdersReportRow createOrderReportRow(final OrderDBO order) {
		final OrdersReportRow ordersReportRow = new OrdersReportRow();
		ordersReportRow.setId(order.getId());
		ordersReportRow.setTable(order.getTable().getName());
		ordersReportRow.setStart(order.getStart());
		ordersReportRow.setEnd(order.getEnd());
		ordersReportRow.setPrice(order.getOrderOffers().stream()
				.map(orderOffer -> orderOffer.getOffer().getPrice()
						.multiply(BigDecimal.valueOf(orderOffer.getAmount())))
				.reduce(BigDecimal.ZERO, BigDecimal::add));

		if (order.getUser() != null) {
			ordersReportRow.setUser(order.getUser().getLogin());
		}

		return ordersReportRow;
	}

	public OrderDBO createOrder(final TableDBO table,
								final UserDBO user,
								final LocalDateTime start,
								final LocalDateTime end) {

		final OrderDBO order = new OrderDBO();
		order.setStart(start);
		order.setEnd(end);
		order.setPaid(false);
		order.setTable(table);
		order.setUser(user);

		return order;
	}

	private OrderDBO getOrder(final Optional<OrderDBO> orderOptional) throws BarOrderNotFoundException {
		return orderOptional.orElseThrow(() -> new BarOrderNotFoundException("Order does not exist"));
	}

	private Check getCheckForOrderOffers(final List<OrderOfferDBO> orderOffers) {
		final Map<String, CheckRow> details = new HashMap<>();

		BigDecimal price = BigDecimal.ZERO;
		final Map<Long, List<OrderOfferDBO>> groups = orderOffers
				.stream().collect(Collectors.groupingBy(orderOfferDBO -> orderOfferDBO.getOffer().getId()));

		for (final List<OrderOfferDBO> orderOffersGroup : groups.values()) {
			final OfferDBO first = orderOffersGroup.get(0).getOffer();
			final Integer sumAmount = orderOffersGroup.stream().map(OrderOfferDBO::getAmount).reduce(0, Integer::sum);
			final BigDecimal offersPrice = first.getPrice().multiply(BigDecimal.valueOf(sumAmount));
			final CheckRow checkRow = new CheckRow(first.getPrice(), sumAmount, offersPrice);
			price = price.add(offersPrice);

			details.put(first.getName(), checkRow);
		}

		return new Check(details, price);
	}

	private OrderOfferDBO createOrderOffer(final OrderDBO order, final OfferDBO offer, final Integer amount) {
		final OrderOfferDBO orderOffer = new OrderOfferDBO();
		orderOffer.setOrder(order);
		orderOffer.setOffer(offer);
		orderOffer.setAmount(amount);

		return orderOffer;
	}
}
