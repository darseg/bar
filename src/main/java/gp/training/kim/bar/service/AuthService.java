package gp.training.kim.bar.service;

import gp.training.kim.bar.dto.entity.UserSignUpRequest;
import gp.training.kim.bar.exception.SuchUserAlreadyExistException;

public interface AuthService {
	void signUp(UserSignUpRequest userSignUpRequest) throws SuchUserAlreadyExistException;
}
