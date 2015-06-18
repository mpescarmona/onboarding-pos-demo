package com.pos.onboarding.bl;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pos.onboarding.bean.Category;

public class CategoryBl {
	private static final Logger log = LogManager.getLogger(CategoryBl.class);
	private Category category;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Category createCategory(Category newCategory) {
		log.trace("Enter method createCategory. method params: {}",
				newCategory);

		if (validateCategory(newCategory)) {
			this.category = newCategory;
		}

		log.trace(
				"Return method createCategory. method params: {}. Result: {}",
				newCategory, category);

		return category;
	}

	public boolean validateCategory(Category category) {
		log.trace("Enter method validateCategory. method params: {}",
				category);
		boolean isValid = true;

		log.debug("validating category name.");
		if (StringUtils.isBlank(category.getCategoryName())) {
			log.error("The category name must be provided");
			isValid = false;
		}

		log.trace(
				"Return method validateCategory. method params: {}. Result: {}",
				category, isValid);

		return isValid;
	}

}
