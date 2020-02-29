package gp.training.kim.bar.service;

import gp.training.kim.bar.dbo.UserDBO;
import gp.training.kim.bar.dto.entity.UserSignUpRequest;
import gp.training.kim.bar.exception.SuchUserAlreadyExistException;
import gp.training.kim.bar.exception.UserNotFoundException;

import java.util.List;

public interface AuthService {
	void signUp(UserSignUpRequest userSignUpRequest) throws SuchUserAlreadyExistException;

	UserDBO getUserByLogin(String login) throws UserNotFoundException;

	List<UserDBO> getUsersByLogins(List<String> logins) throws UserNotFoundException;
}
