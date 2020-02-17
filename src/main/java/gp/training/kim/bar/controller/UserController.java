package gp.training.kim.bar.controller;

import gp.training.kim.bar.dto.UserDTO;
import gp.training.kim.bar.dto.entity.Menu;
import gp.training.kim.bar.dto.entity.UserSignInRequest;
import gp.training.kim.bar.dto.entity.UserSignInResponse;
import gp.training.kim.bar.exception.SuchUserAlreadyExistException;
import gp.training.kim.bar.security.JwtUtil;
import gp.training.kim.bar.service.AuthService;
import gp.training.kim.bar.service.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@AllArgsConstructor
public class UserController {

    private final OfferService offerService;

    @GetMapping(value = "/menu", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    public Menu menu() {
        return offerService.getMenu();
    }
}