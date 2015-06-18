package com.pos.onboarding.bean;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * This class is used to hold the payment methods data.-
 * 
 * @author Mario Pescarmona
 *
 */
public class PaymentMethod {
	private Long id;
	private String name;
	private String url;
	private String phoneNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
