package com.pos.onboarding.bl;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pos.onboarding.bean.PaymentMethod;

public class PaymentMethodBl {
	private static final Logger log = LogManager.getLogger(PaymentMethodBl.class);
	private PaymentMethod paymentMethod;

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public PaymentMethod createPaymentMethod(PaymentMethod newPaymentMethod) {
		log.trace("Enter method createPaymentMethod. method params: {}",
				newPaymentMethod);

		if (validatePaymentMethod(newPaymentMethod)) {
			this.paymentMethod = newPaymentMethod;
		}

		log.trace(
				"Return method createPaymentMethod. method params: {}. Result: {}",
				newPaymentMethod, paymentMethod);

		return paymentMethod;
	}

	public boolean validatePaymentMethod(PaymentMethod PaymentMethod) {
		log.trace("Enter method validatePaymentMethod. method params: {}",
				PaymentMethod);
		boolean isValid = true;

		log.debug("validating PaymentMethod name.");
		if (StringUtils.isBlank(PaymentMethod.getName())) {
			log.error("The PaymentMethod name must be provided");
			isValid = false;
		}

		log.debug("validating PaymentMethod URL.");
		if (StringUtils.isBlank(PaymentMethod.getUrl())) {
			log.error("The PaymentMethod URL must be provided");
			isValid = false;
		}

		log.debug("validating PaymentMethod phone number.");
		if (StringUtils.isBlank(PaymentMethod.getPhoneNumber())) {
			log.error("The PaymentMethod phone number must be provided");
			isValid = false;
		}

		log.trace(
				"Return method validatePaymentMethod. method params: {}. Result: {}",
				PaymentMethod, isValid);

		return isValid;
	}

}
