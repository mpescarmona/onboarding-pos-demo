package com.pos.onboarding.persistance.impl.postgres.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pos.onboarding.beans.Article;
import com.pos.onboarding.connection.impl.ibatis.MyBatisUtil;
import com.pos.onboarding.persistance.ArticleManager;
import com.pos.onboarding.persistance.impl.postgres.mapper.ArticleMapper;

public class ArticleDAO implements ArticleManager{
	private static final Logger log = LogManager.getLogger(ArticleDAO.class);
	
	@Override
	public Article createArticle(Article article) {
		log.trace("Enter method createArticle. Method params: {}", article);
		
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		ArticleMapper mapper = session.getMapper(ArticleMapper.class);
		
		Article result = mapper.selectArticle(article.getId());
		if (result != null) {
			log.error("The provided article already exists");
		} else {
			mapper.insertArticle(article);
		}
		
		Article newArticle = mapper.selectArticle(article.getId());
		session.commit();
		session.close();

		log.trace("Return method createArticle. Method params: {}.", article);
		return newArticle;
	}

	@Override
	public boolean updateArticle(Article article) {
		log.trace("Enter method updateArticle. Method params: {}", article);
		boolean result = false;
		
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		ArticleMapper mapper = session.getMapper(ArticleMapper.class);
		
		Article existingArticle = mapper.selectArticle(article.getId());
		if (existingArticle == null) {
			log.debug("The provided article does not exists. Adding to the database.");
			mapper.insertArticle(article);
		} else {
			log.debug("The provided article exists. Updating the changes to the database.");
			mapper.updateArticle(article);
			result = true;
		}
		
		session.commit();
		session.close();
		
		log.trace(
				"Return method updateArticle. Method params: {}. Result: {}",
				article, result);
		return result;
	}

	@Override
	public boolean removeArticle(Article article) {
		log.trace("Enter method removeArticle. Method params: {}", article);
		boolean result = false;
		
		Long articleId = article.getId();
		
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		ArticleMapper mapper = session.getMapper(ArticleMapper.class);
		
		Article existingArticle = mapper.selectArticle(articleId);
		if (existingArticle == null) {
			log.error(
					"The provided article does not exists. Method prams: {}. Result: {}",
					article, result);
		} else {
			mapper.deleteArticle(articleId);
			result = true;
			log.debug(
					"The provided article was successfully removed. Method prams: {}. Result: {}",
					article, result);
		}
		session.commit();
		session.close();

		log.trace(
				"Return method removeArticle. Method params: {}. Result: {}",
				article, result);
		return result;
	}

	@Override
	public Article getArticleById(Long articleId) {
		log.trace("Enter method getArticleById. Method params: {}", articleId);

		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		ArticleMapper mapper = session.getMapper(ArticleMapper.class);
		Article article = mapper.selectArticle(articleId);
		session.close();
		
		log.trace(
				"Return method getArticleById. Method params: {}. Result: {}",
				articleId, article);

		return article;
	}

	@Override
	public List<Article> getAllArticles() {
		log.trace("Enter method getAll.");

		List<Article> result = new ArrayList<Article>();
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		ArticleMapper mapper = session.getMapper(ArticleMapper.class);
		result = mapper.selectAllArticles();
		session.close();

		log.trace("Return method getAll. Result: {}", result);

		return result;
	}
}
