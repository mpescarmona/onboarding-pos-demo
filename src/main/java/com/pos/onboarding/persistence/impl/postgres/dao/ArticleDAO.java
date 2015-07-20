package com.pos.onboarding.persistence.impl.postgres.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pos.onboarding.bean.Article;
import com.pos.onboarding.bl.ArticleBl;
import com.pos.onboarding.connection.impl.ibatis.MyBatisUtil;
import com.pos.onboarding.persistence.ArticleManager;
import com.pos.onboarding.persistence.impl.postgres.mapper.ArticleMapper;

@Service("articleServicePostgres")
@Transactional
public class ArticleDAO implements ArticleManager{
	private static final Logger log = LogManager.getLogger(ArticleDAO.class);
	
	private SqlSessionFactory sessionFactory = MyBatisUtil
			.getSqlSessionFactory();

	@Override
	public Article createArticle(Article article) {
		log.trace("Enter method createArticle. Method params: {}", article);
		
		SqlSession session = sessionFactory.openSession();
		Article newArticle;
		try {
			ArticleMapper mapper = session.getMapper(ArticleMapper.class);
			Long nextId = mapper.selectNextId();
			nextId = nextId != null ? nextId : 1l;
			if (article.getId() == null) {
				article.setId(nextId);
			}
			Article result = mapper.selectArticle(article.getId());
			if (result != null) {
				log.error("The provided article already exists");
			} else {
				ArticleBl articleBl = new ArticleBl();
				if (articleBl.validateArticle(article)) {
					mapper.insertArticle(article);
					session.commit();
				}
			}
			
			newArticle = mapper.selectArticle(article.getId());
			session.commit();
		} finally {
			session.close();
			log.trace("Return method createArticle. Method params: {}.", article);
		}
		
		return newArticle;
	}

	@Override
	public boolean updateArticle(Article article) {
		log.trace("Enter method updateArticle. Method params: {}", article);
		boolean result = false;
		
		ArticleBl articleBl = new ArticleBl();
		if (articleBl.validateArticle(article)) {
			SqlSession session = sessionFactory.openSession();
			try {
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
			} finally {
				session.close();
				log.trace(
						"Return method updateArticle. Method params: {}. Result: {}",
						article, result);
			}
		} else {
			log.error("The provided article is invalid. Please verify and try again later");
		}
		
		return result;
	}

	@Override
	public boolean removeArticle(Long articleId) {
		log.trace("Enter method removeArticle. Method params: {}", articleId);
		boolean result = false;
		
		SqlSession session = sessionFactory.openSession();
		try {
			ArticleMapper mapper = session.getMapper(ArticleMapper.class);
			
			Article existingArticle = mapper.selectArticle(articleId);
			if (existingArticle == null) {
				log.error(
						"The provided article does not exists. Method prams: {}. Result: {}",
						articleId, result);
			} else {
				mapper.deleteArticle(articleId);
				result = true;
				log.debug(
						"The provided article was successfully removed. Method prams: {}. Result: {}",
						articleId, result);
			}
			session.commit();
		} finally {
			session.close();
			log.trace(
					"Return method removeArticle. Method params: {}. Result: {}",
					articleId, result);
		}
		
		return result;
	}

	@Override
	public Article getArticleById(Long articleId) {
		log.trace("Enter method getArticleById. Method params: {}", articleId);

		SqlSession session = sessionFactory.openSession();
		Article article = null;
		try {
			ArticleMapper mapper = session.getMapper(ArticleMapper.class);
			article = mapper.selectArticle(articleId);
		} finally {
			session.close();
			log.trace(
					"Return method getArticleById. Method params: {}. Result: {}",
					articleId, article);
		}

		return article;
	}

	@Override
	public List<Article> getAllArticles(int pageNumber, int pageSize) {
		log.trace("Enter method getAll.");

		List<Article> result = new ArrayList<Article>();
		SqlSession session = sessionFactory.openSession();
		try {
			ArticleMapper mapper = session.getMapper(ArticleMapper.class);

			int offset = ((pageNumber - 1) * pageSize);
			offset = offset < 0 ? 0 : offset;
			RowBounds rowBounds = new RowBounds(offset, pageSize);

			result = mapper.selectAllArticles(rowBounds);
		} finally {
			session.close();
			log.trace("Return method getAll. Result: {}", result);
		}

		return result;
	}

	@Override
	public Long getCount() {
		log.trace("Enter method getCount.");
		
		Long result = 0l;
		SqlSession session = sessionFactory.openSession();
		try {
			ArticleMapper mapper = session.getMapper(ArticleMapper.class);
			result = mapper.selectCount();
		} finally {
			session.close();
			log.trace("Return method getCount. Result: {}", result);
		}

		return result;
	}
}
