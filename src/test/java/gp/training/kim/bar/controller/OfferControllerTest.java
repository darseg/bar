package gp.training.kim.bar.controller;

import gp.training.kim.bar.repository.OfferRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class OfferControllerTest extends AbstractBarTest {

	@MockBean
	private OfferRepository offerRepository;

	@Test
	public void testGetMenu() throws Exception {
		loadTestResources();
		//given
		given(offerRepository.findAll()).willReturn(new ArrayList<>(getOffers().values()));
		//when
		mockMvc.perform(get("/offers")
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
				// then
				.andExpect(status().isOk())
				.andExpect(content().json(response));

		verify(offerRepository, times(1)).findAll();
	}
}
