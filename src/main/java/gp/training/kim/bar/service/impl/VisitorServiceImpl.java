package gp.training.kim.bar.service.impl;

import gp.training.kim.bar.dto.BookDTO;
import gp.training.kim.bar.dto.BookingDTO;
import gp.training.kim.bar.service.VisitorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VisitorServiceImpl implements VisitorService {

    @Override
    public BookDTO book(BookingDTO input) {
        final List<Integer> visitors = new ArrayList<>();
        visitors.add(3);
        visitors.add(4);
        visitors.add(5);
        visitors.add(6);

        return BookDTO.builder().table("Table 2").visitors(visitors).build();
    }
}
