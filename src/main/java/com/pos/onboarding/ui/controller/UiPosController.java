package com.pos.onboarding.ui.controller;

import java.beans.PropertyEditorSupport;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.pos.onboarding.beans.Category;
import com.pos.onboarding.ui.util.Link;
import com.pos.onboarding.ui.util.LinksUtil;
import com.pos.onboarding.util.CustomJsonParser;

/**
 * Handles and retrieves recipe data
 */
@Controller
@RequestMapping("/ui")
public class UiPosController {
	
	@Value("${webservice.url}")
	private String BASE_URL;
	
	@Value("${web.ui.pagesize}")
	private final String PAGE_SIZE="10"; 

	@Value("${ui.url.prefix}")
	private String UI_URL_PREFIX;
	
	RestTemplate restTemplate = new RestTemplate();
	
	protected static final Logger log = LogManager
			.getLogger(UiPosController.class);

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		
		binder.registerCustomEditor(Collection.class, "category.id", new PropertyEditorSupport() {
			@Override
			public void setAsText(String categoryId) throws IllegalArgumentException {
				if (categoryId != null && !categoryId.isEmpty()) {
					if (categoryId.trim().equalsIgnoreCase("NONE")) {
						setValue(null);
					} else {
						setValue(Integer.valueOf(categoryId));
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
		
		binder.registerCustomEditor(Collection.class, "category", new PropertyEditorSupport() {
			@Override
			public void setAsText(String categoryId) throws IllegalArgumentException {
				if (categoryId != null && !categoryId.isEmpty()) {
					if (categoryId.trim().equalsIgnoreCase("NONE")) {
						setValue(null);
					} else {
						setValue(Integer.valueOf(categoryId));
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
	 * Category UI
	 */
	/**
	 * Handles and retrieves all categories and show it in a JSP page
	 * 
	 * @return the name of the JSP page
	 */
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public String getCategoryList(@RequestParam(value="page", required=false, defaultValue="0") Integer page, 
   					              @RequestParam(value="size", required=false, defaultValue=PAGE_SIZE) Integer size, 
						          @RequestParam(value="filter", required=false, defaultValue="") String filter, 
						          @RequestParam(value="sort", required=false, defaultValue="") String sort, 
						          @RequestParam(value="order", required=false, defaultValue="") String order,
						          Model model) {
    	
    	log.debug("Received request to show all categories");
    	
    	// Retrieve all Categories by delegating the call to API
    	log.debug("Calling WS");
		String showPage = LinksUtil.getPageParams(page, size, filter, sort, order);
    	List<Category> categories = restTemplate.getForObject(BASE_URL + "/ws/categories" + showPage, List.class);
    	
    	log.debug("categories [" + categories.toString() + "]");

    	model.addAttribute("categories", categories);
    	
    	Long count = restTemplate.getForObject(BASE_URL + "/ws/category/count", Long.class);
    	List<Link> links = LinksUtil.getPageLinks(page, size,  filter, sort, order, count);
    	
    	model.addAttribute("links", links);
    	log.debug("links [" + links.toString() + "]");

    	return "categoryList";
	}
    

    /**
     * Retrieves the add page
     * 
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/category/add", method = RequestMethod.GET)
    public String getAddCategory(Model model) {
    	log.debug("Received request to show add page");
    
    	model.addAttribute("categoryAttribute", new Category());

    	return "categoryAdd";
	}

    
    /**
     * Adds a new category by delegating the processing to API call.
     * Displays a confirmation JSP page
     * 
     * @return  the name of the JSP page
     */
    @RequestMapping(value = "/category/add", method = RequestMethod.POST)
    public String addCategory(@ModelAttribute("categoryAttribute") Category category) {
		log.debug("Received request to add new category");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Accept", "application/json");
		
		String categoryString = "";
		categoryString = CustomJsonParser.ObjectToJsonString(category);
		HttpEntity<String> entity = new HttpEntity<String>(categoryString, headers);
    	log.debug("Calling WS with JSON [" + categoryString + "] of Category [" + category.toString() + "]");
    	
    	restTemplate.postForLocation(BASE_URL + "/ws/category", entity);
    	
		return "categoryAdded";
	}
    
    
    /**
     * Deletes an existing category by delegating the processing to API call.
     * Displays a confirmation JSP page
     * 
     * @return  the name of the JSP page
     */
    @RequestMapping(value = "/category/delete", method = RequestMethod.GET)
    public String deleteCategory(@RequestParam(value="id", required=true) Integer id, Model model) {
		log.debug("Received request to delete existing category");
		
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.set("Accept", "application/json");
    	HttpEntity entity = new HttpEntity(headers);
		
    	log.debug("Calling WS");
		restTemplate.exchange(BASE_URL + "/ws/category/" + id.toString(), HttpMethod.DELETE, entity, null);
    	
		return "categoryDeleted";
	}

    
    /**
     * Retrieves the edit page
     * 
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/category/edit", method = RequestMethod.GET)
    public String getEditCategory(@RequestParam(value="id", required=true) Integer id, Model model) {
    	log.debug("Received request to show edit page");
    
    	Category category = restTemplate.getForObject(BASE_URL + "/ws/category/" + id.toString(), Category.class);
    	model.addAttribute("categoryAttribute", category);
    	
    	return "categoryEdit";
	}
    
    
    /**
     * Edits an existing category by delegating the processing to PersonService.
     * Displays a confirmation JSP page
     * 
     * @return  the name of the JSP page
     */
    @RequestMapping(value = "/category/edit", method = RequestMethod.POST)
    public String saveEditCategory(@ModelAttribute("categoryAttribute") Category category, 
    										   @RequestParam(value="id", required=true) Long id, 
    												Model model) {
    	log.debug("Received request to update category");
    
    	category.setId(id);
    	
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Accept", "application/json");

		String categoryString = "";
		categoryString = CustomJsonParser.ObjectToJsonString(category);
		HttpEntity<String> entity = new HttpEntity<String>(categoryString, headers);
    	log.debug("Calling WS with JSON [" + categoryString + "] of Category [" + category.toString() + "]");
		
    	restTemplate.put(BASE_URL + "/ws/category/" + id.toString(), entity);
    	
    	model.addAttribute("categoryAttribute", category);
		model.addAttribute("id", id);
		
		return "categoryEdited";
	}
}
