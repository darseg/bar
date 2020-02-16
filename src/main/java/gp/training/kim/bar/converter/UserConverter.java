package gp.training.kim.bar.converter;

import gp.training.kim.bar.dbo.OfferDBO;
import gp.training.kim.bar.dbo.UserDBO;
import gp.training.kim.bar.dto.OfferDTO;
import gp.training.kim.bar.dto.UserSignUpRequest;
import gp.training.kim.bar.enums.UserRole;
import org.springframework.stereotype.Service;

@Service
public class UserConverter extends AbstractConverter<UserDBO, UserSignUpRequest> {
    @Override
    public UserSignUpRequest convertToDto(UserDBO userDBO) {
        return null;
    }

    @Override
    public UserDBO convertToDbo(UserSignUpRequest userSignUpRequest) {
        return new UserDBO(userSignUpRequest.getFio(), userSignUpRequest.getPhone(), UserRole.GUEST);
    }
}
