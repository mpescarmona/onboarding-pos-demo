package com.pos.onboarding.bl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.pos.onboarding.bean.PaymentMethod;

public class PaymentMethodBlTest {

	private static final Long PAYMENT_METHOD_ID = 1l;
	private static final String PAYMENT_METHOD_NAME = "Test VISA";
	private static final String PAYMENT_METHOD_URL = "http://www.testvisaurl.com";
	private static final String PAYMENT_METHOD_PHONE_NUMBER = "+56 9 12345678";

	@Test
	public void testPaymentMethod() {
		PaymentMethod validPaymentMethod = getValidPaymentMethod();
		PaymentMethodBl paymentMethodBl = new PaymentMethodBl();

		assertTrue(paymentMethodBl.validatePaymentMethod(validPaymentMethod));
		assertEquals(validPaymentMethod, paymentMethodBl.createPaymentMethod(validPaymentMethod));
	}

	private static PaymentMethod getValidPaymentMethod() {
		PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.setId(PAYMENT_METHOD_ID);
		paymentMethod.setName(PAYMENT_METHOD_NAME);
		paymentMethod.setUrl(PAYMENT_METHOD_URL);
		paymentMethod.setPhoneNumber(PAYMENT_METHOD_PHONE_NUMBER);

		return paymentMethod;
	}
}
