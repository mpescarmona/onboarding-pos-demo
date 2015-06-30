package com.pos.onboarding.persistance.impl.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.pos.onboarding.bean.Category;
import com.pos.onboarding.persistence.CategoryManager;
import com.pos.onboarding.persistence.impl.csv.CSVCategoryManager;

public class CSVCategoryManagerTest {
	private static final Long ARTICLE_ID_1 = 1l;
	private static final String ARTICLE_NAME_1 = "Food";
	private static final Long ARTICLE_ID_2 = 2l;
	private static final String ARTICLE_NAME_2 = "Sports";
	private static final Long ARTICLE_ID_3 = 3l;
	private static final String ARTICLE_NAME_3 = "Technology";
	private static final String UPDATED_ARTICLE_NAME = "Cool Technology";
	private static final Long ARTICLE_ID_NEW = 99l;
	private static final String ARTICLE_NAME_NEW = "New Category";

	private CategoryManager categoryManager = new CSVCategoryManager();

	@Before
	public void init() {
		List<Category> categories = categoryManager.getAllCategories(1, 100);
		for (Category category : categories) {
			categoryManager.removeCategory(category.getId());
		}

		Category category1 = new Category();
		category1.setId(ARTICLE_ID_1);
		category1.setCategoryName(ARTICLE_NAME_1);
		categoryManager.createCategory(category1);

		Category category2 = new Category();
		category2.setId(ARTICLE_ID_2);
		category2.setCategoryName(ARTICLE_NAME_2);
		categoryManager.createCategory(category2);

		Category category3 = new Category();
		category3.setId(ARTICLE_ID_3);
		category3.setCategoryName(ARTICLE_NAME_3);
		categoryManager.createCategory(category3);
	}

	@Test
	public void testCreateCategory() {
		init();
		Category newCategory = categoryManager.getCategoryById(ARTICLE_ID_NEW);
		assertNull(newCategory);

		Category category = new Category();
		category.setId(ARTICLE_ID_NEW);
		category.setCategoryName(ARTICLE_NAME_NEW);
		categoryManager.createCategory(category);

		Category createdCategory = categoryManager
				.getCategoryById(ARTICLE_ID_NEW);
		assertNotNull(createdCategory);

	}

	@Test
	public void testGetCategoryById() {

		Category retrievedCategory = categoryManager
				.getCategoryById(ARTICLE_ID_1);
		assertNotNull(retrievedCategory);

		assertEquals(ARTICLE_ID_1, retrievedCategory.getId());
		assertEquals(ARTICLE_NAME_1, retrievedCategory.getCategoryName());
	}

	@Test
	public void testUpdateCategory() {

		Category category = new Category();
		category.setId(ARTICLE_ID_3);
		category.setCategoryName(UPDATED_ARTICLE_NAME);

		assertTrue(categoryManager.updateCategory(category));

		Category retrievedCategory = categoryManager
				.getCategoryById(ARTICLE_ID_3);
		assertNotNull(retrievedCategory);

		assertEquals(ARTICLE_ID_3, retrievedCategory.getId());
		assertEquals(UPDATED_ARTICLE_NAME, retrievedCategory.getCategoryName());
	}

	@Test
	public void testRemoveCategory() {
		Category retrievedCategory = categoryManager
				.getCategoryById(ARTICLE_ID_3);
		assertNotNull(retrievedCategory);

		assertTrue(categoryManager.removeCategory(retrievedCategory.getId()));

		Category removedCategory = categoryManager
				.getCategoryById(ARTICLE_ID_3);
		assertNull(removedCategory);
	}
}
