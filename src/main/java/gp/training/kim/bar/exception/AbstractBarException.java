package gp.training.kim.bar.exception;

import gp.training.kim.bar.constant.ErrorType;
import lombok.Getter;

public abstract class AbstractBarException extends Exception {

	@Getter
	protected final ErrorType errorType;

	AbstractBarException(final String message, final ErrorType errorType) {
		super(message);
		this.errorType = errorType;
	}
}
