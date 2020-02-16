package gp.training.kim.bar.service.impl;

import gp.training.kim.bar.converter.UserConverter;
import gp.training.kim.bar.dbo.AuthInfoDBO;
import gp.training.kim.bar.dbo.UserDBO;
import gp.training.kim.bar.dto.UserSignUpRequest;
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
    public void signUp(final UserSignUpRequest userSignUpRequest) throws SuchUserAlreadyExistException {
        if (authInfoRepository.findByLogin(userSignUpRequest.getLogin()).isPresent()) {
            throw new SuchUserAlreadyExistException("User with login=" + userSignUpRequest.getLogin() + " already exists");
        }
        saveUser(userSignUpRequest);
    }

    private void saveUser(final UserSignUpRequest userSignUpRequest) {
        final UserDBO userEntity = userConverter.convertToDbo(userSignUpRequest);
        final UserDBO savedUser = userRepository.save(userEntity);
        saveAuthInfo(userSignUpRequest, savedUser);
    }

    private void saveAuthInfo(final UserSignUpRequest request, final UserDBO savedUser) {
        final AuthInfoDBO authInfoEntity = new AuthInfoDBO();
        authInfoEntity.setLogin(request.getLogin());
        authInfoEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        authInfoEntity.setUser(savedUser);
        authInfoRepository.save(authInfoEntity);
    }
}
