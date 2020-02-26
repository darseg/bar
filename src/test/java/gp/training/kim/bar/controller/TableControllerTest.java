package gp.training.kim.bar.controller;

import gp.training.kim.bar.AbstractBarTest;
import gp.training.kim.bar.constant.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
		//when
		mockMvc.perform(post("/tables/1/book")
				.header("Authorization", getAuthorizationHeader(GUEST))
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.content(request))
				// then
				.andExpect(status().isCreated())
				.andExpect(content().json(response));
	}
}
