package gp.training.kim.bar;

import gp.training.kim.bar.constant.UserRole;
import gp.training.kim.bar.dbo.AuthInfoDBO;
import gp.training.kim.bar.dbo.IngredientDBO;
import gp.training.kim.bar.dbo.OrderDBO;
import gp.training.kim.bar.dbo.TableDBO;
import gp.training.kim.bar.dbo.TableImageDBO;
import gp.training.kim.bar.dbo.UserDBO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MockDBOsStorage {
	public final static Map<String, AuthInfoDBO> authInfos = new HashMap<>() {{
		final List<String> users = Arrays.asList("BenDelat", "LadyEnvy", "LadySpite", "KalamMekhar");

		for (final String login: users) {
			final Long id = (long) users.indexOf(login) + 1;

			final AuthInfoDBO authInfo = new AuthInfoDBO();
			authInfo.setLogin(login);
			authInfo.setPassword(login.toLowerCase());
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

			put(login, authInfo);
		}
	}};

	public static Map<Long, TableDBO> tables = new HashMap<>() {{
		for (long index = 1; index < 7; index++) {
			final TableDBO table = new TableDBO();
			table.setId(index);
			table.setPrivate(index != 1);
			table.setCapacity((int) (3 + (index % 4)));
			table.setName("Table " + index);
			table.setDescription("Table " + index + " description");

			final List<TableImageDBO> images = new ArrayList<>();
			table.setImages(images);
			for (long imageId = 0; imageId < index % 3; imageId++ ) {
				final TableImageDBO image = new TableImageDBO();
				image.setId(imageId);
				image.setTable(table);
				image.setImageURL("images/table/" + index + "/" + imageId + ".jpg");
				images.add(image);
			}

			put(index, table);
		}
	}};

	public static Map<Long, IngredientDBO> ingredients = new HashMap<>() {{

	}};

	public static Map<Long, OrderDBO> orders = new HashMap<>() {{
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
			for (final UserDBO user: authInfos.values()
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

	private static String splitCamelCase(final String s) {
		return s.replaceAll(
				String.format("%s|%s|%s",
						"(?<=[A-Z])(?=[A-Z][a-z])",
						"(?<=[^A-Z])(?=[A-Z])",
						"(?<=[A-Za-z])(?=[^A-Za-z])"
				),
				" "
		);
	}
}
