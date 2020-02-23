package gp.training.kim.bar.controller;

import gp.training.kim.bar.constant.BarConstants;
import gp.training.kim.bar.dto.TableDTO;
import gp.training.kim.bar.dto.entity.BookingRequest;
import gp.training.kim.bar.dto.entity.Tables;
import gp.training.kim.bar.exception.CannotBookTableException;
import gp.training.kim.bar.service.TableService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
@AllArgsConstructor
@RequestMapping("/tables")
public class TableController {

	private final TableService tableService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
	@ResponseStatus(HttpStatus.CREATED)
	public Tables freeTables(@RequestParam Integer capacity,
							 @RequestParam("from") @DateTimeFormat(pattern = BarConstants.DATE_TIME_FORMAT) LocalDateTime from,
							 @RequestParam("to") @DateTimeFormat(pattern = BarConstants.DATE_TIME_FORMAT) LocalDateTime to,
							 @RequestParam(name = "private", required = false) boolean isPrivate) {
		return tableService.tables(capacity, from, to, isPrivate);
	}

	// I guess this method must be synchronized, but I'n not sure how to do it correctly.
	@PostMapping(value = "/{tableId}/book", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
	@ResponseStatus(HttpStatus.CREATED)
	public TableDTO bookTable(@RequestBody final BookingRequest bookingRequest,
							  @PathVariable final  Long tableId,
							  final Authentication authentication) throws CannotBookTableException {
		return tableService.book(tableId, bookingRequest);
	}
}