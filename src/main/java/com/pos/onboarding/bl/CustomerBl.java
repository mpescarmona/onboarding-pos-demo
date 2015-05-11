package com.pos.onboarding.bl;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pos.onboarding.beans.Customer;

public class CustomerBl {
	private static final Logger log = LogManager.getLogger(CustomerBl.class);
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Customer createCustomer(Customer newCustomer) {
		log.trace("Enter method createCustomer. method params: {}",
				newCustomer);

		if (validateCustomer(newCustomer)) {
			this.customer = newCustomer;
		}

		log.trace(
				"Return method createCustomer. method params: {}. Result: {}",
				newCustomer, customer);

		return customer;
	}

	public boolean validateCustomer(Customer customer) {
		log.trace("Enter method validateCustomer. method params: {}",
				customer);
		boolean isValid = true;

		log.debug("validating customer first name.");
		if (StringUtils.isBlank(customer.getFirstName())) {
			log.error("The customer first name must be provided");
			isValid = false;
		}

		log.debug("validating customer last name.");
		if (StringUtils.isBlank(customer.getLastName())) {
			log.error("The customer last name must be provided");
			isValid = false;
		}

		log.debug("validating customer phone number.");
		if (StringUtils.isBlank(customer.getPhoneNumber())) {
			log.error("The customer phone number must be provided");
			isValid = false;
		}

		log.debug("validating customer email.");
		if (StringUtils.isBlank(customer.getEmail())) {
			log.error("The customer email must be provided");
			isValid = false;
		}

		log.trace(
				"Return method validateCustomer. method params: {}. Result: {}",
				customer, isValid);

		return isValid;
	}

}
