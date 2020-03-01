package gp.training.kim.bar.controller;

import gp.training.kim.bar.dto.OfferDTO;
import gp.training.kim.bar.dto.entity.Menu;
import gp.training.kim.bar.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/offers")
public class OfferController {

	private final OfferService offerService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
	@ResponseStatus(HttpStatus.OK)
	public Menu offers() {
		return offerService.getMenu();
	}

	@GetMapping(value = "/report", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
	@ResponseStatus(HttpStatus.OK)
	public List<OfferDTO> report() {
		return offerService.getOffersReport();
	}
}