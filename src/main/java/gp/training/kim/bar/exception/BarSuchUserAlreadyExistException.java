package gp.training.kim.bar.exception;

import gp.training.kim.bar.constant.ErrorType;

public class BarSuchUserAlreadyExistException extends AbstractBarException {

	public BarSuchUserAlreadyExistException(final String message) {
		super(message, ErrorType.USER_ALREADY_EXIST);
	}
}