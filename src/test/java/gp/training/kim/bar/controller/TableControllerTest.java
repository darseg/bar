package gp.training.kim.bar.controller;

import gp.training.kim.bar.MockDBOsStorage;
import gp.training.kim.bar.constant.BarConstants;
import gp.training.kim.bar.dbo.AuthInfoDBO;
import gp.training.kim.bar.dbo.OrderDBO;
import gp.training.kim.bar.dbo.TableDBO;
import gp.training.kim.bar.dbo.UserDBO;
import gp.training.kim.bar.dto.entity.BookingRequest;
import gp.training.kim.bar.repository.OrderRepository;
import gp.training.kim.bar.repository.TableRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TableControllerTest extends AbstractBarTest {
	@MockBean
	private TableRepository tableRepository;

	@MockBean
	private OrderRepository orderRepository;

	@Test
	public void testGetFreeTables() throws Exception {
		loadTestResources();
		//given
		final Integer capacity = 3;
		final LocalDateTime start = LocalDateTime.of(2020, 2, 25, 19, 0);
		final LocalDateTime end = LocalDateTime.of(2020, 2, 25, 23, 0);
		given(tableRepository.getNotReservedPrivateTablesForTime(capacity, start, end))
				.willReturn(MockDBOsStorage.tables.values().stream()
						.filter(TableDBO::isPrivate)
						.collect(Collectors.toList()));

		given(tableRepository.getPublicTablesWithEnoughPlacesForTime(capacity, start, end))
				.willReturn(MockDBOsStorage.tables.values().stream()
						.filter(tableDBO -> !tableDBO.isPrivate())
						.collect(Collectors.toList()));
		//when
		mockMvc.perform(get("/tables")
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.param("capacity", capacity.toString())
				.param("start", formatLocalDateTime(start))
				.param("end", formatLocalDateTime(end)))
				// then
				.andExpect(status().isOk())
				.andExpect(content().json(response));

		verify(tableRepository, times(1)).getNotReservedPrivateTablesForTime(any(), any(), any());
		verify(tableRepository, times(1)).getPublicTablesWithEnoughPlacesForTime(any(), any(), any());
	}

	@Test
	public void testGetFreePrivateTables() throws Exception {
		loadTestResources();
		//given
		final Integer capacity = 3;
		final LocalDateTime start = LocalDateTime.of(2020, 2, 25, 19, 0);
		final LocalDateTime end = LocalDateTime.of(2020, 2, 25, 23, 0);
		given(tableRepository.getNotReservedPrivateTablesForTime(capacity, start, end))
				.willReturn(MockDBOsStorage.tables.values().stream()
						.filter(TableDBO::isPrivate)
						.collect(Collectors.toList()));

		given(tableRepository.getPublicTablesWithEnoughPlacesForTime(capacity, start, end))
				.willReturn(MockDBOsStorage.tables.values().stream()
						.filter(tableDBO -> !tableDBO.isPrivate())
						.collect(Collectors.toList()));
		//when
		mockMvc.perform(get("/tables")
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.param("capacity", capacity.toString())
				.param("start", formatLocalDateTime(start))
				.param("end", formatLocalDateTime(end))
				.param("private", "true"))
				// then
				.andExpect(status().isOk())
				.andExpect(content().json(response));

		verify(tableRepository, times(1)).getNotReservedPrivateTablesForTime(any(), any(), any());
		verify(tableRepository, times(0)).getPublicTablesWithEnoughPlacesForTime(any(), any(), any());
	}

	@Test
	public void testBookPrivateTable() throws Exception {
		loadTestResources();
		//given
		final String clientLogin = "BenDelat";
		final HttpHeaders auth = getAuthorizationHeader(clientLogin);
		final Long tableId = 3L;
		final TableDBO table = MockDBOsStorage.tables.get(tableId);
		final UserDBO clientUserDBO = MockDBOsStorage.authInfos.get(clientLogin).getUser();

		final BookingRequest bookingRequest = objectMapper
				.setDateFormat(new SimpleDateFormat(BarConstants.DATE_TIME_FORMAT))
				.readValue(request, BookingRequest.class);

		final List<String> users = new ArrayList<>(bookingRequest.getRegisteredGuests());
		users.add(clientLogin);

		final List<OrderDBO> createdOrders = MockDBOsStorage.orders.values().stream()
				.filter(orderDBO -> orderDBO.getTable().equals(table))
				.filter(orderDBO -> orderDBO.getUser() == null || users.contains(orderDBO.getUser().getLogin()))
				.peek(orderDBO -> {
					orderDBO.setStart(bookingRequest.getStart());
					orderDBO.setEnd(bookingRequest.getEnd());
				})
				.collect(Collectors.toList());

		given(tableRepository.findById(tableId)).willReturn(Optional.of(table));
		given(orderRepository.existsByTableAndEndAfterAndStartBefore(table, bookingRequest.getStart(), bookingRequest.getEnd()))
				.willReturn(false);

		given(userRepository.findByLoginIn(users)).willReturn(MockDBOsStorage.authInfos.values().stream()
				.filter(authInfoDBO -> users.contains(authInfoDBO.getLogin()))
				.map(AuthInfoDBO::getUser)
				.collect(Collectors.toList()));

		given(userRepository.findByLogin(clientLogin)).willReturn(Optional.of(clientUserDBO));
		given(orderRepository.findByUserAndPaidFalse(clientUserDBO)).willReturn(createdOrders.stream()
				.filter(orderDBO -> orderDBO.getUser() != null && orderDBO.getUser().equals(clientUserDBO))
				.findAny());
		given(orderRepository.findByTableAndUserIsNullAndPaidFalse(table)).willReturn(createdOrders.stream()
				.filter(orderDBO -> orderDBO.getUser() == null)
				.findAny());
		//when
		mockMvc.perform(post("/tables/" + tableId + "/book")
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.content(request))
				// then
				.andExpect(status().isCreated())
				.andExpect(content().json(response));

		verify(tableRepository, times(1)).findById(any());
		verify(orderRepository, times(1)).existsByTableAndEndAfterAndStartBefore(any(), any(), any());
		verify(tableRepository, times(0)).getPublicTablesWithEnoughPlacesForTime(any(), any(), any());
		verify(userRepository, times(1)).findByLoginIn(anyList());
		verify(userRepository, times(1)).findByLogin(any());
		verify(orderRepository, times(1)).findByUserAndPaidFalse(any());
		verify(orderRepository, times(1)).findByTableAndUserIsNullAndPaidFalse(any());
		verify(orderRepository, times(1)).saveAll(anyList());
		verify(orderRepository, times(1)).saveAll(createdOrders);
	}

	@Test
	public void testBookPublicTable() throws Exception {
		loadTestResources();
		//given
		final String clientLogin = "BenDelat";
		final HttpHeaders auth = getAuthorizationHeader(clientLogin);
		final TableDBO table = MockDBOsStorage.tables.values().stream()
				.filter(tableDBO -> !tableDBO.isPrivate()).findAny().orElse(null);
		final UserDBO clientUserDBO = MockDBOsStorage.authInfos.get(clientLogin).getUser();
		final BookingRequest bookingRequest = objectMapper
				.setDateFormat(new SimpleDateFormat(BarConstants.DATE_TIME_FORMAT))
				.readValue(request, BookingRequest.class);
		final List<String> users = new ArrayList<>(bookingRequest.getRegisteredGuests());
		users.add(clientLogin);

		final List<OrderDBO> createdOrders = MockDBOsStorage.orders.values().stream()
				.filter(orderDBO -> orderDBO.getTable().equals(table))
				.filter(orderDBO -> orderDBO.getUser() != null && users.contains(orderDBO.getUser().getLogin()))
				.peek(orderDBO -> {
					orderDBO.setStart(bookingRequest.getStart());
					orderDBO.setEnd(bookingRequest.getEnd());
				})
				.collect(Collectors.toList());

		assert table != null;
		given(tableRepository.findById(table.getId())).willReturn(Optional.of(table));
		given(orderRepository.existsByTableAndEndAfterAndStartBefore(
				table, bookingRequest.getStart(), bookingRequest.getEnd()))
				.willReturn(false);

		given(tableRepository.getPublicTablesWithEnoughPlacesForTime(
				bookingRequest.getGuestsCount(),
				bookingRequest.getStart(),
				bookingRequest.getEnd())).willReturn(Collections.singletonList(table));

		given(userRepository.findByLoginIn(users)).willReturn(MockDBOsStorage.authInfos.values().stream()
				.filter(authInfoDBO -> users.contains(authInfoDBO.getLogin()))
				.map(AuthInfoDBO::getUser)
				.collect(Collectors.toList()));

		given(userRepository.findByLogin(clientLogin)).willReturn(Optional.of(clientUserDBO));
		given(orderRepository.findByUserAndPaidFalse(clientUserDBO)).willReturn(createdOrders.stream()
				.filter(orderDBO -> orderDBO.getUser().equals(clientUserDBO))
				.findAny());
		given(orderRepository.findByTableAndUserIsNullAndPaidFalse(table)).willReturn(createdOrders.stream()
				.filter(orderDBO -> orderDBO.getUser() == null)
				.findAny());

		//when
		mockMvc.perform(post("/tables/" + table.getId() + "/book")
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.content(request))
				// then
				.andExpect(status().isCreated())
				.andExpect(content().json(response));

		verify(tableRepository, times(1)).findById(any());
		verify(orderRepository, times(1)).existsByTableAndEndAfterAndStartBefore(any(), any(), any());
		verify(tableRepository, times(1)).getPublicTablesWithEnoughPlacesForTime(any(), any(), any());
		verify(userRepository, times(1)).findByLoginIn(anyList());
		verify(userRepository, times(1)).findByLogin(any());
		verify(orderRepository, times(1)).findByUserAndPaidFalse(any());
		verify(orderRepository, times(0)).findByTableAndUserIsNullAndPaidFalse(any());
		verify(orderRepository, times(1)).saveAll(anyList());
		verify(orderRepository, times(1)).saveAll(createdOrders);
	}

	@Test
	public void testBookTableDoesNotExist() throws Exception {
		loadTestResources();
		//given
		final String clientLogin = "BenDelat";
		final HttpHeaders auth = getAuthorizationHeader(clientLogin);
		final Long tableId = 50L;
		given(tableRepository.findById(tableId)).willReturn(Optional.empty());

		//when
		mockMvc.perform(post("/tables/" + tableId + "/book")
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.content(request))
				// then
				.andExpect(status().isBadRequest())
				.andExpect(content().json(response));

		verify(tableRepository, times(1)).findById(any());
		verify(orderRepository, times(0)).saveAll(anyList());
	}

	@Test
	public void testBookPrivateTableIsAlreadyReserved() throws Exception {
		loadTestResources();
		//given
		final String clientLogin = "BenDelat";
		final HttpHeaders auth = getAuthorizationHeader(clientLogin);
		final Long tableId = 3L;
		final TableDBO table = MockDBOsStorage.tables.get(tableId);
		final BookingRequest bookingRequest = objectMapper.setDateFormat(new SimpleDateFormat(BarConstants.DATE_TIME_FORMAT))
				.readValue(request, BookingRequest.class);

		given(tableRepository.findById(tableId)).willReturn(Optional.of(table));
		given(orderRepository.existsByTableAndEndAfterAndStartBefore(table, bookingRequest.getStart(), bookingRequest.getEnd()))
				.willReturn(true);
		//when
		mockMvc.perform(post("/tables/" + tableId + "/book")
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.content(request))
				// then
				.andExpect(status().isBadRequest())
				.andExpect(content().json(response));

		verify(tableRepository, times(1)).findById(any());
		verify(orderRepository, times(1)).existsByTableAndEndAfterAndStartBefore(any(), any(), any());
		verify(tableRepository, times(0)).getPublicTablesWithEnoughPlacesForTime(any(), any(), any());
		verify(userRepository, times(0)).findByLoginIn(anyList());
		verify(orderRepository, times(0)).saveAll(anyList());
	}

	@Test
	public void testBookPublicTableNotEnoughGuests() throws Exception {
		loadTestResources();
		//given
		final String clientLogin = "BenDelat";
		final HttpHeaders auth = getAuthorizationHeader(clientLogin);
		final TableDBO table = MockDBOsStorage.tables.values().stream()
				.filter(tableDBO -> !tableDBO.isPrivate()).findAny().orElse(null);
		assert table != null;
		given(tableRepository.findById(table.getId())).willReturn(Optional.of(table));

		//when
		mockMvc.perform(post("/tables/" + table.getId() + "/book")
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.content(request))
				// then
				.andExpect(status().isBadRequest())
				.andExpect(content().json(response));

		verify(tableRepository, times(1)).findById(any());
		verify(orderRepository, times(0)).existsByTableAndEndAfterAndStartBefore(any(), any(), any());
		verify(tableRepository, times(0)).getPublicTablesWithEnoughPlacesForTime(any(), any(), any());
		verify(orderRepository, times(0)).saveAll(anyList());
	}

	@Test
	public void testBookPublicTableNotEnoughPlaces() throws Exception {
		loadTestResources();
		//given
		final String clientLogin = "BenDelat";
		final HttpHeaders auth = getAuthorizationHeader(clientLogin);
		final TableDBO table = MockDBOsStorage.tables.values().stream()
				.filter(tableDBO -> !tableDBO.isPrivate()).findAny().orElse(null);
		final BookingRequest bookingRequest = objectMapper.setDateFormat(new SimpleDateFormat(BarConstants.DATE_TIME_FORMAT))
				.readValue(request, BookingRequest.class);

		assert table != null;
		given(tableRepository.findById(table.getId())).willReturn(Optional.of(table));
		given(orderRepository.existsByTableAndEndAfterAndStartBefore(table, bookingRequest.getStart(), bookingRequest.getEnd()))
				.willReturn(false);
		given(tableRepository.getPublicTablesWithEnoughPlacesForTime(
				bookingRequest.getGuestsCount(),
				bookingRequest.getStart(),
				bookingRequest.getEnd())).willReturn(Collections.emptyList());

		//when
		mockMvc.perform(post("/tables/" + table.getId() + "/book")
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.content(request))
				// then
				.andExpect(status().isBadRequest())
				.andExpect(content().json(response));

		verify(tableRepository, times(1)).findById(any());
		verify(orderRepository, times(0)).existsByTableAndEndAfterAndStartBefore(any(), any(), any());
		verify(tableRepository, times(1)).getPublicTablesWithEnoughPlacesForTime(any(), any(), any());
		verify(userRepository, times(0)).findByLoginIn(anyList());
		verify(userRepository, times(0)).findByLogin(any());
		verify(orderRepository, times(0)).saveAll(anyList());
	}
}
