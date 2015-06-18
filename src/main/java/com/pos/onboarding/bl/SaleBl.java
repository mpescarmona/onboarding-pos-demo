package com.pos.onboarding.bl;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pos.onboarding.bean.Sale;

public class SaleBl {
	private static final Logger log = LogManager.getLogger(SaleBl.class);
	private Sale sale;

	public Sale createSale(Sale newSale) {
		log.trace("Enter method createSale. method params: {}", newSale);

		if (validateSale(newSale)) {
			this.sale = newSale;
		}

		log.trace("Return method createSale. method params: {}. Result: {}",
				newSale, sale);

		return sale;
	}

	public boolean validateSale(Sale sale) {
		log.trace("Enter method validateSale. method params: {}", sale);
		boolean isValid = true;

		log.debug("validating Sale's payment method.");
		if (sale.getPaymentMethodId() == null) {
			log.error("The sale's payment method must be provided");
			isValid = false;
		} else {
			if (sale.getPaymentMethodId() < 0l) {
				log.error("The sale's payment method must be positive");
				isValid = false;
			}
		}

		log.debug("validating sale's customer.");
		if (sale.getCustomerId() == null) {
			log.error("The sale's customer must be provided");
			isValid = false;
		} else {
			if (sale.getCustomerId() < 0l) {
				log.error("The sale's customer must be positive");
				isValid = false;
			}
		}

		log.debug("validating sale's URL.");
		if (sale.getBillDate() == null) {
			log.error("The bill date of the sale must be provided");
			isValid = false;
		} else {
			if (sale.getBillDate().after(new Date())) {
				log.error("The date of the sale must not be in the future");
				isValid = false;
			}
		}

		log.debug("validating sale's bill status.");
		if (sale.isBilled() == null) {
			log.error("The sale's bill status must be provided");
			isValid = false;
		}

		log.trace("Return method validateSale. method params: {}. Result: {}",
				sale, isValid);

		return isValid;
	}

}
