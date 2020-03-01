package gp.training.kim.bar.exception;

import gp.training.kim.bar.constant.ErrorType;

public class CannotBookTableException extends AbstractBarException {

	public CannotBookTableException(final String message) {
		super(message, ErrorType.CANNOT_BOOK);
	}
}