package com.pos.onboarding.persistance.impl.postgres.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pos.onboarding.beans.Category;
import com.pos.onboarding.bl.CategoryBl;
import com.pos.onboarding.connection.impl.ibatis.MyBatisUtil;
import com.pos.onboarding.persistance.CategoryManager;
import com.pos.onboarding.persistance.impl.postgres.mapper.CategoryMapper;

@Service("categoryServicePostgres")
@Transactional
public class CategoryDAO implements CategoryManager {
	private static final Logger log = LogManager.getLogger(CategoryDAO.class);

	private SqlSessionFactory sessionFactory = MyBatisUtil
			.getSqlSessionFactory();

	@Override
	public Category createCategory(Category category) {
		log.trace("Enter method createCategory. Method params: {}", category);

		SqlSession session = sessionFactory.openSession();
		Category newCategory = null;

		try {
			CategoryMapper mapper = session.getMapper(CategoryMapper.class);
			Long nextId = mapper.selectNextId();
			nextId = nextId != null ? nextId : 1l;
			if (category.getId() == null) {
				category.setId(nextId);
			}
			Category result = mapper.selectCategory(category.getId());
			if (result != null) {
				log.error("The provided category already exists");
			} else {
				CategoryBl categoryBl = new CategoryBl();
				if (categoryBl.validateCategory(category)) {
					mapper.insertCategory(category);
					session.commit();
					newCategory = mapper.selectCategory(category.getId());
				}
			}

		} finally {
			session.close();
			log.trace("Return method createCategory. Method params: {}.",
					newCategory);
		}

		return newCategory;
	}

	@Override
	public boolean updateCategory(Category category) {
		log.trace("Enter method updateCategory. Method params: {}", category);
		boolean result = false;

		CategoryBl categoryBl = new CategoryBl();
		if (categoryBl.validateCategory(category)) {
			SqlSession session = sessionFactory.openSession();
			try {
				CategoryMapper mapper = session.getMapper(CategoryMapper.class);

				Category existingCategory = mapper.selectCategory(category
						.getId());
				if (existingCategory == null) {
					log.debug("The provided category does not exists. Adding to the database.");
					mapper.insertCategory(category);
				} else {
					log.debug("The provided category exists. Updating the changes to the database.");
					mapper.updateCategory(category);
					result = true;
				}

				session.commit();
			} finally {
				session.close();
				log.trace(
						"Return method updateCategory. Method params: {}. Result: {}",
						category, result);
			}
		} else {
			log.error("The provided category is invalid. Please verify and try again later");
		}

		return result;
	}

	@Override
	public boolean removeCategory(Long categoryId) {
		log.trace("Enter method removeCategory. Method params: {}", categoryId);
		boolean result = false;

		SqlSession session = sessionFactory.openSession();
		try {
			CategoryMapper mapper = session.getMapper(CategoryMapper.class);

			Category existingCategory = mapper.selectCategory(categoryId);
			if (existingCategory == null) {
				log.error(
						"The provided category does not exists. Method prams: {}. Result: {}",
						categoryId, result);
			} else {
				mapper.deleteCategory(categoryId);
				result = true;
				log.debug(
						"The provided category was successfully removed. Method prams: {}. Result: {}",
						categoryId, result);
			}
			session.commit();
		} finally {
			session.close();
			log.trace(
					"Return method removeCategory. Method params: {}. Result: {}",
					categoryId, result);
		}
		return result;
	}

	@Override
	public Category getCategoryById(Long categoryId) {
		log.trace("Enter method getCategoryById. Method params: {}", categoryId);

		SqlSession session = sessionFactory.openSession();
		Category category = null;
		try {
			CategoryMapper mapper = session.getMapper(CategoryMapper.class);
			category = mapper.selectCategory(categoryId);
		} finally {
			session.close();
			log.trace(
					"Return method getCategoryById. Method params: {}. Result: {}",
					categoryId, category);
		}

		return category;
	}

	@Override
	public List<Category> getAllCategories(int pageNumber, int pageSize) {
		log.trace("Enter method getAll.");

		SqlSession session = sessionFactory.openSession();
		List<Category> result = new ArrayList<Category>();
		try {
			CategoryMapper mapper = session.getMapper(CategoryMapper.class);

			int offset = ((pageNumber - 1) * pageSize);
			offset = offset < 0 ? 0 : offset;
			RowBounds rowBounds = new RowBounds(offset, pageSize);

			result = mapper.selectAllCategories(rowBounds);
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
			CategoryMapper mapper = session.getMapper(CategoryMapper.class);
			result = mapper.selectCount();
		} finally {
			session.close();
			log.trace("Return method getCount. Result: {}", result);
		}

		return result;
	}
}
