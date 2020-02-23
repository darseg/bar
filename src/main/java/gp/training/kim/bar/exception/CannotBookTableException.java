package gp.training.kim.bar.exception;

import gp.training.kim.bar.constant.ErrorType;

public class CannotBookTableException extends AbstractBarException {

	public CannotBookTableException() {
		super(ErrorType.CANNOT_BOOK);
	}

	public CannotBookTableException(final String message) {
		super(message, ErrorType.CANNOT_BOOK);
	}
}