package com.pos.onboarding.persistance.impl.postgres;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pos.onboarding.beans.Category;


public class RunMybatis {
	private static final Logger log = LogManager.getLogger(RunMybatis.class);

	public static void main(String[] args) {
	  CategoryDAO CategoryDAO = new CategoryDAO();	
	  Category category = new Category();
	  //insert	
	  category.setId(1l);
	  category.setCategoryName("Food");
	  CategoryDAO.createCategory(category);
	  log.trace("---Data saved---");
	  //update
	  category = new Category();
	  category.setId(1l);
	  category.setCategoryName("Sports");
	  CategoryDAO.updateCategory(category);
	  log.trace("---Data updated---");
	  //select
	  category = CategoryDAO.getCategoryById(1l);
	  log.trace("id: {}, Name: {}", category.getId(), category.getCategoryName());
	  //delete
	  CategoryDAO.removeCategory(category);
	  log.trace("---Data deleted---");
	}
}
