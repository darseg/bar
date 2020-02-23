package gp.training.kim.bar.converter;

import gp.training.kim.bar.constant.UserRole;
import gp.training.kim.bar.dbo.UserDBO;
import gp.training.kim.bar.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class UserConverter extends AbstractConverter<UserDBO, UserDTO> {
	public UserConverter() {
		super(new String[]{"login", "password", "role"});
	}

	@Override
	public UserDTO convertToDto(final UserDBO userDBO) {
		return null;
	}

	@Override
	public UserDBO convertToDbo(final UserDTO userDTO) {
		final UserDBO userDBO = super.convertToDbo(userDTO);

		userDBO.setRole(UserRole.GUEST);

		return  userDBO;
	}

	@Override
	protected UserDTO constructDto() {
		return null;
	}

	@Override
	protected UserDBO constructDbo() {
		return new UserDBO();
	}
}
