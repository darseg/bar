package gp.training.kim.bar.constant;

import lombok.Getter;

public enum ErrorType {
	UNDEFINED(0),
	USER_ALREADY_EXIST(1),
	USER_NOT_FOUND(2),
	CANNOT_BOOK(3),
	ORDER_NOT_FOUND(4);

	@Getter
	private final Integer code;

	ErrorType(Integer code) {
		this.code = code;
	}
}