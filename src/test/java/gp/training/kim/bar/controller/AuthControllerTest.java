package gp.training.kim.bar.controller;

import gp.training.kim.bar.converter.AuthUserConverter;
import gp.training.kim.bar.dbo.AuthInfoDBO;
import gp.training.kim.bar.dbo.UserDBO;
import gp.training.kim.bar.dto.entity.UserSignInRequest;
import gp.training.kim.bar.dto.entity.UserSignUpRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AuthControllerTest extends AbstractBarTest {

	@Autowired
	private AuthUserConverter authUserConverter;

	@Test
	public void testSignInOk() throws Exception {
		loadTestResources();

		//given
		final UserSignInRequest userSignInRequest = objectMapper.readValue(request, UserSignInRequest.class);
		final String login = userSignInRequest.getLogin();
		given(authInfoRepository.findByLogin(login)).willReturn(Optional.of(getAuthInfos().get(login)));

		//when
		mockMvc.perform(post("/sign-in")
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.content(request))
				// then
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.token").isString());

		verify(authInfoRepository, times(1)).findByLogin(any());
	}

	@Test
	public void testSignInBadCredentials() throws Exception {
		loadTestResources();

		//given
		final UserSignInRequest userSignInRequest = objectMapper.readValue(request, UserSignInRequest.class);
		final String login = userSignInRequest.getLogin();
		given(authInfoRepository.findByLogin(login)).willReturn(Optional.empty());

		//when
		mockMvc.perform(post("/sign-in")
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.content(request))
				// then
				.andExpect(status().isUnauthorized())
				.andExpect(content().json(response));

		verify(authInfoRepository, times(1)).findByLogin(any());
	}

	@Test
	public void testSignUpOk() throws Exception {
		loadTestResources();

		//given
		final Long id = 50L;
		final UserSignUpRequest userSignUpRequest = objectMapper.readValue(request, UserSignUpRequest.class);
		final UserDBO user = authUserConverter.convertToDbo(userSignUpRequest);
		user.setId(id);
		final AuthInfoDBO authInfo = new AuthInfoDBO();
		authInfo.setId(id);
		authInfo.setLogin(userSignUpRequest.getLogin());
		authInfo.setPassword(passwordEncoder.encode(userSignUpRequest.getPassword()));
		authInfo.setUser(user);
		addAuthInfo(authInfo);

		//noinspection unchecked
		given(authInfoRepository.findByLogin(user.getLogin())).willReturn(Optional.empty(), Optional.of(authInfo));
		given(userRepository.save(user)).willReturn(user);
		given(authInfoRepository.save(authInfo)).willReturn(authInfo);

		//when
		mockMvc.perform(post("/sign-up")
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.content(request))
				// then
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.token").isString());

		verify(authInfoRepository, times(2)).findByLogin(any());
		verify(userRepository, times(1)).save(any());
		verify(authInfoRepository, times(1)).save(any());
	}

	@Test
	public void testSignUpUserAlreadyExist() throws Exception {
		loadTestResources();

		//given
		final UserSignUpRequest userSignUpRequest = objectMapper.readValue(request, UserSignUpRequest.class);
		final String login = userSignUpRequest.getLogin();

		given(authInfoRepository.findByLogin(login)).willReturn(Optional.of(getAuthInfos().get(login)));

		//when
		mockMvc.perform(post("/sign-up")
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
				.content(request))
				// then
				.andExpect(status().isBadRequest())
				.andExpect(content().json(response));

		verify(authInfoRepository, times(1)).findByLogin(any());
		verify(userRepository, times(0)).save(any());
		verify(authInfoRepository, times(0)).save(any());
	}
}
