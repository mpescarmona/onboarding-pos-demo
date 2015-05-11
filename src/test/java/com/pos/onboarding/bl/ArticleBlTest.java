package com.pos.onboarding.bl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import com.pos.onboarding.beans.Article;

public class ArticleBlTest {

	private static final Long ARTICLE_ID = 1l;
	private static final BigDecimal ARTICLE_PRICE = new BigDecimal(150);
	private static final Long ARTICLE_CATEGORY_ID = 1l;
	private static final String ARTICLE_NAME = "Test Article";
	private static final String ARTICLE_DESCRIPTION = "Test Description";
	private static final Long ARTICLE_INVENTORY = 10l;

	@Test
	public void testArticle() {
		Article validArticle = getValidArticle();
		ArticleBl articleBl = new ArticleBl();

		assertTrue(articleBl.validateArticle(validArticle));
		articleBl.createArticle(validArticle);
		assertEquals(validArticle, articleBl.getArticle());
	}

	private static Article getValidArticle() {
		Article article = new Article();
		article.setId(ARTICLE_ID);
		article.setCategoryId(ARTICLE_CATEGORY_ID);
		article.setName(ARTICLE_NAME);
		article.setPrice(ARTICLE_PRICE);
		article.setDescription(ARTICLE_DESCRIPTION);
		article.setInventory(ARTICLE_INVENTORY);

		return article;
	}
}
