package gp.training.kim.bar.exception;

import gp.training.kim.bar.constant.ErrorType;

public class BarOfferIsNotAvailableException extends AbstractBarException {

	public BarOfferIsNotAvailableException(final String message) {
		super(message, ErrorType.OFFER_UNAVAILABLE);
	}
}