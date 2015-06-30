package com.pos.onboarding.persistence.impl.postgres.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.session.RowBounds;

import com.pos.onboarding.bean.Category;

public interface CategoryMapper {
	static final String SELECT_BY_ID = "SELECT id, categoryname from category WHERE id = #{id}";
	static final String INSERT = "INSERT into category(id, categoryname) VALUES(#{id}, #{categoryName})";
	static final String UPDATE = "UPDATE category SET categoryname=#{categoryName} WHERE id =#{id}";
	static final String DELETE = "DELETE FROM category WHERE id =#{categoryId}";
	static final String SELECT_ALL = "SELECT id, categoryname from category order by id";
	static final String SELECT_NEXT_ID = "SELECT max(id) + 1 from category";
	static final String SELECT_COUNT = "SELECT count(*) from category";

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
	@Options(resultSetType=ResultSetType.SCROLL_INSENSITIVE)
	List<Category> selectAllCategories(RowBounds rowBounds);
	
	@Select(SELECT_NEXT_ID)
	Long selectNextId();
	
	@Select(SELECT_COUNT)
	Long selectCount();
}
