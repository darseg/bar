package gp.training.kim.bar.service.impl;

import gp.training.kim.bar.converter.AuthUserConverter;
import gp.training.kim.bar.dbo.AuthInfoDBO;
import gp.training.kim.bar.dbo.UserDBO;
import gp.training.kim.bar.dto.entity.UserSignUpRequest;
import gp.training.kim.bar.exception.BarSuchUserAlreadyExistException;
import gp.training.kim.bar.exception.BarUserNotFoundException;
import gp.training.kim.bar.repository.AuthInfoRepository;
import gp.training.kim.bar.repository.UserRepository;
import gp.training.kim.bar.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;

	private final AuthInfoRepository authInfoRepository;

	private final AuthUserConverter authUserConverter;

	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void signUp(final UserSignUpRequest userSignUpRequest) throws BarSuchUserAlreadyExistException {
		if (authInfoRepository.findByLogin(userSignUpRequest.getLogin()).isPresent()) {
			throw new BarSuchUserAlreadyExistException("User with login=" + userSignUpRequest.getLogin() + " already exists");
		}
		saveUser(userSignUpRequest);
	}

	@Override
	public UserDBO getUserByLogin(final String login) throws BarUserNotFoundException {
		final Optional<UserDBO> user = userRepository.findByLogin(login);
		if (user.isEmpty()) {
			throw new BarUserNotFoundException();
		}
		return user.get();
	}

	@Override
	public List<UserDBO> getUsersByLogins(final List<String> logins) throws BarUserNotFoundException {
		final List<UserDBO> users = userRepository.findByLoginIn(logins);
		if (users.size() != logins.size()) {
			throw new BarUserNotFoundException();
		}
		return users;
	}

	private void saveUser(final UserSignUpRequest userSignUpRequest) {
		final UserDBO userDBO = authUserConverter.convertToDbo(userSignUpRequest);
		final UserDBO savedUser = userRepository.save(userDBO);
		saveAuthInfo(userSignUpRequest, savedUser);
	}

	private void saveAuthInfo(final UserSignUpRequest userSignUpRequest, final UserDBO savedUser) {
		final AuthInfoDBO authInfoEntity = new AuthInfoDBO();
		authInfoEntity.setLogin(userSignUpRequest.getLogin());
		authInfoEntity.setPassword(passwordEncoder.encode(userSignUpRequest.getPassword()));
		authInfoEntity.setUser(savedUser);
		authInfoRepository.save(authInfoEntity);
	}
}
