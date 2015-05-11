package com.pos.onboarding.bl;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pos.onboarding.beans.Article;

public class ArticleBl {
	private static final Logger log = LogManager.getLogger(ArticleBl.class);
	private Article article;

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Article createArticle(Article newArticle) {
		log.trace("Enter method createArticle. method params: {}", newArticle);

		if (validateArticle(newArticle)) {
			this.article = newArticle;
		}

		log.trace("Return method createArticle. method params: {}. Result: {}",
				newArticle, article);

		return article;
	}

	public boolean validateArticle(Article article) {
		log.trace("Enter method validateArticle. method params: {}", article);
		boolean isValid = true;

		log.debug("validating article category.");
		if (article.getCategoryId() == null) {
			log.error("The article's category must be provided");
			isValid = false;
		} else {
			if (article.getCategoryId() < 0l) {
				log.error("The article's category must be positive");
				isValid = false;
			}
		}

		log.debug("validating article name.");
		if (StringUtils.isBlank(article.getName())) {
			log.error("The article name must be provided");
			isValid = false;
		}

		log.debug("validating article description.");
		if (StringUtils.isBlank(article.getDescription())) {

			log.error("The article description must be provided");
			isValid = false;
		}

		log.debug("validating article price.");
		if (article.getPrice() == null) {
			log.error("The article's price must be provided");
			isValid = false;
		} else {
			if (article.getPrice().compareTo(new BigDecimal(0)) <= 0) {

				log.error("The price of article must be positive");
				isValid = false;
			}
		}

		log.debug("validating article inventory.");
		if (article.getInventory() == null) {
			log.error("The article's inventory must be provided");
			isValid = false;
		} else {
			if (article.getInventory() < 0l) {
				log.error("The article's inventory must be positive");
				isValid = false;
			}
		}

		log.trace(
				"Return method validateArticle. method params: {}. Result: {}",
				article, isValid);

		return isValid;
	}

}
