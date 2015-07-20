package com.pos.onboarding.bean;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

/**
 * This class is used to hold the article data.-
 * 
 * @author Mario Pescarmona
 *
 */
public class Article {
	private Long id;
	@NotBlank(message="Category is mandatory")
	private Long categoryId;
	@NotBlank(message="Article Name is mandatory")
	private String name;
	@NotBlank(message="Article Description is mandatory")
	private String description;
	@NotBlank(message="Price is mandatory")
	private BigDecimal price;
	@NotBlank(message="Inventory is mandatory")
	private Long inventory;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Long getInventory() {
		return inventory;
	}

	public void setInventory(Long inventory) {
		this.inventory = inventory;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
