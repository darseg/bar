package gp.training.kim.bar.exception;

import gp.training.kim.bar.constant.ErrorType;

public class BarOrderNotFoundException extends AbstractBarException {

	public BarOrderNotFoundException(final String message) {
		super(message, ErrorType.ORDER_NOT_FOUND);
	}
}