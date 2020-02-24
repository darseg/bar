package gp.training.kim.bar.controller;

import gp.training.kim.bar.dto.entity.AddOffersRequest;
import gp.training.kim.bar.dto.entity.Check;
import gp.training.kim.bar.dto.entity.Orders;
import gp.training.kim.bar.exception.OfferIsNotAvailableException;
import gp.training.kim.bar.exception.OrderNotFoundException;
import gp.training.kim.bar.exception.UserNotFoundException;
import gp.training.kim.bar.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

	private final OrderService orderService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
	@ResponseStatus(HttpStatus.OK)
	public Orders myOrders(final Authentication authentication) throws UserNotFoundException, OrderNotFoundException {
		return orderService.myOrders(authentication.getName());
	}

	@GetMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
	@ResponseStatus(HttpStatus.OK)
	public Check bookTable(@PathVariable final Long orderId,
						   final Authentication authentication) throws OrderNotFoundException {
		return orderService.getCheck(orderId);
	}

	@PatchMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Check addOffersToCheck(@RequestBody final AddOffersRequest addOffersRequest,
						  @PathVariable final Long orderId,
						  final Authentication authentication) throws OrderNotFoundException, OfferIsNotAvailableException {
		return orderService.addOffersToCheck(orderId, addOffersRequest);
	}
}