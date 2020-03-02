package gp.training.kim.bar;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gp.training.kim.bar.dto.entity.Orders;
import gp.training.kim.bar.dto.entity.Tables;
import gp.training.kim.bar.dto.entity.UserSignInResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Sql({"/data.sql"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class BarDemo {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	final ResourceLoader resourceLoader = new DefaultResourceLoader();

	@Test
	public void testGuestDemo() throws Exception {
		final HttpHeaders friendAuth = signUpFriend();
		final HttpHeaders leadAuth = signUpLead();
		final Long tableId = getFreeTables();
		final Orders leadOrders = bookTable(leadAuth, tableId);
		getMenu();
		addOffersToPrivateLeadOrder_1(leadAuth, leadOrders.getUserOrder());
		addOffersToPrivateLeadOrder_2(leadAuth, leadOrders.getUserOrder());
		getLeadCheck(leadAuth, leadOrders.getUserOrder());
		addOffersToTableOrder_1(leadAuth, leadOrders.getTableOrder());
		final Orders friendOrders = getFriendOrders(friendAuth);
		addOffersToPrivateFriendOrder(friendAuth, friendOrders.getUserOrder());
		getFriendCheck(friendAuth, friendOrders.getUserOrder());
		addOffersToTableOrder_2(friendAuth, friendOrders.getTableOrder());
		getTableCheckByLead(leadAuth, leadOrders.getTableOrder());
		getTableCheckByFriend(friendAuth, friendOrders.getTableOrder());

		// As you can see, all orders and storehouse changes are applied
		final HttpHeaders auth = signIn();
		getOffersReport(auth);
		getOrdersReport(auth);
		getIngredientsReport(auth);

		closeOrder(auth, leadOrders.getUserOrder());
		getLeadCheckAfterClosing(auth, leadOrders.getUserOrder());
		getOrdersReportAfterClosing(auth);
	}

	@Test
	public void testAdminDemo() throws Exception {
		final HttpHeaders auth = signIn();
		addOffer(auth);
		getOffersReport(auth);
		getOrdersReport(auth);
		getIngredientsReport(auth);
	}

	private HttpHeaders signUp(final String request) throws Exception {
		return getAuth(mockMvc.perform(post("/sign-up")
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.content(request))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.token").isString())
				.andReturn().getResponse().getContentAsString());
	}

	private HttpHeaders signIn() throws Exception {
		return getAuth(mockMvc.perform(post("/sign-in")
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.content(getRequest()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.token").isString())
				.andReturn().getResponse().getContentAsString());
	}

	private HttpHeaders getAuth(final String response) throws JsonProcessingException {
		final HttpHeaders authHeader = new HttpHeaders();
		authHeader.set(
				HttpHeaders.AUTHORIZATION, "Bearer " + objectMapper.readValue(
						response, UserSignInResponse.class).getToken());

		return authHeader;
	}

	private HttpHeaders signUpFriend() throws Exception {
		return signUp(getRequest());
	}

	private HttpHeaders signUpLead() throws Exception {
		return signUp(getRequest());
	}

	private Long getFreeTables() throws Exception {
		final String response = mockMvc.perform(get("/tables")
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.param("capacity", "3")
				.param("start", "2020-03-05T20:00")
				.param("end", "2020-03-05T23:00"))
				.andExpect(status().isOk())
				.andExpect(content().json(getResponse()))
				.andReturn().getResponse().getContentAsString();

		return objectMapper.readValue(response, Tables.class).getTables().get(0).getId();
	}

	private Orders bookTable(final HttpHeaders auth, final Long tableId) throws Exception {
		final String response = mockMvc.perform(post("/tables/" + tableId + "/book")
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.content(getRequest()))
				.andExpect(status().isCreated())
				.andExpect(content().json(getResponse()))
				.andReturn().getResponse().getContentAsString();

		return objectMapper.readValue(response, Orders.class);
	}

	private void getMenu() throws Exception {
		mockMvc.perform(get("/offers")
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
				.andExpect(status().isOk())
				.andExpect(content().json(getResponse()));
	}

	private void addOffersToOrder(final HttpHeaders auth, final Long orderId, final String request, final String response) throws Exception {
		mockMvc.perform(patch("/orders/" + orderId)
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.content(request))
				.andExpect(status().isAccepted())
				.andExpect(content().json(response));
	}

	private void addOffersToPrivateLeadOrder_1(final HttpHeaders auth, final Long orderId) throws Exception {
		addOffersToOrder(auth, orderId, getRequest(), getResponse());
	}

	private void addOffersToPrivateLeadOrder_2(final HttpHeaders auth, final Long orderId) throws Exception {
		addOffersToOrder(auth, orderId, getRequest(), getResponse());
	}

	private void addOffersToPrivateFriendOrder(final HttpHeaders auth, final Long orderId) throws Exception {
		addOffersToOrder(auth, orderId, getRequest(), getResponse());
	}

	private void addOffersToTableOrder_1(final HttpHeaders auth, final Long orderId) throws Exception {
		addOffersToOrder(auth, orderId, getRequest(), getResponse());
	}

	private void addOffersToTableOrder_2(final HttpHeaders auth, final Long orderId) throws Exception {
		addOffersToOrder(auth, orderId, getRequest(), getResponse());
	}

	private void getCheck(final HttpHeaders auth, final Long orderId, final String response) throws Exception {
		mockMvc.perform(get("/orders/" + orderId)
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
				.andExpect(status().isOk())
				.andExpect(content().json(response));
	}

	private void getLeadCheck(final HttpHeaders auth, final Long orderId) throws Exception {
		getCheck(auth, orderId, getResponse());
	}

	private void getLeadCheckAfterClosing(final HttpHeaders auth, final Long orderId) throws Exception {
		mockMvc.perform(get("/orders/" + orderId)
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
				.andExpect(status().isBadRequest())
				.andExpect(content().json(getResponse()));
	}

	private void getFriendCheck(final HttpHeaders auth, final Long orderId) throws Exception {
		getCheck(auth, orderId, getResponse());
	}

	private void getTableCheckByLead(final HttpHeaders auth, final Long orderId) throws Exception {
		getCheck(auth, orderId, getResponse());
	}

	private void getTableCheckByFriend(final HttpHeaders auth, final Long orderId) throws Exception {
		getCheck(auth, orderId, getResponse());
	}

	private Orders getFriendOrders(final HttpHeaders auth) throws Exception {
		final String response = mockMvc.perform(get("/orders")
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
				.andExpect(status().isOk())
				.andExpect(content().json(getResponse()))
				.andReturn().getResponse().getContentAsString();

		return objectMapper.readValue(response, Orders.class);
	}

	private void getOffersReport(final HttpHeaders auth) throws Exception {
		mockMvc.perform(get("/offers/report")
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
				.andExpect(status().isOk())
				.andExpect(content().json(getResponse()));
	}

	private void ordersReport(final HttpHeaders auth, final String response) throws Exception {
		mockMvc.perform(get("/orders/report")
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
				.andExpect(status().isOk())
				.andExpect(content().json(response));
	}

	private void getOrdersReport(final HttpHeaders auth) throws Exception {
		ordersReport(auth, getResponse());
	}

	private void getOrdersReportAfterClosing(final HttpHeaders auth) throws Exception {
		ordersReport(auth, getResponse());
	}

	private void getIngredientsReport(final HttpHeaders auth) throws Exception {
		mockMvc.perform(get("/ingredients/report")
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
				.andExpect(status().isOk())
				.andExpect(content().json(getResponse()));
	}

	private void closeOrder(final HttpHeaders auth, final Long orderId) throws Exception {
		mockMvc.perform(delete("/orders/" + orderId)
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
				.andExpect(status().isAccepted());
	}

	private void addOffer(final HttpHeaders auth) throws Exception {
		mockMvc.perform(post("/offers")
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.content(getRequest()))
				.andExpect(status().isCreated())
				.andExpect(content().json(getResponse()));
	}

	private String getRequest() throws IOException {
		return loadFileAsString("rq.json");
	}

	private String getResponse() throws IOException {
		return loadFileAsString("rs.json");
	}

	private String loadFileAsString(final String fileName) throws IOException {
		final StackTraceElement[] stackTraceElements = new Throwable().getStackTrace();

		final Resource resource = resourceLoader.getResource(String.format(
				"classpath:json/BarDemo/%s/%s/%s",
				stackTraceElements[3].getMethodName(),
				stackTraceElements[2].getMethodName(),
				fileName));

		if (resource.exists()) {
			return FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream(), UTF_8));
		}
		return null;
	}
}
