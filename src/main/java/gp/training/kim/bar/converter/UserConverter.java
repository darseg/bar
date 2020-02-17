package gp.training.kim.bar.converter;

import gp.training.kim.bar.dto.UserDTO;
import gp.training.kim.bar.enums.UserRole;
import org.springframework.stereotype.Service;

@Service
public class UserConverter extends AbstractConverter<gp.training.kim.bar.dbo.UserDBO, UserDTO> {
    @Override
    public UserDTO convertToDto(gp.training.kim.bar.dbo.UserDBO userDBO) {
        return null;
    }

    @Override
    public gp.training.kim.bar.dbo.UserDBO convertToDbo(UserDTO userDTO) {
        return new gp.training.kim.bar.dbo.UserDBO(userDTO.getFio(), userDTO.getPhone(), UserRole.GUEST);
    }
}
