package gp.training.kim.bar.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class OfferControllerTest extends AbstractBarTest {

    @Test
	public void testGetMenu() throws Exception {
		loadTestResources();
		//given
		//when
		mockMvc.perform(get("/offers")
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
				// then
				.andExpect(status().isOk())
				.andExpect(content().json(response));
	}
}
