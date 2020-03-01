package gp.training.kim.bar.exception;

import gp.training.kim.bar.constant.ErrorType;

public class BarCannotCreateEntity extends AbstractBarException {

	public BarCannotCreateEntity(final String message) {
		super(message, ErrorType.CANNOT_CREATE_ENTITY);
	}
}