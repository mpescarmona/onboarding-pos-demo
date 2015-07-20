package com.pos.onboarding.persistance.impl.postgres.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.pos.onboarding.bean.Article;
import com.pos.onboarding.bean.Category;
import com.pos.onboarding.persistence.ArticleManager;
import com.pos.onboarding.persistence.CategoryManager;
import com.pos.onboarding.persistence.impl.postgres.dao.ArticleDAO;
import com.pos.onboarding.persistence.impl.postgres.dao.CategoryDAO;

public class ArticleDAOTest {
	private static final Long CATEGORY_ID = 999l;
	private static final String CATEGORY_NAME = "Test Category";
	
	private static final Long ARTICLE_ID_1 = 1l;
	private static final String ARTICLE_NAME_1 = "Spaghetti";
	private static final String ARTICLE_DESCRIPTION_1 = "The best spaghetti of USA";
	private static final BigDecimal ARTICLE_PRICE_1 = new BigDecimal("150.00");
	private static final Long ARTICLE_INVENTORY_1 = 10l;
	
	private static final Long ARTICLE_ID_2 = 2l;
	private static final String ARTICLE_NAME_2 = "Ravioli";
	private static final String ARTICLE_DESCRIPTION_2 = "The best ravioli of USA";
	private static final BigDecimal ARTICLE_PRICE_2 = new BigDecimal("234.00");
	private static final Long ARTICLE_INVENTORY_2 = 15l;
	
	private static final Long ARTICLE_ID_3 = 3l;
	private static final String ARTICLE_NAME_3 = "Pizza";
	private static final String ARTICLE_DESCRIPTION_3 = "The best pizza of USA";
	private static final BigDecimal ARTICLE_PRICE_3 = new BigDecimal("456.00");
	private static final Long ARTICLE_INVENTORY_3 = 15l;
	
	private static final String UPDATED_ARTICLE_NAME = "Pizza Hut";
	private static final Long ARTICLE_ID_NEW = 99l;
	private static final String ARTICLE_NAME_NEW = "Turkey";
	private static final Long ARTICLE_INVENTORY_NEW = 1l;

	private ArticleManager articleManager = new ArticleDAO();
	private CategoryManager categoryManager = new CategoryDAO();

	@Before
	public void init() {
		tearDown();
		
		Category testCategory = new Category();
		testCategory.setId(CATEGORY_ID);
		testCategory.setCategoryName(CATEGORY_NAME);
		categoryManager.createCategory(testCategory);

		Article article1 = new Article();
		article1.setId(ARTICLE_ID_1);
		article1.setCategoryId(CATEGORY_ID);
		article1.setName(ARTICLE_NAME_1);
		article1.setDescription(ARTICLE_DESCRIPTION_1);
		article1.setPrice(ARTICLE_PRICE_1);
		article1.setInventory(ARTICLE_INVENTORY_1);
		articleManager.createArticle(article1);

		Article article2 = new Article();
		article2.setId(ARTICLE_ID_2);
		article2.setCategoryId(CATEGORY_ID);
		article2.setName(ARTICLE_NAME_2);
		article2.setDescription(ARTICLE_DESCRIPTION_2);
		article2.setPrice(ARTICLE_PRICE_2);
		article2.setInventory(ARTICLE_INVENTORY_2);
		articleManager.createArticle(article2);

		Article article3 = new Article();
		article3.setId(ARTICLE_ID_3);
		article3.setCategoryId(CATEGORY_ID);
		article3.setName(ARTICLE_NAME_3);
		article3.setDescription(ARTICLE_DESCRIPTION_3);
		article3.setPrice(ARTICLE_PRICE_3);
		article3.setInventory(ARTICLE_INVENTORY_3);
		articleManager.createArticle(article3);
	}

	@After
	public void tearDown() {
		List<Article> articles = articleManager.getAllArticles(1, 100);
		for (Article article : articles) {
			articleManager.removeArticle(article.getId());
		}
		
		categoryManager.removeCategory(CATEGORY_ID);
	}
	
	@Test
	public void testCreateArticle() {
		init();
		Article newArticle = articleManager.getArticleById(ARTICLE_ID_NEW);
		assertNull(newArticle);

		Article article = new Article();
		article.setId(ARTICLE_ID_NEW);
		article.setName(ARTICLE_NAME_NEW);
		article.setCategoryId(CATEGORY_ID);
		article.setInventory(ARTICLE_INVENTORY_NEW);
		articleManager.createArticle(article);

		Article createdArticle = articleManager
				.getArticleById(ARTICLE_ID_NEW);
		assertNotNull(createdArticle);
	}

	@Test
	public void testGetArticleById() {

		Article retrievedArticle = articleManager
				.getArticleById(ARTICLE_ID_1);
		assertNotNull(retrievedArticle);

		assertEquals(ARTICLE_ID_1, retrievedArticle.getId());
		assertEquals(CATEGORY_ID, retrievedArticle.getCategoryId());
		assertEquals(ARTICLE_NAME_1, retrievedArticle.getName());
		assertEquals(ARTICLE_DESCRIPTION_1, retrievedArticle.getDescription());
		assertEquals(ARTICLE_PRICE_1, retrievedArticle.getPrice());
		assertEquals(ARTICLE_INVENTORY_1, retrievedArticle.getInventory());
	}

	@Test
	public void testUpdateArticle() {

		Article article = new Article();
		article.setId(ARTICLE_ID_3);
		article.setName(UPDATED_ARTICLE_NAME);
		article.setInventory(ARTICLE_INVENTORY_NEW);
		article.setCategoryId(CATEGORY_ID);

		assertTrue(articleManager.updateArticle(article));

		Article retrievedArticle = articleManager
				.getArticleById(ARTICLE_ID_3);
		assertNotNull(retrievedArticle);

		assertEquals(ARTICLE_ID_3, retrievedArticle.getId());
		assertEquals(UPDATED_ARTICLE_NAME, retrievedArticle.getName());
		assertEquals(CATEGORY_ID, retrievedArticle.getCategoryId());
	}

	@Test
	public void testRemoveArticle() {
		Article retrievedArticle = articleManager
				.getArticleById(ARTICLE_ID_3);
		assertNotNull(retrievedArticle);

		assertTrue(articleManager.removeArticle(retrievedArticle.getId()));

		Article removedArticle = articleManager
				.getArticleById(ARTICLE_ID_3);
		assertNull(removedArticle);
	}
}
