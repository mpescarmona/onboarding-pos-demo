package com.pos.onboarding.bl;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pos.onboarding.beans.Article;

public class ArticleBl {
	private static final Logger logger = LogManager.getLogger(ArticleBl.class);
	private Article article;

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Article createArticle(Article newArticle) {
		logger.trace("Enter method createArticle. method params: {}",
				newArticle);

		if (validateArticle(newArticle)) {
			this.article = newArticle;
		}

		logger.trace(
				"Return method createArticle. method params: {}. Result: {}",
				newArticle, article);

		return article;
	}

	public boolean validateArticle(Article article) {
		logger.trace("Enter method validateArticle. method params: {}", article);
		boolean isValid = true;

		logger.debug("validating article name.");
		if (StringUtils.isBlank(article.getName())) {
			logger.error("The article name must be provided");
			isValid = false;
		}

		logger.debug("validating article description.");
		if (StringUtils.isBlank(article.getDescription())) {

			logger.error("The article description must be provided");
			isValid = false;
		}

		logger.debug("validating article price.");
		if (article.getPrice() == null
				|| article.getPrice().compareTo(new BigDecimal(0)) <= 0) {

			logger.error("The price of article must be positive");
			isValid = false;
		}

		logger.debug("validating article inventory.");
		if (article.getInventory() == null
				|| article.getInventory().longValue() < 0l) {

			logger.error("The inventory of article must be positive");
			isValid = false;
		}

		logger.trace(
				"Return method validateArticle. method params: {}. Result: {}",
				article, isValid);

		return isValid;
	}

}
