package gp.training.kim.bar;

import gp.training.kim.bar.constant.UserRole;
import gp.training.kim.bar.security.JwtUtil;
import gp.training.kim.bar.security.LoadUserDetailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Sql({"/data.sql"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureMockMvc
public class AbstractBarTest {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected JwtUtil jwtUtil;

	@Autowired
	protected LoadUserDetailService userDetailsService;

	protected String request;

	protected String response;

	private final Map<String, HttpHeaders> authorization = new HashMap<>();

	protected void loadTestResources() throws IOException {
		final StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
		final String testResourcesPath = String.format(
				"classpath:test/%s/%s/",
				StringUtils.substringAfterLast(stackTraceElement.getClassName(), "."),
				stackTraceElement.getMethodName());
		final ResourceLoader resourceLoader = new DefaultResourceLoader();

		this.request = loadResourceAsString(resourceLoader.getResource(testResourcesPath + "rq.json"));
		this.response = loadResourceAsString(resourceLoader.getResource(testResourcesPath + "rs.json"));
	}

	protected HttpHeaders getAuthorizationHeader(final UserRole role) {
		if (role == UserRole.ADMIN) {
			return getAuthorizationHeader("Dars");
		}
		return getAuthorizationHeader("LadySpite");
	}

	protected HttpHeaders getAuthorizationHeader(final String login) {
		if (!authorization.containsKey(login)) {
			final HttpHeaders authHeader = new HttpHeaders();
			authHeader.set(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUtil.generateToken(
					userDetailsService.loadUserByUsername(login)));
			this.authorization.put(login, authHeader);

			return authHeader;
		}

		return authorization.get(login);
	}

	private String loadResourceAsString(final Resource resource) throws IOException {
		if (resource.exists()) {
			return FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream(), UTF_8));
		}
		return null;
	}
}
