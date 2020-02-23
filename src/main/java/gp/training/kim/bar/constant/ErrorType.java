package gp.training.kim.bar.constant;

import lombok.Getter;

public enum ErrorType {
	USER_ALREADY_EXIST(1),
	USER_NOT_FOUND(2),
	CANNOT_BOOK(3),
	UNDEFINED(0);

	@Getter
	private final Integer code;

	ErrorType(Integer code) {
		this.code = code;
	}
}