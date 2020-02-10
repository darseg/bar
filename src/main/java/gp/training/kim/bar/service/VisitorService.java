package gp.training.kim.bar.service;

import gp.training.kim.bar.dto.BookingDTO;
import gp.training.kim.bar.dto.TableDTO;

public interface VisitorService {
    TableDTO book(BookingDTO input);
}
