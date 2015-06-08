package com.pos.onboarding.persistance;

import java.util.List;

import com.pos.onboarding.beans.Customer;

public interface CustomerManager {
	/**
	 * Gets a customer by customer Id
	 * 
	 * @param customerId
	 *            the customer Id
	 * @return the customer instance. Null when the customer is not found
	 */
	public Customer getCustomerById(Long customerId);

	/**
	 * Creates a new instance of {@link Customer} with the given object.
	 * 
	 * @param customer
	 *            the customer object to be created.
	 * @return the customer created. This should by updated with the proper
	 *         customer Id
	 */
	public Customer createCustomer(Customer customer);

	/**
	 * Updates the given {@link Customer} instance
	 * 
	 * @param customer
	 *            the customer object to be updated.
	 * @return true when the customer was properly created, otherwise, false.
	 */
	public boolean updateCustomer(Customer customer);

	/**
	 * Removes the given {@link Customer} instance
	 * 
	 * @param customerId
	 * @return true when the customer was properly removed, otherwise, false.
	 */
	public boolean removeCustomer(Long customerId);

	/**
	 * Retrieves all the Customers.
	 * 
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	List<Customer> getAllCategories(int pageNumber, int pageSize);

	/**
	 * Retrieves the total number of customers
	 * 
	 * @return
	 */
	public Long getCount();

}
