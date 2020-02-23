package gp.training.kim.bar.service.impl;

import gp.training.kim.bar.converter.TableConverter;
import gp.training.kim.bar.dbo.OrderDBO;
import gp.training.kim.bar.dbo.TableDBO;
import gp.training.kim.bar.dto.TableDTO;
import gp.training.kim.bar.dto.entity.BookingRequest;
import gp.training.kim.bar.dto.entity.Tables;
import gp.training.kim.bar.repository.OrderRepository;
import gp.training.kim.bar.repository.TableRepository;
import gp.training.kim.bar.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	final TableRepository tableRepository;

	final OrderRepository orderRepository;

	final TableConverter tableConverter;

	@Override
	public TableDTO book(BookingRequest bookingRequest) {
		final List<OrderDBO> orders = new ArrayList<>();
		return null;
		//new TableDTO("Table 2", new ArrayList<>(List.of(3, 4, 5, 6)));
	}

	public Tables tables(final Integer capacity, final LocalDateTime from, final LocalDateTime to, final boolean isPrivate) {
		final List<TableDBO> tableDBOs = tableRepository.getNotReservedPrivateTablesForTime(capacity, from, to);
		if (!isPrivate) {
			tableDBOs.addAll(tableRepository.getPublicTablesWithEnoughPlacesForTime(capacity, from, to));
		}

		return new Tables(tableDBOs.stream().map(tableConverter::convertToDto).collect(Collectors.toList()));
	}
}
