package gp.training.kim.bar.dto;

import lombok.Data;

@Data
public class BookingDTO {
    final Integer count;

    final String date;

    final String from;

    final String to;

    final Boolean barRack;
}
