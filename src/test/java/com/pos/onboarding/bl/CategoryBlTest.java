package com.pos.onboarding.bl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.pos.onboarding.bean.Category;

public class CategoryBlTest {

	private static final Long CATEGORY_ID = 1l;
	private static final String CATEGORY_NAME = "Test Category";

	@Test
	public void testCategory() {
		Category validVategory = getValidCategory();
		CategoryBl categoryBl = new CategoryBl();

		assertTrue(categoryBl.validateCategory(validVategory));
		categoryBl.createCategory(validVategory);
		assertEquals(validVategory, categoryBl.getCategory());
	}

	private static Category getValidCategory() {
		Category category = new Category();
		category.setId(CATEGORY_ID);
		category.setCategoryName(CATEGORY_NAME);
		return category;
	}
}
