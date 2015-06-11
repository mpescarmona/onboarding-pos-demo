package com.pos.onboarding.ws.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.pos.onboarding.ws.error.resource.ErrorDescription;
import com.pos.onboarding.ws.error.resource.ErrorResource;

public class RestExceptionHandler {

	public ResponseEntity<ErrorResource> handleHibernateValidation(
			MethodArgumentNotValidException e) {

		List<FieldError> errors = e.getBindingResult().getFieldErrors();
		String errorString = "";
		List<ErrorDescription> errorDescriptionList = new ArrayList<>();

		for (FieldError error : errors) {
			ErrorDescription errorDescription = new ErrorDescription();
			errorDescription.setField(error.getField() == null ? "null" : error.getField());
			errorDescription.setMessage(error.getDefaultMessage() == null ? "null" : error.getDefaultMessage());
			errorDescription.setRejectedValue(error.getRejectedValue() == null ? "null" : error.getRejectedValue().toString());
			errorDescription.setResourceName(error.getObjectName() == null ? "null" : error.getObjectName());

			errorDescriptionList.add(errorDescription);
			errorString = errorString + error.getDefaultMessage() == null ? "null" : error.getDefaultMessage();
		}

		ErrorResource errorResource = new ErrorResource(HttpStatus.BAD_REQUEST,
				HttpStatus.BAD_REQUEST.value(), e.getMessage(), errorString,
				"link", null);

		errorResource.setErrorList(errorDescriptionList);

		return new ResponseEntity<>(errorResource, HttpStatus.BAD_REQUEST);
	}
}
