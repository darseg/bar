package gp.training.kim.bar.security;

import gp.training.kim.bar.dbo.AuthInfoDBO;
import gp.training.kim.bar.repository.AuthInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoadUserDetailService implements UserDetailsService {

    private final AuthInfoRepository authInfoRepository;

    @Override
    public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {
        final Optional<AuthInfoDBO> authInfoDBO = authInfoRepository.findByLogin(login);
        if (authInfoDBO.isEmpty()) {
            throw new UsernameNotFoundException("User with login: " + login + " not found");
        } else {
            final SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
                    "ROLE_" + authInfoDBO.get().getUser().getRole().name());
            return new User(login, authInfoDBO.get().getPassword(), List.of(authority));
        }
    }
}