package gp.training.kim.bar.exception;

import gp.training.kim.bar.constant.ErrorType;

public class OfferIsNotAvailableException extends AbstractBarException {

	public OfferIsNotAvailableException(final String message) {
		super(message, ErrorType.OFFER_UNAVAILABLE);
	}
}