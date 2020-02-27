package gp.training.kim.bar.controller;

import gp.training.kim.bar.AbstractBarTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static gp.training.kim.bar.constant.UserRole.GUEST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TableControllerTest extends AbstractBarTest {


	@Test
	public void testGetFreeTables() throws Exception {
		loadTestResources();
		//given
		//when
		mockMvc.perform(get("/tables")
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.param("capacity", "3")
				.param("start", "2020-02-25T19:00")
				.param("end", "2020-02-25T23:00"))
				// then
				.andExpect(status().isOk())
				.andExpect(content().json(response));
	}

	@Test
	public void testGetFreePrivateTables() throws Exception {
		loadTestResources();
		//given
		//when
		mockMvc.perform(get("/tables")
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.param("capacity", "3")
				.param("start", "2020-02-25T19:00")
				.param("end", "2020-02-25T23:00")
				.param("private", "true"))
				// then
				.andExpect(status().isOk())
				.andExpect(content().json(response));
	}

	@Test
	public void testBookPrivateTable() throws Exception {
		loadTestResources();
		//given
		final HttpHeaders auth = getAuthorizationHeader(GUEST);
		final Long tableId = 1L;
		//when
		mockMvc.perform(post("/tables/" + tableId + "/book")
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.content(request))
				// then
				.andExpect(status().isCreated())
				.andExpect(content().json(response));
	}

	@Test
	public void testBookPublicTable() throws Exception {
		loadTestResources();
		//given
		final HttpHeaders auth = getAuthorizationHeader("LadyEnvy");
		final Long tableId = 5L;
		//when
		mockMvc.perform(post("/tables/" + tableId + "/book")
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.content(request))
				// then
				.andExpect(status().isCreated())
				.andExpect(content().json(response));
	}

	@Test
	public void testBookTableDoesNotExist() throws Exception {
		loadTestResources();
		//given
		final HttpHeaders auth = getAuthorizationHeader(GUEST);
		final Long tableId = 50L;
		//when
		mockMvc.perform(post("/tables/" + tableId + "/book")
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.content(request))
				// then
				.andExpect(status().isBadRequest())
				.andExpect(content().json(response));
	}

	@Test
	public void testBookTableIsAlreadyReserved() throws Exception {
		loadTestResources();
		//given
		final HttpHeaders auth = getAuthorizationHeader("LadyEnvy");
		final Long tableId = 2L;
		//when
		mockMvc.perform(post("/tables/" + tableId + "/book")
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.content(request))
				// then
				.andExpect(status().isBadRequest())
				.andExpect(content().json(response));
	}

	@Test
	public void testBookPublicTableNotEnoughGuests() throws Exception {
		loadTestResources();
		//given
		final HttpHeaders auth = getAuthorizationHeader(GUEST);
		final Long tableId = 5L;
		//when
		mockMvc.perform(post("/tables/" + tableId + "/book")
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.content(request))
				// then
				.andExpect(status().isBadRequest())
				.andExpect(content().json(response));
	}
}
