package gp.training.kim.bar.exception;

import gp.training.kim.bar.constant.ErrorType;

public class UserNotFoundException extends AbstractBarException {
	public UserNotFoundException() {
		super(ErrorType.USER_NOT_FOUND);
	}

	public UserNotFoundException(final String message) {
		super(message, ErrorType.USER_NOT_FOUND);
	}
}
