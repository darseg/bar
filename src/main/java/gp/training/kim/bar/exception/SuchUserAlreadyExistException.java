package gp.training.kim.bar.exception;

import gp.training.kim.bar.constant.ErrorType;

public class SuchUserAlreadyExistException extends AbstractBarException {

	public SuchUserAlreadyExistException(final String message) {
		super(message, ErrorType.USER_ALREADY_EXIST);
	}
}