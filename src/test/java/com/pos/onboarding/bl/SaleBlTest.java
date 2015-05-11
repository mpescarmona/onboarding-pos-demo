package com.pos.onboarding.bl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import com.pos.onboarding.beans.Sale;

public class SaleBlTest {

	private static final Long SALE_ID = 1l;
	private static final Long PAYMENT_METHOD__ID = 1l;
	private static final Long CUSTOMER_ID = 1l;
	private static final Date SALE_BILL_DATE = new Date();
	private static final Boolean SALE_STATUS = false;

	@Test
	public void testSale() {
		Sale validSale = getValidSale();
		SaleBl saleBl = new SaleBl();

		assertTrue(saleBl.validateSale(validSale));
		assertEquals(validSale, saleBl.createSale(validSale));
	}

	private static Sale getValidSale() {
		Sale sale = new Sale();
		sale.setId(SALE_ID);
		sale.setPaymentMethodId(PAYMENT_METHOD__ID);
		sale.setCustomerId(CUSTOMER_ID);
		sale.setBillDate(SALE_BILL_DATE);
		sale.setBilled(SALE_STATUS);

		return sale;
	}
}
