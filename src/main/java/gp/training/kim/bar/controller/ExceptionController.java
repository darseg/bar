package gp.training.kim.bar.controller;

import gp.training.kim.bar.constant.ErrorType;
import gp.training.kim.bar.dto.entity.ApiError;
import gp.training.kim.bar.exception.AbstractBarException;
import gp.training.kim.bar.exception.CannotBookTableException;
import gp.training.kim.bar.exception.OfferIsNotAvailableException;
import gp.training.kim.bar.exception.OrderNotFoundException;
import gp.training.kim.bar.exception.SuchUserAlreadyExistException;
import lombok.extern.java.Log;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.logging.Level;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Log
public class ExceptionController extends ResponseEntityExceptionHandler {

	@ExceptionHandler({
			SuchUserAlreadyExistException.class,
			CannotBookTableException.class,
			OrderNotFoundException.class,
			OfferIsNotAvailableException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private ResponseEntity<Object> handleBadRequest(final AbstractBarException e) {
		log.log(Level.SEVERE, e.getMessage(), e);
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, e.getErrorType().getCode(), e.getMessage()));
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	private ResponseEntity<Object> handleBadRequest(final Exception e) {
		log.log(Level.SEVERE, e.getMessage(), e);
		return buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED, ErrorType.USER_NOT_FOUND.getCode(), e.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	private ResponseEntity<Object> handleUndefinedError(final Exception e) {
		log.log(Level.SEVERE, e.getMessage(), e);
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.UNDEFINED.getCode(), e.getMessage()));
	}

	private ResponseEntity<Object> buildResponseEntity(final ApiError apiError) {
		return new ResponseEntity<Object>(apiError, apiError.getStatus());
	}
}