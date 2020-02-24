package gp.training.kim.bar.exception;

import gp.training.kim.bar.constant.ErrorType;

public class OrderNotFoundException extends AbstractBarException {

	public OrderNotFoundException() {
		super(ErrorType.ORDER_NOT_FOUND);
	}

	public OrderNotFoundException(final String message) {
		super(message, ErrorType.ORDER_NOT_FOUND);
	}
}