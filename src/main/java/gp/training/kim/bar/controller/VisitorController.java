package gp.training.kim.bar.controller;

import gp.training.kim.bar.dto.BookDTO;
import gp.training.kim.bar.dto.BookingDTO;
import gp.training.kim.bar.service.VisitorService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Data
@RestController
@RequestMapping("/visitor")
public class VisitorController {

    private final VisitorService visitorService;

    @PostMapping(value = "/book", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO bookTable(@RequestBody final BookingDTO bookingDTO) {
        return visitorService.book(bookingDTO);
    }
}