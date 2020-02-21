package gp.training.kim.bar.controller;

import gp.training.kim.bar.dto.BookingDTO;
import gp.training.kim.bar.dto.TableDTO;
import gp.training.kim.bar.dto.entity.Menu;
import gp.training.kim.bar.service.OfferService;
import gp.training.kim.bar.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class UserController {

	private final UserService userService;

	private final OfferService offerService;

	@GetMapping(value = "/menu", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
	@ResponseStatus(HttpStatus.OK)
	public Menu menu() {
		return offerService.getMenu();
	}

	@PostMapping(value = "/book", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
	@ResponseStatus(HttpStatus.CREATED)
	public TableDTO bookTable(@RequestBody final BookingDTO bookingDTO) {
		return userService.book(bookingDTO);
	}
}