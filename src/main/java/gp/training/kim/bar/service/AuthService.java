package gp.training.kim.bar.service;

import gp.training.kim.bar.dto.UserSignUpRequest;
import gp.training.kim.bar.exception.SuchUserAlreadyExistException;
import org.springframework.transaction.annotation.Transactional;

public interface AuthService {
    void signUp(UserSignUpRequest userSignUpRequest) throws SuchUserAlreadyExistException;
}
