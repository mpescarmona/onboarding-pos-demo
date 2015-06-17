package com.pos.onboarding.ws.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.pos.onboarding.beans.Customer;
import com.pos.onboarding.persistance.CustomerManager;
import com.pos.onboarding.ws.exception.ResourceNotFoundException;

/**
 * REST service provider
 * 
 * Only GET and POST will return values PUT and DELETE will not.
 */
@Controller
@RequestMapping("/customer")
public class RestPosCustomerController extends RestExceptionHandler {

	protected static final Logger log = LogManager
			.getLogger(RestPosCustomerController.class);

	@Resource(name = "customerServicePostgres")
	private CustomerManager customerManager;

	/**
	 * Customer methods
	 */

	@RequestMapping(value = "", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	public @ResponseBody List<Customer> getAllCategories(
			@RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "size", required = false, defaultValue = "100") int pageSize) {
		log.debug("Provider has received request to get all customers");

		// Call service here
		List<Customer> result = new ArrayList<Customer>();
		result = customerManager.getAllCategories(pageNumber, pageSize);

		log.debug(
				"Return of request to get all customers. Method params: {}. Result: {}", result);

		return result;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public Customer getCustomer(@PathVariable("id") Long id) {
		log.debug("Provider has received request to get customer with id: "
				+ id);

		// Call service here
		Customer customer = customerManager.getCustomerById(id);
		if (customer == null) {
			throw new ResourceNotFoundException(id);
		}

		log.debug(
				"Return of request to get customer by Id. Method params: {}. Result: {}", id, customer);
		
		return customer;
	}

	@RequestMapping(value = "", method = RequestMethod.POST, headers = "Accept=application/xml, application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public Customer addCustomer(HttpServletResponse response, @Valid @RequestBody Customer customer) {
		log.debug("Provider has received request to add new customer");

		// Call service to here
		Customer newCustomer = customerManager.createCustomer(customer); 

		log.debug(
				"Return of request to add new customer. Method params: {}. Result: {}", customer, newCustomer);
		
		return newCustomer;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers = "Accept=application/xml, application/json")
	public @ResponseBody boolean updateCustomer(@PathVariable("id") Long id,
			@Valid @RequestBody Customer customer) {
		log.debug("Provider has received request to edit customer with id: "
				+ id);

		// Call service here
		customer.setId(id);
		boolean result = customerManager.updateCustomer(customer);

		log.debug(
				"Return of request to edit customer by Id. Method params: {}. Result: {}", id, result);
		
		return result;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public Boolean deleteCustomer(@PathVariable("id") Long id) {
		log.debug("Provider has received request to delete customer with id: "
				+ id);

		// Call service here
		Boolean result = customerManager.removeCustomer(id);

		log.debug(
				"Return of request to delete customer by Id. Method params: {}. Result: {}", id, result);
		
		return result;
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public Long getCount() {
		log.debug("Provider has received request to get customer count");

		// Call service here
		Long result = customerManager.getCount();

		log.debug(
				"Return of request to get customer count. Result: {}", result);
		
		return result;
	}
}
