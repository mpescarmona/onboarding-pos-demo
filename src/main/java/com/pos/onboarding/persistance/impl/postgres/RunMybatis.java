package com.pos.onboarding.persistance.impl.postgres;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pos.onboarding.bean.Article;
import com.pos.onboarding.bean.Category;
import com.pos.onboarding.persistance.impl.postgres.dao.ArticleDAO;
import com.pos.onboarding.persistance.impl.postgres.dao.CategoryDAO;


public class RunMybatis {
	private static final Logger log = LogManager.getLogger(RunMybatis.class);

	public static void main(String[] args) {
	  CategoryDAO categoryDAO = new CategoryDAO();	
	  Category category = new Category();
	  //insert	
	  category.setId(1l);
	  category.setCategoryName("Food");
	  categoryDAO.createCategory(category);
	  log.trace("---Data saved---");
	  //update
	  category = new Category();
	  category.setId(1l);
	  category.setCategoryName("Sports");
	  categoryDAO.updateCategory(category);
	  log.trace("---Data updated---");
	  //select
	  category = categoryDAO.getCategoryById(1l);
	  log.trace("id: {}, Name: {}", category.getId(), category.getCategoryName());
	  //delete
	  categoryDAO.removeCategory(1l);
	  log.trace("---Data deleted---");

	  
	  ArticleDAO articleDAO = new ArticleDAO();
	  categoryDAO = new CategoryDAO();	
	  category = new Category();
	  //insert	
	  category.setId(1l);
	  category.setCategoryName("Food");
	  categoryDAO.createCategory(category);
	  
	  
	  //insert	
	  Article article = new Article();
	  article.setId(1l);
	  article.setCategoryId(1l);
	  article.setName("Skate board");
	  article.setDescription("Awesome skateboard, jet powered");
	  article.setPrice(new BigDecimal(150));
	  article.setInventory(10l);
	  articleDAO.createArticle(article);
	  log.trace("---Data saved---");
	  //update
	  article = new Article();
	  article.setId(1l);
	  article.setCategoryId(1l);
	  article.setName("Skate board, UPDATED");
	  article.setDescription("Awesome skateboard, jet powered");
	  article.setPrice(new BigDecimal(150));
	  article.setInventory(10l);
	  articleDAO.updateArticle(article);
	  log.trace("---Data updated---");
	  //select
	  article = articleDAO.getArticleById(1l);
	  log.trace("id: {}, Name: {}", article.getId(), article.getName());
	  //delete
//	  articleDAO.removeArticle(article);
	  log.trace("---Data deleted---");
	  
	}
}
