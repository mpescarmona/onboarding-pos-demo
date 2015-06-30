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

import com.pos.onboarding.bean.Category;
import com.pos.onboarding.persistence.CategoryManager;
import com.pos.onboarding.ws.exception.ResourceNotFoundException;

/**
 * REST service provider
 * 
 * Only GET and POST will return values PUT and DELETE will not.
 */
@Controller
@RequestMapping("/category")
public class RestPosCategoryController extends RestExceptionHandler {

	protected static final Logger log = LogManager
			.getLogger(RestPosCategoryController.class);

//	@Resource(name = "categoryServicePostgres")
	@Resource(name = "categoryServiceCSV")
	private CategoryManager categoryManager;

	/**
	 * Category methods
	 */

	@RequestMapping(value = "", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	public @ResponseBody List<Category> getAllCategories(
			@RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "size", required = false, defaultValue = "100") int pageSize) {
		log.debug("Provider has received request to get all categories");

		// Call service here
		List<Category> result = new ArrayList<Category>();

		result = categoryManager.getAllCategories(pageNumber, pageSize);

		log.debug(
				"Return of request to get all categories. Method params: {}. Result: {}",
				result);

		return result;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public Category getCategory(@PathVariable("id") Long id) {
		log.debug("Provider has received request to get category with id: "
				+ id);

		// Call service here
		Category category = categoryManager.getCategoryById(id);
		if (category == null) {
			throw new ResourceNotFoundException(id);
		}

		log.debug(
				"Return of request to get category by Id. Method params: {}. Result: {}",
				id, category);

		return category;
	}

	@RequestMapping(value = "", method = RequestMethod.POST, headers = "Accept=application/xml, application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public Category addCategory(HttpServletResponse response,
			@Valid @RequestBody Category category) {
		log.debug("Provider has received request to add new category");

		// Call service to here
		Category newCategory = categoryManager.createCategory(category);

		log.debug(
				"Return of request to add new category. Method params: {}. Result: {}",
				category, newCategory);

		return newCategory;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers = "Accept=application/xml, application/json")
	public @ResponseBody boolean updateCategory(@PathVariable("id") Long id,
			@RequestBody Category category) {
		log.debug("Provider has received request to edit category with id: "
				+ id);

		// Call service here
		category.setId(id);
		boolean result = categoryManager.updateCategory(category);
		if (!result) {

		}

		log.debug(
				"Return of request to edit category by Id. Method params: {}. Result: {}",
				id, result);

		return result;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public Boolean deleteCategory(@PathVariable("id") Long id) {
		log.debug("Provider has received request to delete category with id: "
				+ id);

		// Call service here
		Boolean result = categoryManager.removeCategory(id);

		log.debug(
				"Return of request to delete category by Id. Method params: {}. Result: {}",
				id, result);

		return result;
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public Long getCount() {
		log.debug("Provider has received request to get category count");

		// Call service here
		Long result = categoryManager.getCount();

		log.debug("Return of request to get category count. Result: {}", result);

		return result;
	}
}
