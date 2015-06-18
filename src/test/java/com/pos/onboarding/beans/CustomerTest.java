package com.pos.onboarding.beans;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.pos.onboarding.bean.Customer;

public class CustomerTest {

	private static final Long CUSTOMER_ID = 1l;
	private static final String CUSTOMER_FIRST_NAME = "Customer First Name";
	private static final String CUSTOMER_LAST_NAME = "Customer Last Name";
	private static final String CUSTOMER_EMAIL = "test@test.com";
	private static final String CUSTOMER_PHONE_NUMBER = "+56 9 12345678";

	@Test
	public void testArticle() {
		Customer c = new Customer();
		c.setId(CUSTOMER_ID);
		c.setFirstName(CUSTOMER_FIRST_NAME);
		c.setLastName(CUSTOMER_LAST_NAME);
		c.setEmail(CUSTOMER_EMAIL);
		c.setPhoneNumber(CUSTOMER_PHONE_NUMBER);

		assertEquals(CUSTOMER_ID, c.getId());
		assertEquals(CUSTOMER_FIRST_NAME, c.getFirstName());
		assertEquals(CUSTOMER_LAST_NAME, c.getLastName());
		assertEquals(CUSTOMER_EMAIL, c.getEmail());
		assertEquals(CUSTOMER_PHONE_NUMBER, c.getPhoneNumber());
	}
}
