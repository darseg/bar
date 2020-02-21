package gp.training.kim.bar.controller;

import gp.training.kim.bar.dto.entity.Check;
import gp.training.kim.bar.dto.entity.TableCheck;
import gp.training.kim.bar.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/guests")
public class GuestController {

	private final OrderService orderService;


	@PostMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void makeOrder(@RequestBody final List<Integer> offerIds, @RequestHeader final Integer visitorId) {
		orderService.makeOrder(visitorId, offerIds);
	}

	@GetMapping(value = "/check", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
	@ResponseStatus(HttpStatus.OK)
	public Check getVisitorCheck(@RequestHeader final Long visitorId) {
		return orderService.getCheck(visitorId);
	}

	@GetMapping(value = "/table-check", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
	@ResponseStatus(HttpStatus.OK)
	public TableCheck getTableCheck(@RequestHeader final Long visitorId, @RequestParam(required = false) String visitors) {
		return orderService.getTableCheck(visitorId, visitors);
	}
}