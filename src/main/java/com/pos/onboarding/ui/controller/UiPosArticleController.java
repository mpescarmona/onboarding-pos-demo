package com.pos.onboarding.ui.controller;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.pos.onboarding.bean.Article;
import com.pos.onboarding.ui.util.Link;
import com.pos.onboarding.ui.util.LinksUtil;
import com.pos.onboarding.util.CustomJsonParser;

/**
 * Handles and retrieves article data
 */
@Controller
@RequestMapping("/article-ui")
public class UiPosArticleController {
	
	@Value("${webservice.url}")
	private String BASE_URL;
	
	@Value("${web.ui.pagesize}")
	private final String PAGE_SIZE="10"; 

	@Value("${ui.url.prefix}")
	private String UI_URL_PREFIX;
	
	RestTemplate restTemplate = new RestTemplate();
	
	protected static final Logger log = LogManager
			.getLogger(UiPosArticleController.class);

	/**
	 * Article UI
	 */
	/**
	 * Handles and retrieves all articles and show it in a JSP page
	 * 
	 * @return the name of the JSP page
	 */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getArticleList(@RequestParam(value="page", required=false, defaultValue="1") Integer page, 
   					              @RequestParam(value="size", required=false, defaultValue=PAGE_SIZE) Integer size, 
						          @RequestParam(value="filter", required=false, defaultValue="") String filter, 
						          @RequestParam(value="sort", required=false, defaultValue="") String sort, 
						          @RequestParam(value="order", required=false, defaultValue="") String order,
						          Model model) {
    	
    	log.debug("Received request to show all articles");
    	
    	// Retrieve all Categories by delegating the call to API
    	log.debug("Calling WS");
		String showPage = LinksUtil.getPageParams(page, size, filter, sort, order);
    	List<Article> articles = restTemplate.getForObject(BASE_URL + "/ws/article" + showPage, List.class);
    	
    	log.debug("articles [" + articles.toString() + "]");

    	model.addAttribute("articles", articles);
    	
    	Long count = restTemplate.getForObject(BASE_URL + "/ws/article/count", Long.class);
    	List<Link> links = LinksUtil.getPageLinks(page, size,  filter, sort, order, count);
    	
    	model.addAttribute("links", links);
    	log.debug("links [" + links.toString() + "]");

    	return "articleList";
	}
    

    /**
     * Retrieves the add page
     * 
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getAddArticle(Model model) {
    	log.debug("Received request to show add page");
    
    	model.addAttribute("articleAttribute", new Article());

    	return "articleAdd";
	}

    
    /**
     * Adds a new article by delegating the processing to API call.
     * Displays a confirmation JSP page
     * 
     * @return  the name of the JSP page
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addArticle(@Valid @ModelAttribute("articleAttribute") Article article, BindingResult result) {
		log.debug("Received request to add new article");
		
    	String resultString = "redirect:/ui/article-ui";
    	
    	if (result.hasErrors()) {
    		resultString = "articleAdd";
    	} else {
    		HttpHeaders headers = new HttpHeaders();
    		headers.setContentType(MediaType.APPLICATION_JSON);
    		headers.set("Accept", "application/json");
    		
    		String articleString = "";
    		articleString = CustomJsonParser.ObjectToJsonString(article);
    		HttpEntity<String> entity = new HttpEntity<String>(articleString, headers);
    		log.debug("Calling WS with JSON [" + articleString + "] of Article [" + article.toString() + "]");
    		
    		restTemplate.postForLocation(BASE_URL + "/ws/article", entity);
    	}
    	
		return resultString;
	}
    
    
    /**
     * Deletes an existing article by delegating the processing to API call.
     * Displays a confirmation JSP page
     * 
     * @return  the name of the JSP page
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteArticle(@RequestParam(value="id", required=true) Integer id, Model model) {
		log.debug("Received request to delete existing article");
		
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.set("Accept", "application/json");
    	HttpEntity entity = new HttpEntity(headers);
		
    	log.debug("Calling WS");
		restTemplate.exchange(BASE_URL + "/ws/article/" + id.toString(), HttpMethod.DELETE, entity, Boolean.class);
    	
		return "redirect:/ui/article-ui";
	}

    
    /**
     * Retrieves the edit page
     * 
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String getEditArticle(@RequestParam(value="id", required=true) Integer id, Model model) {
    	log.debug("Received request to show edit page");
    
    	Article article = restTemplate.getForObject(BASE_URL + "/ws/article/" + id.toString(), Article.class);
    	model.addAttribute("articleAttribute", article);
    	
    	return "articleEdit";
	}
    
    
    /**
     * Edits an existing article by delegating the processing to PersonService.
     * Displays a confirmation JSP page
     * 
     * @return  the name of the JSP page
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String saveEditArticle(@Valid @ModelAttribute("articleAttribute") Article article, BindingResult result, 
    										   @RequestParam(value="id", required=true) Long id, 
    												Model model) {
    	log.debug("Received request to update article");
    
    	String resultString = "redirect:/ui/article-ui";
    	
    	if (result.hasErrors()) {
    		resultString = "articleEdit";
    	} else {
    		article.setId(id);
    		HttpHeaders headers = new HttpHeaders();
    		headers.setContentType(MediaType.APPLICATION_JSON);
    		headers.set("Accept", "application/json");
    		
    		String articleString = "";
    		articleString = CustomJsonParser.ObjectToJsonString(article);
    		HttpEntity<String> entity = new HttpEntity<String>(articleString, headers);
    		log.debug("Calling WS with JSON [" + articleString + "] of Article [" + article.toString() + "]");
    		
    		restTemplate.put(BASE_URL + "/ws/article/" + id.toString(), entity);
    	}
    	
		return resultString;
	}    
}
