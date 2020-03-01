package gp.training.kim.bar.exception;

import gp.training.kim.bar.constant.ErrorType;

public class BarUserNotFoundException extends AbstractBarException {
	public BarUserNotFoundException() {
		super("User does not exist", ErrorType.USER_NOT_FOUND);
	}
}
