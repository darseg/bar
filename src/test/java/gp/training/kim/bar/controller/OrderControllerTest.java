package gp.training.kim.bar.controller;

import gp.training.kim.bar.constant.UserRole;
import gp.training.kim.bar.dbo.OfferDBO;
import gp.training.kim.bar.dbo.OrderDBO;
import gp.training.kim.bar.dbo.OrderOfferDBO;
import gp.training.kim.bar.dbo.UserDBO;
import gp.training.kim.bar.dto.entity.AddOffersRequest;
import gp.training.kim.bar.repository.OfferRepository;
import gp.training.kim.bar.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class OrderControllerTest extends AbstractBarTest {

	@MockBean
	private OrderRepository orderRepository;

	@MockBean
	private OfferRepository offerRepository;

	@Test
	public void testMyOrdersPrivateTable() throws Exception {
		loadTestResources();

		//given
		final String clientLogin = "BenDelat";
		final HttpHeaders auth = getAuthorizationHeader(clientLogin);
		final Long tableId = 2L;
		final UserDBO clientUserDBO = getAuthInfos().get(clientLogin).getUser();
		final Optional<OrderDBO> userOrder = getOrders().values().stream()
				.filter(orderDBO -> orderDBO.getTable().getId().equals(tableId))
				.filter(orderDBO -> orderDBO.getUser() != null && orderDBO.getUser().equals(clientUserDBO))
				.findAny();
		final Optional<OrderDBO> tableOrder = getOrders().values().stream()
				.filter(orderDBO -> orderDBO.getTable().getId().equals(tableId) && orderDBO.getUser() == null)
				.findAny();

		given(userRepository.findByLogin(clientLogin)).willReturn(Optional.of(clientUserDBO));
		given(orderRepository.findByUserAndPaidFalse(clientUserDBO)).willReturn(userOrder);
		//noinspection OptionalGetWithoutIsPresent
		given(orderRepository.findByTableAndUserIsNullAndPaidFalse(userOrder.get().getTable()))
				.willReturn(tableOrder);

		//when
		mockMvc.perform(get("/orders")
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
				// then
				.andExpect(status().isOk())
				.andExpect(content().json(response));

		verify(userRepository, times(1)).findByLogin(any());
		verify(orderRepository, times(1)).findByUserAndPaidFalse(any());
		verify(orderRepository, times(1)).findByTableAndUserIsNullAndPaidFalse(any());
	}

	@Test
	public void testMyOrdersPublicTable() throws Exception {
		loadTestResources();

		//given
		final String clientLogin = "BenDelat";
		final HttpHeaders auth = getAuthorizationHeader(clientLogin);
		final UserDBO clientUserDBO = getAuthInfos().get(clientLogin).getUser();
		final Optional<OrderDBO> userOrder = getOrders().values().stream()
				.filter(orderDBO -> !orderDBO.getTable().isPrivate())
				.filter(orderDBO -> orderDBO.getUser() != null && orderDBO.getUser().equals(clientUserDBO))
				.findAny();

		given(userRepository.findByLogin(clientLogin)).willReturn(Optional.of(clientUserDBO));
		given(orderRepository.findByUserAndPaidFalse(clientUserDBO)).willReturn(userOrder);

		//when
		mockMvc.perform(get("/orders")
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
				// then
				.andExpect(status().isOk())
				.andExpect(content().json(response));

		verify(userRepository, times(1)).findByLogin(any());
		verify(orderRepository, times(1)).findByUserAndPaidFalse(any());
		verify(orderRepository, times(0)).findByTableAndUserIsNullAndPaidFalse(any());
	}

	@Test
	public void testMyOrdersUserNotFound() throws Exception {
		loadTestResources();

		//given
		final String clientLogin = "BenDelat";
		final HttpHeaders auth = getAuthorizationHeader(clientLogin);

		given(userRepository.findByLogin(clientLogin)).willReturn(Optional.empty());

		//when
		mockMvc.perform(get("/orders")
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
				// then
				.andExpect(status().isUnauthorized())
				.andExpect(content().json(response));

		verify(userRepository, times(1)).findByLogin(any());
		verify(orderRepository, times(0)).findByUserAndPaidFalse(any());
		verify(orderRepository, times(0)).findByTableAndUserIsNullAndPaidFalse(any());
	}

	@Test
	public void testMyOrdersOrderNotFound() throws Exception {
		loadTestResources();

		//given
		final String clientLogin = "BenDelat";
		final HttpHeaders auth = getAuthorizationHeader(clientLogin);
		final UserDBO clientUserDBO = getAuthInfos().get(clientLogin).getUser();

		given(userRepository.findByLogin(clientLogin)).willReturn(Optional.of(clientUserDBO));
		given(orderRepository.findByUserAndPaidFalse(clientUserDBO)).willReturn(Optional.empty());

		//when
		mockMvc.perform(get("/orders")
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
				// then
				.andExpect(status().isBadRequest())
				.andExpect(content().json(response));

		verify(userRepository, times(1)).findByLogin(any());
		verify(orderRepository, times(1)).findByUserAndPaidFalse(any());
		verify(orderRepository, times(0)).findByTableAndUserIsNullAndPaidFalse(any());
	}

	@Test
	public void testGetCheck() throws Exception {
		loadTestResources();

		//given
		final String clientLogin = "BenDelat";
		final HttpHeaders auth = getAuthorizationHeader(clientLogin);
		final UserDBO clientUserDBO = getAuthInfos().get(clientLogin).getUser();
		final Optional<OrderDBO> order = getOrders().values().stream()
				.filter(orderDBO -> orderDBO.getUser() != null && orderDBO.getUser().equals(clientUserDBO))
				.findAny();
		//noinspection OptionalGetWithoutIsPresent
		final List<OrderOfferDBO> orderOffers = order.get().getOrderOffers();
		orderOffers.add(createOrderOffer(order.get(), getOffers().get(2L), 3));
		orderOffers.add(createOrderOffer(order.get(), getOffers().get(12L), 4));
		orderOffers.add(createOrderOffer(order.get(), getOffers().get(7L), 2));
		orderOffers.add(createOrderOffer(order.get(), getOffers().get(3L), 6));


		given(orderRepository.getOrderDBOByIdAndPaidFalse(order.get().getId())).willReturn(order);

		//when
		mockMvc.perform(get("/orders/" + order.get().getId())
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
				// then
				.andExpect(status().isOk())
				.andExpect(content().json(response));

		verify(orderRepository, times(1)).getOrderDBOByIdAndPaidFalse(any());
	}

	@Test
	public void testGetCheckOrderDoesNotExist() throws Exception {
		loadTestResources();

		//given
		final String clientLogin = "BenDelat";
		final HttpHeaders auth = getAuthorizationHeader(clientLogin);
		final Long orderId = 70L;

		given(orderRepository.getOrderDBOByIdAndPaidFalse(orderId)).willReturn(Optional.empty());

		//when
		mockMvc.perform(get("/orders/" + orderId)
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
				// then
				.andExpect(status().isBadRequest())
				.andExpect(content().json(response));

		verify(orderRepository, times(1)).getOrderDBOByIdAndPaidFalse(any());
	}

	@Test
	public void testAddOffers() throws Exception {
		loadTestResources();

		//given
		final String clientLogin = "LadyEnvy";
		final HttpHeaders auth = getAuthorizationHeader(clientLogin);
		final UserDBO clientUserDBO = getAuthInfos().get(clientLogin).getUser();
		final Optional<OrderDBO> order = getOrders().values().stream()
				.filter(orderDBO -> orderDBO.getUser() != null && orderDBO.getUser().equals(clientUserDBO))
				.findAny();
		final AddOffersRequest addOffersRequest = objectMapper.readValue(request, AddOffersRequest.class);
		final List<OfferDBO> requestedOffers = getOffers().entrySet().stream()
				.filter(entry -> addOffersRequest.getOffers().containsKey(entry.getKey()))
				.map(Map.Entry::getValue).collect(Collectors.toList());
		//noinspection OptionalGetWithoutIsPresent
		final List<OrderOfferDBO> orderOffers = order.get().getOrderOffers();

		given(orderRepository.getOrderDBOByIdAndPaidFalse(order.get().getId())).willReturn(order);
		given(offerRepository.findByIdIn(addOffersRequest.getOffers().keySet()))
				.willReturn(requestedOffers);

		for (final Map.Entry<Long, Integer> entry : addOffersRequest.getOffers().entrySet()) {
			orderOffers.add(createOrderOffer(order.get(), getOffers().get(entry.getKey()), entry.getValue()));
		}

		given(offerRepository.saveAll(requestedOffers)).willReturn(requestedOffers);
		given(orderRepository.save(order.get())).willReturn(order.get());

		//when
		mockMvc.perform(patch("/orders/" + order.get().getId())
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.content(request))
				// then
				.andExpect(status().isAccepted())
				.andExpect(content().json(response));

		verify(orderRepository, times(1)).getOrderDBOByIdAndPaidFalse(any());
		verify(offerRepository, times(1)).findByIdIn(any());
		verify(offerRepository, times(1)).saveAll(any());
		// I'm not sure that this check is necessary - you need to do a lot of manipulations with nested objects
		//verify(offerRepository, times(1)).saveAll(requestedOffers);
		verify(orderRepository, times(1)).save(any());
		verify(orderRepository, times(1)).save(order.get());

		order.get().setOrderOffers(new ArrayList<>());
	}

	@Test
	public void testAddOffersNotEnoughIngredients() throws Exception {
		loadTestResources();

		//given
		final String clientLogin = "LadyEnvy";
		final HttpHeaders auth = getAuthorizationHeader(clientLogin);
		final UserDBO clientUserDBO = getAuthInfos().get(clientLogin).getUser();
		final Optional<OrderDBO> order = getOrders().values().stream()
				.filter(orderDBO -> orderDBO.getUser() != null && orderDBO.getUser().equals(clientUserDBO))
				.findAny();

		final AddOffersRequest addOffersRequest = objectMapper.readValue(request, AddOffersRequest.class);

		//noinspection OptionalGetWithoutIsPresent
		given(orderRepository.getOrderDBOByIdAndPaidFalse(order.get().getId())).willReturn(order);
		given(offerRepository.findByIdIn(addOffersRequest.getOffers().keySet()))
				.willReturn(getOffers().entrySet().stream()
						.filter(entry -> addOffersRequest.getOffers().containsKey(entry.getKey()))
						.map(Map.Entry::getValue).collect(Collectors.toList()));

		//when
		mockMvc.perform(patch("/orders/" + order.get().getId())
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.content(request))
				// then
				.andExpect(status().isBadRequest())
				.andExpect(content().json(response));

		verify(orderRepository, times(1)).getOrderDBOByIdAndPaidFalse(any());
		verify(offerRepository, times(1)).findByIdIn(any());
		verify(offerRepository, times(0)).saveAll(any());
		verify(orderRepository, times(0)).save(any());
	}

	@Test
	public void testGetOrdersReportOk() throws Exception {
		loadTestResources();

		//given
		final HttpHeaders auth = getAuthorizationHeader(UserRole.ADMIN);
		final List<OrderDBO> orders = getOrders().values().stream().limit(6).collect(Collectors.toList());

		for (final OrderDBO order : orders) {
			final int index = orders.indexOf(order);
			for (int i = 0; i < index + 2; i++) {
				order.getOrderOffers().add(createOrderOffer(order, getOffers().get((long) (getOffers().size() - i - index)), 1));
			}
		}
		given(orderRepository.findAllByPaidFalse()).willReturn(orders);

		//when
		mockMvc.perform(get("/orders/report")
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
				// then
				.andExpect(status().isOk())
				.andExpect(content().json(response));

		verify(orderRepository, times(1)).findAllByPaidFalse();
	}

	@Test
	public void testGetOrdersReportDenied() throws Exception {
		loadTestResources();

		//given
		final HttpHeaders auth = getAuthorizationHeader(UserRole.GUEST);

		//when
		mockMvc.perform(get("/orders/report")
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
				// then
				.andExpect(status().isForbidden())
				.andExpect(content().json(response));

		verify(orderRepository, times(0)).findAllByPaidFalse();
	}

	@Test
	public void testCloseOrder() throws Exception {
		loadTestResources();

		//given
		final HttpHeaders auth = getAuthorizationHeader(UserRole.ADMIN);
		final Long orderId = 2L;
		final OrderDBO order = getOrders().get(orderId);
		final OrderDBO updatedOrder = new OrderDBO();
		updatedOrder.setId(order.getId());
		updatedOrder.setOrderOffers(order.getOrderOffers());
		updatedOrder.setStart(order.getStart());
		updatedOrder.setEnd(order.getEnd());
		updatedOrder.setUser(order.getUser());
		updatedOrder.setTable(order.getTable());
		updatedOrder.setPaid(true);

		given(orderRepository.getOrderDBOByIdAndPaidFalse(orderId)).willReturn(Optional.of(order));
		given(orderRepository.save(order)).willReturn(updatedOrder);

		//when
		mockMvc.perform(delete("/orders/" + orderId)
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
				// then
				.andExpect(status().isAccepted());

		verify(orderRepository, times(1)).getOrderDBOByIdAndPaidFalse(any());
		verify(orderRepository, times(1)).save(order);
	}

	private OrderOfferDBO createOrderOffer(final OrderDBO order, final OfferDBO offer, final Integer amount) {
		final OrderOfferDBO orderOffer = new OrderOfferDBO();
		orderOffer.setOrder(order);
		orderOffer.setAmount(amount);
		orderOffer.setOffer(offer);

		return orderOffer;
	}
}
