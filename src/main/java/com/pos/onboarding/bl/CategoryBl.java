package com.pos.onboarding.bl;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pos.onboarding.beans.Category;

public class CategoryBl {
	private static final Logger logger = LogManager.getLogger(CategoryBl.class);
	private Category category;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Category createCategory(Category newCategory) {
		logger.trace("Enter method createCategory. method params: {}",
				newCategory);

		if (validateCategory(newCategory)) {
			this.category = newCategory;
		}

		logger.trace(
				"Return method createCategory. method params: {}. Result: {}",
				newCategory, category);

		return category;
	}

	public boolean validateCategory(Category category) {
		logger.trace("Enter method validateCategory. method params: {}",
				category);
		boolean isValid = true;

		logger.debug("validating category name.");
		if (StringUtils.isBlank(category.getCategoryName())) {
			logger.error("The category name must be provided");
			isValid = false;
		}

		logger.trace(
				"Return method validateCategory. method params: {}. Result: {}",
				category, isValid);

		return isValid;
	}

}
