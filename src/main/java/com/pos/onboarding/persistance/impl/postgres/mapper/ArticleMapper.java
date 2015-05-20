package com.pos.onboarding.persistance.impl.postgres.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.pos.onboarding.beans.Article;

public interface ArticleMapper {
	static final String SELECT_BY_ID = "SELECT * from article WHERE id = #{id}";
	static final String INSERT = "INSERT into article(id, categoryid, name, description, price, inventory) VALUES(#{id}, #{categoryId}, #{name}, #{description}, #{price}, #{inventory})";
	static final String UPDATE = "UPDATE article SET categoryid=#{categoryId}, name=#{name}, description=#{description}, price=#{price}, inventory=#{inventory} WHERE id =#{id}";
	static final String DELETE = "DELETE FROM article WHERE id =#{Id}";
	static final String SELECT_ALL = "SELECT * from article";

	@Results(
			{
				@Result(id=true, property = "id", column = "id"),
				@Result(property = "categoryId", column = "categoryid"),
				@Result(property = "name", column = "name"),
				@Result(property = "description", column = "description"),
				@Result(property = "price", column = "price"),
				@Result(property = "inventory", column = "inventory")
	})
	@Select(SELECT_BY_ID)
	Article selectArticle(Long id);

	@Insert(INSERT)
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insertArticle(Article article);

	@Update(UPDATE)
	void updateArticle(Article article);

	@Delete(DELETE)
	void deleteArticle(Long id);
	
	@Select(SELECT_ALL)
	List<Article> selectAllArticles();
}
