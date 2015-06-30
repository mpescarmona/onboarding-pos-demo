package com.pos.onboarding.persistence;

import java.util.List;

import com.pos.onboarding.bean.Category;

public interface CategoryManager {
	/**
	 * Gets a category by category Id
	 * 
	 * @param categoryId
	 *            the category Id
	 * @return the category instance. Null when the category is not found
	 */
	public Category getCategoryById(Long categoryId);

	/**
	 * Creates a new instance of {@link Category} with the given object.
	 * 
	 * @param category
	 *            the category object to be created.
	 * @return the category created. This should by updated with the proper
	 *         category Id
	 */
	public Category createCategory(Category category);

	/**
	 * Updates the given {@link Category} instance
	 * 
	 * @param category
	 *            the category object to be updated.
	 * @return true when the category was properly created, otherwise, false.
	 */
	public boolean updateCategory(Category category);

	/**
	 * Removes the given {@l Category} instance
	 * 
	 * @param categoryId
	 * @return true when the category was properly removed, otherwise, false.
	 */
	public boolean removeCategory(Long categoryId);

	/**
	 * Retrieves all the Categories.
	 * 
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	List<Category> getAllCategories(int pageNumber, int pageSize);

	/**
	 * Retrieves the total number of categories
	 * 
	 * @return
	 */
	public Long getCount();
}
