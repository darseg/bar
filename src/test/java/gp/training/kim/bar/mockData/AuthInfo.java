package gp.training.kim.bar.mockData;

import gp.training.kim.bar.constant.UserRole;
import gp.training.kim.bar.dbo.AuthInfoDBO;
import gp.training.kim.bar.dbo.UserDBO;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthInfo {
	public final static Map<String, AuthInfoDBO> authInfos = new HashMap<>() {{
		final List<String> users = Arrays.asList("BenDelat", "LadyEnvy", "LadySpite", "KalamMekhar");

		for (final String login : users) {
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

	private static String splitCamelCase(final String s) {
		return s.replaceAll(
				String.format("%s|%s|%s",
						"(?<=[A-Z])(?=[A-Z][a-z])",
						"(?<=[^A-Z])(?=[A-Z])",
						"(?<=[A-Za-z])(?=[^A-Za-z])"
				), " ");
	}
}
