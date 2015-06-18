package com.pos.onboarding.bean;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

/**
 * This class is used to hold the customer data.-
 * 
 * @author Mario Pescarmona
 *
 */
public class Customer {
	private Long id;
	@NotBlank(message = "First Name is mandatory")
	private String firstName;
	@NotBlank(message = "Last Name is mandatory")
	private String lastName;
	@NotBlank(message = "Phone Number is mandatory")
	private String phoneNumber;
	@NotBlank(message = "Email is mandatory")
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
