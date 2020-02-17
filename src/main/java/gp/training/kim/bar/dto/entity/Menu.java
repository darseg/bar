package gp.training.kim.bar.dto.entity;

import gp.training.kim.bar.dto.OfferDTO;
import lombok.Data;

import java.util.List;

@Data
public class Menu {
    final private List<OfferDTO> beer;

    final private List<OfferDTO> food;
}
