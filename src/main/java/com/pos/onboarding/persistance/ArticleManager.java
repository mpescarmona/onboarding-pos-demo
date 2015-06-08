package com.pos.onboarding.persistance;

import java.util.List;

import com.pos.onboarding.beans.Article;

public interface ArticleManager {
	/**
	 * Gets a article by article Id
	 * 
	 * @param articleId
	 *            the article Id
	 * @return the article instance. Null when the article is not found
	 */
	public Article getArticleById(Long articleId);

	/**
	 * Creates a new instance of {@link Article} with the given object.
	 * 
	 * @param article
	 *            the article object to be created.
	 * @return the article created. This should by updated with the proper
	 *         article Id
	 */
	public Article createArticle(Article article);

	/**
	 * Updates the given {@link Article} instance
	 * 
	 * @param article
	 *            the article object to be updated.
	 * @return true when the article was properly created, otherwise, false.
	 */
	public boolean updateArticle(Article article);

	/**
	 * Removes the given {@link Article} instance
	 * 
	 * @param article
	 * @return true when the article was properly removed, otherwise, false.
	 */
	public boolean removeArticle(Article article);

	/**
	 * Retrieves all the Articles.
	 * 
	 * @param pageSize 
	 * @param pageNumber 
	 * @return the list of articles
	 */
	public List<Article> getAllArticles(int pageNumber, int pageSize);

	/**
	 * Retrieves the total number of articles 
	 * @return
	 */
	public Long getCount();
}
