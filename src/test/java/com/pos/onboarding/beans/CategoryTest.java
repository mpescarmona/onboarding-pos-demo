package com.pos.onboarding.beans;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.pos.onboarding.beans.Category;

public class CategoryTest {

	private static final Long CATEGORY_ID = 1l;
	private static final String CATEGORY_NAME = "Test Category";

	@Test
	public void testArticle() {
		Category a = new Category();
		a.setId(CATEGORY_ID);
		a.setCategoryName(CATEGORY_NAME);

		assertEquals(CATEGORY_ID, a.getId());
		assertEquals(CATEGORY_NAME, a.getCategoryName());
	}
}
