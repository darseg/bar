package gp.training.kim.bar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gp.training.kim.bar.constant.BarConstants;
import gp.training.kim.bar.constant.OfferType;
import gp.training.kim.bar.constant.UserRole;
import gp.training.kim.bar.dbo.AuthInfoDBO;
import gp.training.kim.bar.dbo.IngredientDBO;
import gp.training.kim.bar.dbo.IngredientStoreHouseDBO;
import gp.training.kim.bar.dbo.OfferDBO;
import gp.training.kim.bar.dbo.OfferImageDBO;
import gp.training.kim.bar.dbo.OfferParamDBO;
import gp.training.kim.bar.dbo.OrderDBO;
import gp.training.kim.bar.dbo.RecipeRowDBO;
import gp.training.kim.bar.dbo.TableDBO;
import gp.training.kim.bar.dbo.TableImageDBO;
import gp.training.kim.bar.dbo.UserDBO;
import gp.training.kim.bar.dbo.embeddable.RecipeRowId;
import gp.training.kim.bar.repository.AuthInfoRepository;
import gp.training.kim.bar.repository.UserRepository;
import gp.training.kim.bar.security.JwtUtil;
import gp.training.kim.bar.security.LoadUserDetailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AbstractBarTest {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected JwtUtil jwtUtil;

	@Autowired
	protected PasswordEncoder passwordEncoder;

	@Autowired
	protected LoadUserDetailService userDetailsService;

	@Autowired
	protected ObjectMapper objectMapper;

	@MockBean
	protected UserRepository userRepository;

	@MockBean
	protected AuthInfoRepository authInfoRepository;

	protected String request;

	protected String response;

	private final long beerCount = 6L;

	private final Map<String, HttpHeaders> authorization = new HashMap<>();

	private final Map<String, AuthInfoDBO> authInfos = new HashMap<>();

	private final Map<Long, OrderDBO> orders = new HashMap<>();

	private final Map<Long, TableDBO> tables = new HashMap<>();

	private final Map<Long, IngredientDBO> ingredients = new HashMap<>();

	private final Map<Long, OfferDBO> offers = new HashMap<>();

	protected void loadTestResources() throws IOException {
		final StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
		final String testResourcesPath = String.format(
				"classpath:json/%s/%s/",
				StringUtils.substringAfterLast(stackTraceElement.getClassName(), "."),
				stackTraceElement.getMethodName());
		final ResourceLoader resourceLoader = new DefaultResourceLoader();

		this.request = loadResourceAsString(resourceLoader.getResource(testResourcesPath + "rq.json"));
		this.response = loadResourceAsString(resourceLoader.getResource(testResourcesPath + "rs.json"));
	}

	protected HttpHeaders getAuthorizationHeader(final UserRole role) {
		if (role == UserRole.ADMIN) {
			return getAuthorizationHeader("BenDelat");
		}
		return getAuthorizationHeader("LadyEnvy");
	}

	protected HttpHeaders getAuthorizationHeader(final String login) {
		given(authInfoRepository.findByLogin(login)).willReturn(Optional.of(getAuthInfos().get(login)));
		if (!authorization.containsKey(login)) {
			final HttpHeaders authHeader = new HttpHeaders();
			authHeader.set(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUtil.generateToken(
					userDetailsService.loadUserByUsername(login)));
			this.authorization.put(login, authHeader);

			return authHeader;
		}

		verify(authInfoRepository, times(1)).findByLogin(any());

		return authorization.get(login);
	}

	protected String formatLocalDateTime(final LocalDateTime localDateTime) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(BarConstants.DATE_TIME_FORMAT);

		return localDateTime.format(formatter);
	}

	private String loadResourceAsString(final Resource resource) throws IOException {
		if (resource.exists()) {
			return FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream(), UTF_8));
		}
		return null;
	}

	private static String splitCamelCase(final String s) {
		return s.replaceAll(
				String.format("%s|%s|%s",
						"(?<=[A-Z])(?=[A-Z][a-z])",
						"(?<=[^A-Z])(?=[A-Z])",
						"(?<=[A-Za-z])(?=[^A-Za-z])"
				), " ");
	}

	protected Map<String, AuthInfoDBO> getAuthInfos() {
		if (authInfos.isEmpty()) {
			final List<String> users = Arrays.asList("BenDelat", "LadyEnvy", "LadySpite", "KalamMekhar");

			for (final String login : users) {
				final Long id = (long) users.indexOf(login) + 1;

				final AuthInfoDBO authInfo = new AuthInfoDBO();
				authInfo.setLogin(login);
				authInfo.setPassword(passwordEncoder.encode(login.toLowerCase()));
				authInfo.setId(id);

				final UserDBO user = new UserDBO();
				user.setId(id);
				user.setLogin(login);
				user.setFio(splitCamelCase(login));
				user.setPhone(String.valueOf(login.length()));
				if (id == 1) {
					user.setRole(UserRole.ADMIN);
				} else {
					user.setRole(UserRole.GUEST);
				}

				authInfo.setUser(user);

				authInfos.put(login, authInfo);
			}
		}

		return authInfos;
	}

	protected void addAuthInfo(final AuthInfoDBO authInfo) {
		authInfos.put(authInfo.getLogin(), authInfo);
	}

	protected Map<Long, TableDBO> getTables() {
		if (tables.isEmpty()) {
			long imgId = 1;
			for (long index = 1; index < 7; index++) {
				final TableDBO table = new TableDBO();
				table.setId(index);
				table.setPrivate(index != 1);
				table.setCapacity((int) (3 + (index % 4)));
				table.setName("Table " + index);
				table.setDescription("Table " + index + " description");

				final List<TableImageDBO> images = new ArrayList<>();
				table.setImages(images);

				for (long j = 0; j < index % 3; j++) {
					final TableImageDBO image = new TableImageDBO();
					image.setId(imgId);
					image.setTable(table);
					image.setImageURL("images/table/" + index + "/" + j + ".jpg");
					images.add(image);
					imgId++;
				}

				tables.put(index, table);
			}
		}
		return tables;
	}

	protected Map<Long, OrderDBO> getOrders() {
		if (orders.isEmpty()) {
			Long id = 1L;
			final LocalDateTime start = LocalDateTime.parse("2020-02-25T15:00");
			final LocalDateTime end = LocalDateTime.parse("2020-02-25T17:00");

			for (final TableDBO tableDBO : getTables().values()) {
				final boolean paid = tableDBO.getId() == 5L;
				if (tableDBO.isPrivate()) {
					final OrderDBO order = new OrderDBO();
					order.setTable(tableDBO);
					order.setId(id);
					order.setStart(start);
					order.setEnd(end);
					order.setPaid(paid);
					order.setOrderOffers(new ArrayList<>());

					orders.put(id, order);
				}

				id++;
				for (final UserDBO user : getAuthInfos().values()
						.stream().map(AuthInfoDBO::getUser).collect(Collectors.toList())) {
					final OrderDBO order = new OrderDBO();
					order.setId(id);
					order.setTable(tableDBO);
					order.setUser(user);
					order.setPaid(paid);
					order.setStart(start);
					order.setEnd(end);
					order.setOrderOffers(new ArrayList<>());

					orders.put(id, order);
					id++;
				}
			}
		}

		return orders;
	}

	protected Map<Long, IngredientDBO> getIngredients() {
		if (ingredients.isEmpty()) {
			for (long i = 1; i <= beerCount; i++) {
				final IngredientDBO ing = new IngredientDBO();
				ing.setId(i);
				ing.setName("Beer " + i);
				ing.setCostPrice(BigDecimal.valueOf(10).multiply(BigDecimal.valueOf((i % 3)).add(BigDecimal.valueOf(9))));

				final IngredientStoreHouseDBO stored = new IngredientStoreHouseDBO();
				final BigDecimal balance = BigDecimal.valueOf(5 + 10 / (i % 4 + 1));
				stored.setBalance(balance);
				stored.setStartBalance(balance);
				stored.setIngredient(ing);

				ing.setStorehouse(stored);

				ingredients.put(i, ing);
			}

			for (long i = beerCount + 1; i <= beerCount + 16; i++) {
				final IngredientDBO ing = new IngredientDBO();
				ing.setId(i);
				ing.setName("Ingredient " + i);
				ing.setCostPrice(BigDecimal.valueOf(7).multiply(BigDecimal.valueOf((i % 4)).add(BigDecimal.valueOf(9))));

				final IngredientStoreHouseDBO stored = new IngredientStoreHouseDBO();
				final BigDecimal balance = BigDecimal.valueOf(8 + 10 / (i % 3 + 1));
				stored.setBalance(balance);
				stored.setStartBalance(balance);
				stored.setIngredient(ing);

				ing.setStorehouse(stored);
				ing.setRecipeRows(new ArrayList<>());

				ingredients.put(i, ing);
			}

		}

		return ingredients;
	}

	protected Map<Long, OfferDBO> getOffers() {
		if (offers.isEmpty()) {
			long innerId = 1;
			for (long i = 1; i <= beerCount + 12; i++) {
				final OfferDBO offer = new OfferDBO();

				final List<OfferImageDBO> images = new ArrayList<>();
				for (long j = 1; j <= (i % 2) + 1; j++) {
					final OfferImageDBO image = new OfferImageDBO();
					image.setId(innerId);
					image.setImageURL("images/offer/" + i + "/" + j + ".jpg");
					image.setOffer(offer);

					images.add(image);
					innerId++;
				}
				offer.setImages(images);

				final List<OfferParamDBO> offerParams = new ArrayList<>();
				innerId = 1;
				for (long j = 1; j <= (i % 3) + 1; j++) {
					final OfferParamDBO offerParam = new OfferParamDBO();
					offerParam.setId(innerId);
					offerParam.setName("Param " + j);
					offerParam.setValue(innerId % 7 + "." + innerId % 3);
					offerParam.setOffer(offer);
					offerParams.add(offerParam);

					innerId++;
				}
				offer.setParams(offerParams);

				if (i <= beerCount) {
					offer.setType(OfferType.BEER);
					offer.setName("Beer " + i);
					offer.setDescription("Beer " + i + " description");

					final RecipeRowDBO recipeRow = new RecipeRowDBO();
					recipeRow.setAmount(BigDecimal.valueOf(0.5));
					recipeRow.setId(new RecipeRowId(i, i));
					final IngredientDBO ingredient = getIngredients().get(i);
					ingredient.getRecipeRows().add(recipeRow);
					recipeRow.setIngredient(ingredient);
					recipeRow.setOffer(offer);

					offer.setRecipeRows(Collections.singletonList(recipeRow));
				} else {
					offer.setType(OfferType.FOOD);
					offer.setName("Food " + (i - beerCount));
					offer.setDescription("Food " + (i - beerCount) + " description");

					final long count = i % 3 + 1;
					final List<RecipeRowDBO> recipeRows = new ArrayList<>();
					for (long j = 0; j < count; j++) {
						final RecipeRowDBO recipeRow = new RecipeRowDBO();
						recipeRow.setAmount(BigDecimal.valueOf(5).multiply(BigDecimal.valueOf(1 + i % 2)));
						recipeRow.setOffer(offer);
						final Long ingId = getIngredientId(i, count, j);
						recipeRow.setId(new RecipeRowId(i, ingId));
						final IngredientDBO ingredient = getIngredients().get(ingId);
						ingredient.getRecipeRows().add(recipeRow);
						recipeRow.setIngredient(ingredient);

						recipeRows.add(recipeRow);
					}
					offer.setRecipeRows(recipeRows);
				}

				offer.setPrice(offer.getRecipeRows().stream()
						.map(recipeRowDBO -> recipeRowDBO.getAmount().multiply(recipeRowDBO.getIngredient().getCostPrice()))
						.reduce(BigDecimal.ZERO, (bigDecimal, augend) -> bigDecimal.add(augend).multiply(BigDecimal.valueOf(0.4))));

				offer.setId(i);
				offers.put(i, offer);
			}
		}
		return offers;
	}

	private Long getIngredientId(final Long offerId, final long count, final long num) {
		final long nonBeerIngCount = getIngredients().size() - beerCount;
		final long currentAddendum = offerId + nonBeerIngCount / count * num + 1;

		return currentAddendum < nonBeerIngCount ? beerCount + currentAddendum : beerCount + (currentAddendum - nonBeerIngCount);
	}
}
