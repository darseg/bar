package gp.training.kim.bar.service.impl;

import gp.training.kim.bar.dto.BookingDTO;
import gp.training.kim.bar.dto.TableDTO;
import gp.training.kim.bar.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public TableDTO book(BookingDTO input) {
        return TableDTO.builder()
                .table("Table 2")
                .visitors(new ArrayList<>(
                        List.of(3,4,5,6))).build();
    }
}
