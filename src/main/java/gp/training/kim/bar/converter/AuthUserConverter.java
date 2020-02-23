package gp.training.kim.bar.converter;

import gp.training.kim.bar.constant.UserRole;
import gp.training.kim.bar.dbo.UserDBO;
import gp.training.kim.bar.dto.UserDTO;
import gp.training.kim.bar.dto.entity.UserSignUpRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthUserConverter extends AbstractConverter<UserDBO, UserSignUpRequest> {
	public AuthUserConverter() {
		super(new String[]{"password", "role"});
	}

	@Override
	public UserSignUpRequest convertToDto(final UserDBO userDBO) {
		return null;
	}

	@Override
	public UserDBO convertToDbo(final UserSignUpRequest userSignUpRequest) {
		final UserDBO userDBO = super.convertToDbo(userSignUpRequest);

		userDBO.setRole(UserRole.GUEST);

		return  userDBO;
	}

	@Override
	protected UserSignUpRequest constructDto() {
		return null;
	}

	@Override
	protected UserDBO constructDbo() {
		return new UserDBO();
	}
}
