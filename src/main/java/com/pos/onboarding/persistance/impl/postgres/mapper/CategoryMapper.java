package com.pos.onboarding.persistance.impl.postgres.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.pos.onboarding.beans.Category;

public interface CategoryMapper {
	static final String SELECT_BY_ID = "SELECT id, categoryname from category WHERE id = #{id}";
	static final String INSERT = "INSERT into category(id, categoryname) VALUES(#{id}, #{categoryName})";
	static final String UPDATE = "UPDATE category SET categoryname=#{categoryName} WHERE id =#{id}";
	static final String DELETE = "DELETE FROM category WHERE id =#{categoryId}";
	static final String SELECT_ALL = "SELECT id, categoryname from category";
	static final String SELECT_NEXT_ID = "SELECT max(id) + 1 from category";

	@Results(
			{
				@Result(id=true, property = "id", column = "Id"),
				@Result(property = "categoryName", column = "CategoryName")
	})
	@Select(SELECT_BY_ID)
	Category selectCategory(Long id);

	@Insert(INSERT)
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insertCategory(Category category);

	@Update(UPDATE)
	void updateCategory(Category category);

	@Delete(DELETE)
	void deleteCategory(Long id);
	
	@Select(SELECT_ALL)
	List<Category> selectAllCategories();
	
	@Select(SELECT_NEXT_ID)
	Long selectNextId();
}
