package gp.training.kim.bar.exception;

import gp.training.kim.bar.constant.ErrorType;

public class BarCannotBookTableException extends AbstractBarException {

	public BarCannotBookTableException(final String message) {
		super(message, ErrorType.CANNOT_BOOK);
	}
}