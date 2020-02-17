package gp.training.kim.bar.controller;

import java.util.List;

import gp.training.kim.bar.dto.entity.UserSignInRequest;
import gp.training.kim.bar.dto.entity.UserSignInResponse;
import gp.training.kim.bar.dto.UserDTO;
import gp.training.kim.bar.exception.SuchUserAlreadyExistException;
import gp.training.kim.bar.security.JwtUtil;
import gp.training.kim.bar.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    private final AuthService authService;


    @PostMapping(value = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserSignInResponse singUp(@RequestBody final UserDTO userDTO) throws SuchUserAlreadyExistException {
        authService.signUp(userDTO);
        return singIn(new UserSignInRequest(userDTO.getLogin(), userDTO.getPassword()));
    }

    @PostMapping(value = "/sign-in", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserSignInResponse singIn(@RequestBody final UserSignInRequest userSignInRequest) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userSignInRequest.getLogin(), userSignInRequest.getPassword()));

        return new UserSignInResponse(
                jwtUtil.generateToken(
                        new User(userSignInRequest.getLogin(), userSignInRequest.getPassword(),
                                List.of(new SimpleGrantedAuthority("STUDENT")))));
    }
}