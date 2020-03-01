package gp.training.kim.bar.controller;

import gp.training.kim.bar.constant.OfferType;
import gp.training.kim.bar.constant.UserRole;
import gp.training.kim.bar.dbo.IngredientDBO;
import gp.training.kim.bar.dbo.OfferDBO;
import gp.training.kim.bar.dbo.OfferImageDBO;
import gp.training.kim.bar.dbo.OfferParamDBO;
import gp.training.kim.bar.dbo.RecipeRowDBO;
import gp.training.kim.bar.dbo.embeddable.RecipeRowId;
import gp.training.kim.bar.dto.OfferDTO;
import gp.training.kim.bar.repository.IngredientRepository;
import gp.training.kim.bar.repository.OfferRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class OfferControllerTest extends AbstractBarTest {

	@MockBean
	private OfferRepository offerRepository;

	@MockBean
	private IngredientRepository ingredientRepository;

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

	@Test
	public void testGetOffersReport() throws Exception {
		loadTestResources();

		//given
		final HttpHeaders auth = getAuthorizationHeader(UserRole.ADMIN);
		final List<OfferDBO> offers = new ArrayList<>(getOffers().values());

		given(offerRepository.findAll()).willReturn(offers);

		//when
		mockMvc.perform(get("/offers/report")
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
				// then
				.andExpect(status().isOk())
				.andExpect(content().json(response));

		verify(offerRepository, times(1)).findAll();
	}

	@Test
	public void testAddOfferOk() throws Exception {
		loadTestResources();

		//given
		final HttpHeaders auth = getAuthorizationHeader(UserRole.ADMIN);
		final OfferDTO offerRequest = objectMapper.readValue(request, OfferDTO.class);
		final OfferDBO newOffer = new OfferDBO();
		newOffer.setId((long) (getOffers().size() + 1));
		newOffer.setName(offerRequest.getName());
		newOffer.setType(OfferType.valueOf(offerRequest.getType().toUpperCase()));
		newOffer.setDescription(offerRequest.getDescription());
		newOffer.setPrice(offerRequest.getPrice());
		newOffer.setParams(offerRequest.getParams().entrySet().stream()
				.map(entry -> {
					final OfferParamDBO offerParam = new OfferParamDBO();
					offerParam.setOffer(newOffer);
					offerParam.setName(entry.getKey());
					offerParam.setValue(entry.getValue());

					return offerParam;
				})
				.collect(Collectors.toList()));
		final List<RecipeRowDBO> recipeRows = new ArrayList<>();
		offerRequest.getIngredients().forEach((key, value) -> {
			final RecipeRowDBO recipeRow = new RecipeRowDBO();
			final IngredientDBO ingredient = getIngredients().get(key);
			recipeRow.setId(new RecipeRowId(newOffer.getId(), ingredient.getId()));
			recipeRow.setOffer(newOffer);
			recipeRow.setIngredient(ingredient);
			recipeRow.setAmount(value);

			recipeRows.add(recipeRow);
		});
		newOffer.setRecipeRows(recipeRows);
		newOffer.setImages(offerRequest.getImages().stream()
				.map(s -> {
					final OfferImageDBO offerImage = new OfferImageDBO();
					offerImage.setOffer(newOffer);
					offerImage.setImageURL(s);

					return offerImage;
				})
				.collect(Collectors.toList()));

		given(ingredientRepository.findAllByIdIn(offerRequest.getIngredients().keySet()))
				.willReturn(getIngredients().values().stream()
						.filter(ingredientDBO -> offerRequest.getIngredients().containsKey(ingredientDBO.getId()))
						.collect(Collectors.toList()));
		given(offerRepository.save(newOffer)).willReturn(newOffer);

		//when
		mockMvc.perform(post("/offers")
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.content(request))
				// then
				.andExpect(status().isCreated())
				.andExpect(content().json(response));

		verify(ingredientRepository, times(1)).findAllByIdIn(any());
		verify(offerRepository, times(1)).save(any());
	}

	@Test
	public void testAddOfferWrongIngredient() throws Exception {
		loadTestResources();

		//given
		final HttpHeaders auth = getAuthorizationHeader(UserRole.ADMIN);
		final OfferDTO offerRequest = objectMapper.readValue(request, OfferDTO.class);

		given(ingredientRepository.findAllByIdIn(offerRequest.getIngredients().keySet()))
				.willReturn(getIngredients().values().stream()
						.filter(ingredientDBO -> offerRequest.getIngredients().containsKey(ingredientDBO.getId()))
						.collect(Collectors.toList()));

		//when
		mockMvc.perform(post("/offers")
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.content(request))
				// then
				.andExpect(status().isBadRequest())
				.andExpect(content().json(response));

		verify(ingredientRepository, times(1)).findAllByIdIn(any());
		verify(offerRepository, times(0)).save(any());
	}
}
