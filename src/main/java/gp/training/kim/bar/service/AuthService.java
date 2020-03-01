package gp.training.kim.bar.service;

import gp.training.kim.bar.dbo.UserDBO;
import gp.training.kim.bar.dto.entity.UserSignUpRequest;
import gp.training.kim.bar.exception.BarSuchUserAlreadyExistException;
import gp.training.kim.bar.exception.BarUserNotFoundException;

import java.util.List;

public interface AuthService {
	void signUp(UserSignUpRequest userSignUpRequest) throws BarSuchUserAlreadyExistException;

	UserDBO getUserByLogin(String login) throws BarUserNotFoundException;

	List<UserDBO> getUsersByLogins(List<String> logins) throws BarUserNotFoundException;
}
