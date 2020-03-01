package gp.training.kim.bar.controller;

import gp.training.kim.bar.constant.UserRole;
import gp.training.kim.bar.dbo.IngredientDBO;
import gp.training.kim.bar.repository.IngredientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IngredientControllerTest extends AbstractBarTest {

	@MockBean
	private IngredientRepository ingredientRepository;

	@Test
	public void testGetIngredientsReport() throws Exception {
		loadTestResources();

		//given
		final HttpHeaders auth = getAuthorizationHeader(UserRole.ADMIN);
		getOffers();
		final List<IngredientDBO> ingredients = new ArrayList<>(getIngredients().values());

		given(ingredientRepository.findAll()).willReturn(ingredients);

		//when
		mockMvc.perform(get("/ingredients/report")
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
				// then
				.andExpect(status().isOk())
				.andExpect(content().json(response));

		verify(ingredientRepository, times(1)).findAll();
	}

}
