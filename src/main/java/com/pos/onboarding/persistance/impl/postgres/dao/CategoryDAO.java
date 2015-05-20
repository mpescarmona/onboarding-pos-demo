package com.pos.onboarding.persistance.impl.postgres.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pos.onboarding.beans.Category;
import com.pos.onboarding.connection.impl.ibatis.MyBatisUtil;
import com.pos.onboarding.persistance.CategoryManager;
import com.pos.onboarding.persistance.impl.postgres.mapper.CategoryMapper;

public class CategoryDAO implements CategoryManager{
	private static final Logger log = LogManager.getLogger(CategoryDAO.class);
	
	@Override
	public Category createCategory(Category category) {
		log.trace("Enter method createCategory. Method params: {}", category);
		
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		CategoryMapper mapper = session.getMapper(CategoryMapper.class);
		
		Category result = mapper.selectCategory(category.getId());
		if (result != null) {
			log.error("The provided category already exists");
		} else {
			mapper.insertCategory(category);
		}
		
		Category newCategory = mapper.selectCategory(category.getId());
		session.commit();
		session.close();

		log.trace("Return method createCategory. Method params: {}.", category);
		return newCategory;
	}

	@Override
	public boolean updateCategory(Category category) {
		log.trace("Enter method updateCategory. Method params: {}", category);
		boolean result = false;
		
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		CategoryMapper mapper = session.getMapper(CategoryMapper.class);
		
		Category existingCategory = mapper.selectCategory(category.getId());
		if (existingCategory == null) {
			log.debug("The provided category does not exists. Adding to the database.");
			mapper.insertCategory(category);
		} else {
			log.debug("The provided category exists. Updating the changes to the database.");
			mapper.updateCategory(category);
			result = true;
		}
		
		session.commit();
		session.close();
		
		log.trace(
				"Return method updateCategory. Method params: {}. Result: {}",
				category, result);
		return result;
	}

	@Override
	public boolean removeCategory(Category category) {
		log.trace("Enter method removeCategory. Method params: {}", category);
		boolean result = false;
		
		Long categoryId = category.getId();
		
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		CategoryMapper mapper = session.getMapper(CategoryMapper.class);
		
		Category existingCategory = mapper.selectCategory(categoryId);
		if (existingCategory == null) {
			log.error(
					"The provided category does not exists. Method prams: {}. Result: {}",
					category, result);
		} else {
			mapper.deleteCategory(categoryId);
			result = true;
			log.debug(
					"The provided category was successfully removed. Method prams: {}. Result: {}",
					category, result);
		}
		session.commit();
		session.close();

		log.trace(
				"Return method removeCategory. Method params: {}. Result: {}",
				category, result);
		return result;
	}

	@Override
	public Category getCategoryById(Long categoryId) {
		log.trace("Enter method getCategoryById. Method params: {}", categoryId);

		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		CategoryMapper mapper = session.getMapper(CategoryMapper.class);
		Category category = mapper.selectCategory(categoryId);
		session.close();
		
		log.trace(
				"Return method getCategoryById. Method params: {}. Result: {}",
				categoryId, category);

		return category;
	}

	@Override
	public List<Category> getAllCategories() {
		log.trace("Enter method getAll.");

		List<Category> result = new ArrayList<Category>();
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		CategoryMapper mapper = session.getMapper(CategoryMapper.class);
		result = mapper.selectAllCategories();
		session.close();

		log.trace("Return method getAll. Result: {}", result);

		return result;
	}
}
