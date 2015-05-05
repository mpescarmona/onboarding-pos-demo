package com.pos.onboarding.bl;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pos.onboarding.beans.Customer;

public class CustomerBl {
	private static final Logger logger = LogManager.getLogger(CustomerBl.class);
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Customer createCustomer(Customer newCustomer) {
		logger.trace("Enter method createCustomer. method params: {}",
				newCustomer);

		if (validateCustomer(newCustomer)) {
			this.customer = newCustomer;
		}

		logger.trace(
				"Return method createCustomer. method params: {}. Result: {}",
				newCustomer, customer);

		return customer;
	}

	public boolean validateCustomer(Customer customer) {
		logger.trace("Enter method validateCustomer. method params: {}",
				customer);
		boolean isValid = true;

		logger.debug("validating customer first name.");
		if (StringUtils.isBlank(customer.getFirstName())) {
			logger.error("The customer first name must be provided");
			isValid = false;
		}

		logger.debug("validating customer last name.");
		if (StringUtils.isBlank(customer.getLastName())) {
			logger.error("The customer last name must be provided");
			isValid = false;
		}

		logger.debug("validating customer phone number.");
		if (StringUtils.isBlank(customer.getPhoneNumber())) {
			logger.error("The customer phone number must be provided");
			isValid = false;
		}

		logger.debug("validating customer email.");
		if (StringUtils.isBlank(customer.getEmail())) {
			logger.error("The customer email must be provided");
			isValid = false;
		}

		logger.trace(
				"Return method validateCustomer. method params: {}. Result: {}",
				customer, isValid);

		return isValid;
	}

}
