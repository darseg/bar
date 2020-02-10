package gp.training.kim.bar.utils;

import gp.training.kim.bar.dbo.OfferDBO;
import gp.training.kim.bar.dto.OfferDTO;

import java.math.BigDecimal;
import java.util.List;

public class BarUtils {
    public static BigDecimal getSumPriceFromDTOs(final List<OfferDTO> offerDTOS) {
        return offerDTOS.stream().map(OfferDTO::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static BigDecimal getSumPriceFromDBOs(final List<OfferDBO> offerDBOS) {
        return offerDBOS.stream().map(OfferDBO::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
