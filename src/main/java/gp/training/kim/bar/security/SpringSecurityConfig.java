package gp.training.kim.bar.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import gp.training.kim.bar.constant.ErrorType;
import gp.training.kim.bar.constant.UserRole;
import gp.training.kim.bar.dto.entity.ApiError;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@AllArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	private final LoadUserDetailService userDetailsService;
	private final JwtRequestFilter jwtRequestFilter;

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http
				//.httpBasic()
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/ingredients/*").hasRole(UserRole.ADMIN.name())
				.antMatchers("/offers/*").hasRole(UserRole.ADMIN.name())
				.antMatchers(HttpMethod.POST, "/offers").hasRole(UserRole.ADMIN.name())
				.antMatchers(HttpMethod.GET, "/orders/report").hasRole(UserRole.ADMIN.name())
				.antMatchers(HttpMethod.POST, "/sign-in", "/sign-up").permitAll()
				.antMatchers(HttpMethod.GET, "/offers", "tables").permitAll()
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.formLogin().disable();
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());

	}


	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return (request, response, ex) -> {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");

			final ServletOutputStream out = response.getOutputStream();
			new ObjectMapper().writeValue(out, new ApiError(HttpStatus.FORBIDDEN, ErrorType.ACCESS_DENIED.getCode(), ex.getMessage()));
			out.flush();
		};
	}
}