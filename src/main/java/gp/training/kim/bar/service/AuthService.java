package gp.training.kim.bar.service;

import gp.training.kim.bar.dto.UserDTO;
import gp.training.kim.bar.exception.SuchUserAlreadyExistException;

public interface AuthService {
    void signUp(UserDTO userDTO) throws SuchUserAlreadyExistException;
}
