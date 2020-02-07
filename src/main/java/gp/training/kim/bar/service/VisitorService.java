package gp.training.kim.bar.service;

import gp.training.kim.bar.dto.BookDTO;
import gp.training.kim.bar.dto.BookingDTO;

public interface VisitorService {
    BookDTO book(BookingDTO input);
}
