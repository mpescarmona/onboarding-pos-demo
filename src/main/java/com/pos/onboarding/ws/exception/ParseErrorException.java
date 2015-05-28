package com.pos.onboarding.ws.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ParseErrorException extends RuntimeException {

	private static final long serialVersionUID = 4553464302467525828L;

	private Object entity;

	public ParseErrorException() {
	}
	
	public ParseErrorException(Object entity) {
		this.entity = entity;
	}

	public ParseErrorException(Object entity, StackTraceElement[] stackTrace) {
		this.entity = entity;
		this.setStackTrace(stackTrace);
	}

	public Object getEntity() {
		return entity;
	}

}
