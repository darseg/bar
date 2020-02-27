package gp.training.kim.bar.controller;

import gp.training.kim.bar.AbstractBarTest;
import gp.training.kim.bar.dbo.OrderDBO;
import gp.training.kim.bar.dbo.TableDBO;
import gp.training.kim.bar.repository.OrderRepository;
import gp.training.kim.bar.repository.TableRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static gp.training.kim.bar.constant.UserRole.GUEST;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TableControllerMockDataTest extends AbstractBarTest {


	@MockBean
	private TableRepository tableRepository;

	@MockBean
	private OrderRepository orderRepository;

	@Test
	public void testBookPrivateTable() throws Exception {
		loadTestResources();
		//given
		final HttpHeaders auth = getAuthorizationHeader(GUEST);
		final Long tableId = 1L;
		final TableDBO table = mockTables.get(tableId);
		given(tableRepository.findById(tableId)).willReturn(Optional.of(table));
		given(orderRepository.existsByTableAndEndAfterAndStartBefore(
				table, LocalDateTime.parse("2020-02-25T15:00"), LocalDateTime.parse("2020-02-25T17:00"))).willReturn(false);
		//when
		mockMvc.perform(post("/tables/" + tableId + "/book")
				.headers(auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.content(request))
				// then
				.andExpect(status().isCreated())
				.andExpect(content().json(response));

		verify(orderRepository, times(1)).saveAll(Mockito.<OrderDBO>anyList());


	}

	private final Map<Long, TableDBO> mockTables = new HashMap<>() {{
		final TableDBO table = new TableDBO();
		final Long id = 1L;
		table.setId(id);
		table.setName("table 1");
		table.setDescription("at window");
		table.setCapacity(4);
		table.setPrivate(true);
		put(id, table);

	}};
}
