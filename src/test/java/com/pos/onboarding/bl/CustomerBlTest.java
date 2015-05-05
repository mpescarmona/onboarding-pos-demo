package com.pos.onboarding.bl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.pos.onboarding.beans.Customer;

public class CustomerBlTest {

	private static final Long CUSTOMER_ID = 1l;
	private static final String CUSTOMER_FIRST_NAME = "Customer First Name";
	private static final String CUSTOMER_LAST_NAME = "Customer Last Name";
	private static final String CUSTOMER_EMAIL = "test@test.com";
	private static final String CUSTOMER_PHONE_NUMBER = "+56 9 12345678";

	@Test
	public void testCustomer() {
		Customer validCustomer = getValidCustomer();
		CustomerBl customerBl = new CustomerBl();

		assertTrue(customerBl.validateCustomer(validCustomer));
		customerBl.createCustomer(validCustomer);
		assertEquals(validCustomer, customerBl.createCustomer(validCustomer));
	}

	private static Customer getValidCustomer() {
		Customer customer = new Customer();
		customer.setId(CUSTOMER_ID);
		customer.setFirstName(CUSTOMER_FIRST_NAME);
		customer.setLastName(CUSTOMER_LAST_NAME);
		customer.setEmail(CUSTOMER_EMAIL);
		customer.setPhoneNumber(CUSTOMER_PHONE_NUMBER);

		return customer;
	}
}
