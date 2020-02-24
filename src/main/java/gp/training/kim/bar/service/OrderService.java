package gp.training.kim.bar.service;

import gp.training.kim.bar.dbo.OrderDBO;
import gp.training.kim.bar.dbo.TableDBO;
import gp.training.kim.bar.dbo.UserDBO;
import gp.training.kim.bar.dto.entity.AddOffersRequest;
import gp.training.kim.bar.dto.entity.Check;
import gp.training.kim.bar.dto.entity.Orders;
import gp.training.kim.bar.exception.CannotBookTableException;
import gp.training.kim.bar.exception.OfferIsNotAvailableException;
import gp.training.kim.bar.exception.OrderNotFoundException;
import gp.training.kim.bar.exception.UserNotFoundException;

import java.time.LocalDateTime;

public interface OrderService {
	Check getCheck(Long orderId) throws OrderNotFoundException;

	OrderDBO createOrder(TableDBO table, UserDBO user, LocalDateTime start, LocalDateTime end) throws CannotBookTableException;

	Orders myOrders(String login) throws UserNotFoundException, OrderNotFoundException;

	Check addOffersToCheck(Long orderId, AddOffersRequest addOffersRequest) throws OrderNotFoundException, OfferIsNotAvailableException;
}
