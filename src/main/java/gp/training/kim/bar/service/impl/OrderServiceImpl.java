package gp.training.kim.bar.service.impl;

import gp.training.kim.bar.converter.OfferConverter;
import gp.training.kim.bar.dbo.UserDBO;
import gp.training.kim.bar.dto.OfferDTO;
import gp.training.kim.bar.dto.entity.Check;
import gp.training.kim.bar.dto.entity.CheckRow;
import gp.training.kim.bar.dto.entity.TableCheck;
import gp.training.kim.bar.repository.OrderRepository;
import gp.training.kim.bar.service.OrderService;
import gp.training.kim.bar.utils.BarUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    final OrderRepository orderRepository;

    final OfferConverter offerConverter;

    @Override
    public Check getCheck(final Long guestId) {

        return getCheckFromOffers(
                orderRepository.findByUserAndPaidNot(guestId).getOffers().stream()
                        .map(offerConverter::convertToDto).collect(Collectors.toList()));
    }

    @Override
    public TableCheck getTableCheck(final Long tableId, final String visitors) {
        final List<Long> visitorsList = new ArrayList<>();
        if (visitors != null)
            visitorsList.addAll(Arrays.stream(visitors.split(",")).map(Long::parseLong).collect(Collectors.toList()));

        final List<OfferDTO> tableOffers = new ArrayList<>();
        final Map<Long, Check> visitorsChecks = new HashMap<>();

        orderRepository.findByTableAndPaidNot(tableId).forEach(orderDBO -> {
            final Optional<UserDBO> guest = Optional.ofNullable(orderDBO.getUser());
            final List<OfferDTO> offers = orderDBO.getOffers().stream().map(offerConverter::convertToDto).collect(Collectors.toList());

            if (guest.isPresent() && visitorsList.contains(guest.get().getId())) {
                visitorsChecks.put(guest.get().getId(), getCheckFromOffers(offers));
            } else {
                tableOffers.addAll(offers);
            }
        });

        return new TableCheck(getCheckFromOffers(tableOffers), visitorsChecks);
    }

    private Check getCheckFromOffers(final List<OfferDTO> offerDTOS) {
        BigDecimal price = BarUtils.getSumPriceFromDTOs(offerDTOS);

        final Map<String, CheckRow> details = new HashMap<>();

        final Map<Long, List<OfferDTO>> groups = offerDTOS.stream().collect(Collectors.groupingBy(OfferDTO::getId));

        groups.values().forEach(offers -> {
            final OfferDTO first = offers.get(0);
            details.put(first.getName(), new CheckRow(first.getPrice(), offers.size(), BarUtils.getSumPriceFromDTOs(offers)));
        });

        return new Check(details, price);
    }
}
