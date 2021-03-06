package com.pos.onboarding.ws.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4553464302467525828L;

	private Long resourceId;

	public ResourceNotFoundException(Long resourceId) {
		this.resourceId = resourceId;
	}

	public ResourceNotFoundException(Integer resourceId) {
		this.resourceId = Long.valueOf(resourceId);
	}

	public Long getResourceId() {
		return resourceId;
	}

}
