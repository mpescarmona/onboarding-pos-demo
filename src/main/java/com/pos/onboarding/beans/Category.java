package com.pos.onboarding.beans;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * This class is used to hold the categories of articles.-
 * 
 * @author Mario Pescarmona
 *
 */
public class Category {
	private Long id;
	private String categoryName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
