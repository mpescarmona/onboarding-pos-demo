package com.pos.onboarding.ui.controller;

import java.beans.PropertyEditorSupport;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.pos.onboarding.beans.Customer;
import com.pos.onboarding.ui.util.Link;
import com.pos.onboarding.ui.util.LinksUtil;
import com.pos.onboarding.util.CustomJsonParser;

/**
 * Handles and retrieves customer data
 */
@Controller
@RequestMapping("/customer-ui")
public class UiPosCustomerController {
	
	@Value("${webservice.url}")
	private String BASE_URL;
	
	@Value("${web.ui.pagesize}")
	private final String PAGE_SIZE="10"; 

	@Value("${ui.url.prefix}")
	private String UI_URL_PREFIX;
	
	RestTemplate restTemplate = new RestTemplate();
	
	protected static final Logger log = LogManager
			.getLogger(UiPosCustomerController.class);

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		
		binder.registerCustomEditor(Collection.class, "customer.id", new PropertyEditorSupport() {
			@Override
			public void setAsText(String customerId) throws IllegalArgumentException {
				if (customerId != null && !customerId.isEmpty()) {
					if (customerId.trim().equalsIgnoreCase("NONE")) {
						setValue(null);
					} else {
						setValue(Integer.valueOf(customerId));
					}
				} else {
					setValue(null);
				}
			}

			@Override
			public String getAsText() {
				return getValue() != null ? getValue().toString()
						.replace("[", "").replace("]", "") : "";
			}
		});
		
		binder.registerCustomEditor(Collection.class, "customer", new PropertyEditorSupport() {
			@Override
			public void setAsText(String customerId) throws IllegalArgumentException {
				if (customerId != null && !customerId.isEmpty()) {
					if (customerId.trim().equalsIgnoreCase("NONE")) {
						setValue(null);
					} else {
						setValue(Integer.valueOf(customerId));
					}
				} else {
					setValue(null);
				}
			}

			@Override
			public String getAsText() {
				return getValue() != null ? getValue().toString()
						.replace("[", "").replace("]", "") : "";
			}
		});
	}
	
	
	/**
	 * Customer UI
	 */
	/**
	 * Handles and retrieves all customers and show it in a JSP page
	 * 
	 * @return the name of the JSP page
	 */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getCustomerList(@RequestParam(value="page", required=false, defaultValue="1") Integer page, 
   					              @RequestParam(value="size", required=false, defaultValue=PAGE_SIZE) Integer size, 
						          @RequestParam(value="filter", required=false, defaultValue="") String filter, 
						          @RequestParam(value="sort", required=false, defaultValue="") String sort, 
						          @RequestParam(value="order", required=false, defaultValue="") String order,
						          Model model) {
    	
    	log.debug("Received request to show all customers");
    	
    	// Retrieve all Categories by delegating the call to API
    	log.debug("Calling WS");
		String showPage = LinksUtil.getPageParams(page, size, filter, sort, order);
    	List<Customer> customers = restTemplate.getForObject(BASE_URL + "/ws/customer" + showPage, List.class);
    	
    	log.debug("customers [" + customers.toString() + "]");

    	model.addAttribute("customers", customers);
    	
    	Long count = restTemplate.getForObject(BASE_URL + "/ws/customer/count", Long.class);
    	List<Link> links = LinksUtil.getPageLinks(page, size,  filter, sort, order, count);
    	
    	model.addAttribute("links", links);
    	log.debug("links [" + links.toString() + "]");

    	return "customerList";
	}
    

    /**
     * Retrieves the add page
     * 
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getAddCustomer(Model model) {
    	log.debug("Received request to show add page");
    
    	model.addAttribute("customerAttribute", new Customer());

    	return "customerAdd";
	}

    
    /**
     * Adds a new customer by delegating the processing to API call.
     * Displays a confirmation JSP page
     * 
     * @return  the name of the JSP page
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addCustomer(@Valid @ModelAttribute("customerAttribute") Customer customer, BindingResult result) {
		log.debug("Received request to add new customer");
		
		String resultString = "redirect:/ui/customer-ui";
		
		if (result.hasErrors()) {
			resultString = "customerAdd";
		} else {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Accept", "application/json");
			
			String customerString = "";
			customerString = CustomJsonParser.ObjectToJsonString(customer);
			HttpEntity<String> entity = new HttpEntity<String>(customerString, headers);
			log.debug("Calling WS with JSON [" + customerString + "] of Customer [" + customer.toString() + "]");
			
			restTemplate.postForLocation(BASE_URL + "/ws/customer", entity);
		}
    	
		return resultString;
	}
    
    
    /**
     * Deletes an existing customer by delegating the processing to API call.
     * Displays a confirmation JSP page
     * 
     * @return  the name of the JSP page
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteCustomer(@RequestParam(value="id", required=true) Integer id, Model model) {
		log.debug("Received request to delete existing customer");
		
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.set("Accept", "application/json");
    	HttpEntity entity = new HttpEntity(headers);
		
    	log.debug("Calling WS");
		restTemplate.exchange(BASE_URL + "/ws/customer/" + id.toString(), HttpMethod.DELETE, entity, Boolean.class);
    	
		return "redirect:/ui/customer-ui";
	}

    
    /**
     * Retrieves the edit page
     * 
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String getEditCustomer(@RequestParam(value="id", required=true) Integer id, Model model) {
    	log.debug("Received request to show edit page");
    
    	Customer customer = restTemplate.getForObject(BASE_URL + "/ws/customer/" + id.toString(), Customer.class);
    	model.addAttribute("customerAttribute", customer);
    	
    	return "customerEdit";
	}
    
    
    /**
     * Edits an existing customer by delegating the processing to CustomerService.
     * Displays a confirmation JSP page
     * 
     * @return  the name of the JSP page
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String saveEditCustomer(@Valid @ModelAttribute("customerAttribute") Customer customer, BindingResult result, 
    										   @RequestParam(value="id", required=true) Long id, 
    												Model model) {
    	log.debug("Received request to update customer");
    
    	String resultString = "redirect:/ui/customer-ui";
    	
    	if (result.hasErrors()) {
    		resultString = "customerEdit";
    	} else {
    		customer.setId(id);
    		
    		HttpHeaders headers = new HttpHeaders();
    		headers.setContentType(MediaType.APPLICATION_JSON);
    		headers.set("Accept", "application/json");
    		
    		String customerString = "";
    		customerString = CustomJsonParser.ObjectToJsonString(customer);
    		HttpEntity<String> entity = new HttpEntity<String>(customerString, headers);
    		log.debug("Calling WS with JSON [" + customerString + "] of Customer [" + customer.toString() + "]");
    		
    		restTemplate.put(BASE_URL + "/ws/customer/" + id.toString(), entity);
    	}
    	
		return resultString;
	}
}
