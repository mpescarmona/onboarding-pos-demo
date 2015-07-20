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

import com.pos.onboarding.bean.Article;
import com.pos.onboarding.persistence.ArticleManager;
import com.pos.onboarding.ws.exception.ResourceNotFoundException;

/**
 * REST service provider
 * 
 * Only GET and POST will return values PUT and DELETE will not.
 */
@Controller
@RequestMapping("/article")
public class RestPosArticleController extends RestExceptionHandler {

	protected static final Logger log = LogManager
			.getLogger(RestPosArticleController.class);

	@Resource(name = "articleServicePostgres")
	private ArticleManager articleManager;

	/**
	 * Article methods
	 */

	@RequestMapping(value = "", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	public @ResponseBody List<Article> getAllCategories(
			@RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "size", required = false, defaultValue = "100") int pageSize) {
		log.debug("Provider has received request to get all categories");

		// Call service here
		List<Article> result = new ArrayList<Article>();

		result = articleManager.getAllArticles(pageNumber, pageSize);

		log.debug(
				"Return of request to get all categories. Method params: {}. Result: {}",
				result);

		return result;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public Article getArticle(@PathVariable("id") Long id) {
		log.debug("Provider has received request to get article with id: "
				+ id);

		// Call service here
		Article article = articleManager.getArticleById(id);
		if (article == null) {
			throw new ResourceNotFoundException(id);
		}

		log.debug(
				"Return of request to get article by Id. Method params: {}. Result: {}",
				id, article);

		return article;
	}

	@RequestMapping(value = "", method = RequestMethod.POST, headers = "Accept=application/xml, application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public Article addArticle(HttpServletResponse response,
			@Valid @RequestBody Article article) {
		log.debug("Provider has received request to add new article");

		// Call service to here
		Article newArticle = articleManager.createArticle(article);

		log.debug(
				"Return of request to add new article. Method params: {}. Result: {}",
				article, newArticle);

		return newArticle;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers = "Accept=application/xml, application/json")
	public @ResponseBody boolean updateArticle(@PathVariable("id") Long id,
			@RequestBody Article article) {
		log.debug("Provider has received request to edit article with id: "
				+ id);

		// Call service here
		article.setId(id);
		boolean result = articleManager.updateArticle(article);
		if (!result) {

		}

		log.debug(
				"Return of request to edit article by Id. Method params: {}. Result: {}",
				id, result);

		return result;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public Boolean deleteArticle(@PathVariable("id") Long id) {
		log.debug("Provider has received request to delete article with id: "
				+ id);

		// Call service here
		Boolean result = articleManager.removeArticle(id);

		log.debug(
				"Return of request to delete article by Id. Method params: {}. Result: {}",
				id, result);

		return result;
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public Long getCount() {
		log.debug("Provider has received request to get article count");

		// Call service here
		Long result = articleManager.getCount();

		log.debug("Return of request to get article count. Result: {}", result);

		return result;
	}
}
