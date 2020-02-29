package gp.training.kim.bar.mockData;

import gp.training.kim.bar.dbo.AuthInfoDBO;
import gp.training.kim.bar.dbo.OrderDBO;
import gp.training.kim.bar.dbo.TableDBO;
import gp.training.kim.bar.dbo.UserDBO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Order {
	private final static Map<String, AuthInfoDBO> authInfos = AuthInfo.authInfos;
	private final static Map<Long, TableDBO> tables = Table.tables;


	public final static Map<Long, OrderDBO> orders = new HashMap<>() {{
		Long id = 1L;
		final LocalDateTime start = LocalDateTime.parse("2020-02-25T15:00");
		final LocalDateTime end = LocalDateTime.parse("2020-02-25T17:00");

		for (final TableDBO tableDBO : tables.values()) {
			final boolean paid = tableDBO.getId() == 5L;
			if (tableDBO.isPrivate()) {
				final OrderDBO order = new OrderDBO();
				order.setTable(tableDBO);
				order.setId(id);
				order.setStart(start);
				order.setEnd(end);
				order.setPaid(paid);

				put(id, order);
			}

			id++;
			for (final UserDBO user : authInfos.values()
					.stream().map(AuthInfoDBO::getUser).collect(Collectors.toList())) {
				final OrderDBO order = new OrderDBO();
				order.setId(id);
				order.setTable(tableDBO);
				order.setUser(user);
				order.setPaid(paid);
				order.setStart(start);
				order.setEnd(end);
				put(id, order);
				id++;
			}
		}
	}};
}
