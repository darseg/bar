package gp.training.kim.bar.controller.entity;

import gp.training.kim.bar.dto.OfferDTO;
import gp.training.kim.bar.utils.BarUtils;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class Check {
    final private Map<String, CheckRow> details;

    final private BigDecimal price;

    public Check(final List<OfferDTO> offerDTOS) {
        this.price = BarUtils.getSumPriceFromDTOs(offerDTOS);

        details = new HashMap<>();

        final Map<Long, List<OfferDTO>> groups = offerDTOS.stream().collect(Collectors.groupingBy(OfferDTO::getId));

        groups.values().forEach(offers -> {
            final OfferDTO first = offers.get(0);
            details.put(first.getName(), new CheckRow(first.getPrice(), offers.size(), BarUtils.getSumPriceFromDTOs(offers)));
        });
    }
}