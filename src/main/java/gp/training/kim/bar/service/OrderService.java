package gp.training.kim.bar.service;

import gp.training.kim.bar.dbo.OrderDBO;
import gp.training.kim.bar.dbo.TableDBO;
import gp.training.kim.bar.dbo.UserDBO;
import gp.training.kim.bar.dto.entity.AddOffersRequest;
import gp.training.kim.bar.dto.entity.Check;
import gp.training.kim.bar.dto.entity.Orders;
import gp.training.kim.bar.dto.entity.OrdersReport;
import gp.training.kim.bar.exception.BarCannotBookTableException;
import gp.training.kim.bar.exception.BarOfferIsNotAvailableException;
import gp.training.kim.bar.exception.BarOrderNotFoundException;
import gp.training.kim.bar.exception.BarUserNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
	Check getCheck(Long orderId) throws BarOrderNotFoundException;

	OrderDBO createOrder(TableDBO table, UserDBO user, LocalDateTime start, LocalDateTime end) throws BarCannotBookTableException;

	Orders myOrders(String login) throws BarUserNotFoundException, BarOrderNotFoundException;

	Check addOffersToCheck(Long orderId, AddOffersRequest addOffersRequest) throws BarOrderNotFoundException, BarOfferIsNotAvailableException;

	void createOrders(TableDBO table,
					  List<String> userLogins,
					  LocalDateTime start,
					  LocalDateTime end) throws BarCannotBookTableException, BarUserNotFoundException;

	OrdersReport getNotPayedOrders();
}
