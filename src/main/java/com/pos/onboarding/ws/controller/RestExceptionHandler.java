package com.pos.onboarding.ws.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.pos.onboarding.ws.error.resource.ErrorDescription;
import com.pos.onboarding.ws.error.resource.ErrorResource;

public class RestExceptionHandler {
	private static final Logger log = LogManager
			.getLogger(RestExceptionHandler.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResource> handleHibernateValidation(
			MethodArgumentNotValidException e) {
		log.trace("Enter method handleHibernateValidation. method params: {}",
				e);

		List<FieldError> errors = e.getBindingResult().getFieldErrors();
		String errorString = "";
		List<ErrorDescription> errorDescriptionList = new ArrayList<>();

		for (FieldError error : errors) {
			ErrorDescription errorDescription = new ErrorDescription();
			errorDescription.setField(error.getField() == null ? "null" : error
					.getField());
			errorDescription
					.setMessage(error.getDefaultMessage() == null ? "null"
							: error.getDefaultMessage());
			errorDescription
					.setRejectedValue(error.getRejectedValue() == null ? "null"
							: error.getRejectedValue().toString());
			errorDescription
					.setResourceName(error.getObjectName() == null ? "null"
							: error.getObjectName());

			errorDescriptionList.add(errorDescription);
			errorString = errorString + error.getDefaultMessage() == null ? "null"
					: error.getDefaultMessage();
		}

		ErrorResource errorResource = new ErrorResource(HttpStatus.BAD_REQUEST,
				HttpStatus.BAD_REQUEST.value(), e.getMessage(), errorString,
				"link", null);

		errorResource.setErrorList(errorDescriptionList);

		ResponseEntity<ErrorResource> result = new ResponseEntity<ErrorResource>(
				errorResource, HttpStatus.BAD_REQUEST);
		log.trace(
				"Return method handleHibernateValidation. method params: {}. Result: {}",
				e, result);

		return result;
	}
}
