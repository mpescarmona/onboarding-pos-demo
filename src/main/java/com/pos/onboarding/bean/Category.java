package com.pos.onboarding.bean;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

/**
 * This class is used to hold the categories of articles.-
 * 
 * @author Mario Pescarmona
 *
 */
public class Category {
	private Long id;
	@NotBlank(message="Category Name is mandatory")
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
