package gp.training.kim.bar.service.impl;

import gp.training.kim.bar.converter.UserConverter;
import gp.training.kim.bar.dbo.AuthInfoDBO;
import gp.training.kim.bar.dbo.UserDBO;
import gp.training.kim.bar.dto.UserDTO;
import gp.training.kim.bar.exception.SuchUserAlreadyExistException;
import gp.training.kim.bar.repository.AuthInfoRepository;
import gp.training.kim.bar.repository.UserRepository;
import gp.training.kim.bar.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;

	private final AuthInfoRepository authInfoRepository;

	private final UserConverter userConverter;

	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void signUp(final UserDTO userDTO) throws SuchUserAlreadyExistException {
		if (authInfoRepository.findByLogin(userDTO.getLogin()).isPresent()) {
			throw new SuchUserAlreadyExistException("User with login=" + userDTO.getLogin() + " already exists");
		}
		saveUser(userDTO);
	}

	private void saveUser(final UserDTO userDTO) {
		final UserDBO userDBO = userConverter.convertToDbo(userDTO);
		final UserDBO savedUser = userRepository.save(userDBO);
		saveAuthInfo(userDTO, savedUser);
	}

	private void saveAuthInfo(final UserDTO userDTO, final UserDBO savedUser) {
		final AuthInfoDBO authInfoEntity = new AuthInfoDBO();
		authInfoEntity.setLogin(userDTO.getLogin());
		authInfoEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		authInfoEntity.setUser(savedUser);
		authInfoRepository.save(authInfoEntity);
	}
}
