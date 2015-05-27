package com.pos.onboarding.ws.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

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

import com.pos.onboarding.beans.Category;
import com.pos.onboarding.persistance.CategoryManager;
import com.pos.onboarding.ws.exception.ResourceNotFoundException;

/**
 * REST service provider
 * 
 * Only GET and POST will return values PUT and DELETE will not.
 */
@Controller
public class RestPosController {

	protected static final Logger log = LogManager
			.getLogger(RestPosController.class);

	@Resource(name = "categoryServicePostgres")
	private CategoryManager categoryManager;

	/**
	 * Category methods
	 */

	@RequestMapping(value = "/categories", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	public @ResponseBody List<Category> getAllCategories(
			@RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "size", required = false, defaultValue = "100") int pageSize) {
		log.debug("Provider has received request to get all categories");

		// Call service here
		List<Category> result = new ArrayList<Category>();
		result = categoryManager.getAllCategories();

		return result;
	}

	@RequestMapping(value = "/category/{id}", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public Category getCategory(@PathVariable("id") Long id) {
		log.debug("Provider has received request to get category with id: "
				+ id);

		// Call service here
		Category category = categoryManager.getCategoryById(id);
		if (category == null) {
			throw new ResourceNotFoundException(id);
		}

		return category;
	}

	@RequestMapping(value = "/category", method = RequestMethod.POST, headers = "Accept=application/xml, application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public Category addCategory(@RequestBody Category category) {
		log.debug("Provider has received request to add new category");

		// Call service to here
		return categoryManager.createCategory(category);
	}

	@RequestMapping(value = "/category/{id}", method = RequestMethod.PUT, headers = "Accept=application/xml, application/json")
	public @ResponseBody boolean updateCategory(@PathVariable("id") Long id,
			@RequestBody Category category) {
		log.debug("Provider has received request to edit category with id: "
				+ id);

		// Call service here
		category.setId(id);
		return categoryManager.updateCategory(category);
	}

	@RequestMapping(value = "/category/{id}", method = RequestMethod.DELETE, headers = "Accept=application/xml, application/json")
	public @ResponseBody void deleteCategory(@RequestBody Category category) {
		log.debug("Provider has received request to delete category with id: "
				+ category.getId());

		// Call service here
		categoryManager.removeCategory(category);
	}
}
