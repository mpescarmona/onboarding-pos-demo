package com.pos.onboarding.beans;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.pos.onboarding.bean.Article;

public class ArticleTest {

	private static final Long ARTICLE_ID = 1l;
	private static final BigDecimal ARTICLE_PRICE = new BigDecimal(150);
	private static final String ARTICLE_NAME = "Test Article";
	private static final String ARTICLE_DESCRIPTION = "Test Description";
	private static final Long ARTICLE_INVENTORY = 10l;

	@Test
	public void testArticle() {
		Article a = new Article();
		a.setId(ARTICLE_ID);
		a.setName(ARTICLE_NAME);
		a.setPrice(ARTICLE_PRICE);
		a.setDescription(ARTICLE_DESCRIPTION);
		a.setInventory(ARTICLE_INVENTORY);

		assertEquals(ARTICLE_ID, a.getId());
		assertEquals(ARTICLE_NAME, a.getName());
		assertEquals(ARTICLE_PRICE, a.getPrice());
		assertEquals(ARTICLE_DESCRIPTION, a.getDescription());
		assertEquals(ARTICLE_INVENTORY, a.getInventory());
	}
}
