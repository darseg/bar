package gp.training.kim.bar.controller;

import gp.training.kim.bar.controller.entity.Check;
import gp.training.kim.bar.controller.entity.Menu;
import gp.training.kim.bar.controller.entity.TableCheck;
import gp.training.kim.bar.dto.BookingDTO;
import gp.training.kim.bar.dto.TableDTO;
import gp.training.kim.bar.service.OfferServise;
import gp.training.kim.bar.service.VisitorService;
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
@RequestMapping("/visitor")
public class VisitorController {

    private final VisitorService visitorService;

    private final OfferServise offerServise;


    @PostMapping(value = "/book", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    @ResponseStatus(HttpStatus.CREATED)
    public TableDTO bookTable(@RequestBody final BookingDTO bookingDTO) {
        return visitorService.book(bookingDTO);
    }

    @GetMapping(value = "/menu", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    public Menu menu() {
        return offerServise.getMenu();
    }

    @PostMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void makeOrder(@RequestBody final List<Integer> offerIds, @RequestHeader final Integer visitorId) {
        offerServise.makeOrder(visitorId, offerIds);
    }

    @GetMapping(value = "/check", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    public Check getVisitorCheck(@RequestHeader final Integer visitorId) {
        return offerServise.getCheck(visitorId);
    }

    @GetMapping(value = "/tableCheck", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    public TableCheck getTableCheck(@RequestHeader final Integer visitorId, @RequestParam(required = false) String visitors) {
        return offerServise.getTableCheck(visitorId, visitors);
    }
}