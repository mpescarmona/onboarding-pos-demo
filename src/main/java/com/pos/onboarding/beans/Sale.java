package com.pos.onboarding.beans;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * This class is used to hold the sales data.-
 * 
 * @author Mario Pescarmona
 *
 */
public class Sale {
	private Long id;
	private Long paymentMethodId;
	private Long customerId;
	private Date billDate;
	private Boolean billed;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPaymentMethodId() {
		return paymentMethodId;
	}

	public void setPaymentMethodId(Long paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long cutomerId) {
		this.customerId = cutomerId;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public Boolean isBilled() {
		return billed;
	}

	public void setBilled(Boolean billed) {
		this.billed = billed;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
