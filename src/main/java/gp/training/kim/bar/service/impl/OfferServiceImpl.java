package gp.training.kim.bar.service.impl;

import gp.training.kim.bar.controller.entity.Check;
import gp.training.kim.bar.controller.entity.Menu;
import gp.training.kim.bar.controller.entity.TableCheck;
import gp.training.kim.bar.converter.OfferConverter;
import gp.training.kim.bar.dto.OfferDTO;
import gp.training.kim.bar.service.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OfferServiceImpl implements OfferService {

    final private OfferConverter offerConverter;

    @Override
    public Menu getMenu() {
        return new Menu(TempData.getBeer().values().stream().map(offerConverter::convertToDto).collect(Collectors.toList()),
                TempData.getFood().values().stream().map(offerConverter::convertToDto).collect(Collectors.toList()));
    }

    @Override
    public void makeOrder(final Integer visitorId, final List<Integer> offerIds) {

    }

    @Override
    public Check getCheck(final Integer visitorId) {
        return new Check(TempData.getVisitors().get(visitorId).getOrder().stream().map(offerConverter::convertToDto).collect(Collectors.toList()));
    }

    @Override
    public TableCheck getTableCheck(final Integer visitorId, final String visitors) {
        final List<Long> visitorsList = new ArrayList<>();
        if (visitors != null)
            visitorsList.addAll(Arrays.stream(visitors.split(",")).map(Long::parseLong).collect(Collectors.toList()));

        final List<OfferDTO> tableOffers = new ArrayList<>();
        final Map<Long, Check> visitorsChecks = new HashMap<>();

        TempData.getVisitorToTableMap().get(visitorId).getGuests().forEach(guestDBO -> {
            final Long id = guestDBO.getId();
            final List<OfferDTO> offers = guestDBO.getOrder().stream().map(offerConverter::convertToDto).collect(Collectors.toList());

            if (visitorsList.contains(id)) {
                visitorsChecks.put(id, new Check(offers));
            } else {
                tableOffers.addAll(offers);
            }
        });

        return new TableCheck(new Check(tableOffers), visitorsChecks);
    }
}
