package com.pos.onboarding.persistance.impl.postgres.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.pos.onboarding.beans.Customer;

public interface CustomerMapper {
	static final String SELECT_BY_ID = "SELECT id, firstname, lastname, phonenumber, email from customer WHERE id = #{id}";
	static final String INSERT = "INSERT into customer(id, firstname, lastname, phonenumber, email) VALUES(#{id}, #{firstName}, #{lastName}, #{phoneNumber}, #{email})";
	static final String UPDATE = "UPDATE customer SET firstname=#{firstName}, lastname=#{lastName}, phonenumber=#{phoneNumber}, email=#{email} WHERE id =#{id}";
	static final String DELETE = "DELETE FROM customer WHERE id =#{customerId}";
	static final String SELECT_ALL = "SELECT id, firstname, lastname, phonenumber, email from customer";
	static final String SELECT_NEXT_ID = "SELECT max(id) + 1 from customer";

	@Results(
			{
				@Result(id=true, property = "id", column = "Id"),
				@Result(property = "firstName", column = "FirstName"),
				@Result(property = "lastName", column = "LastName"),
				@Result(property = "phoneNumber", column = "PhoneNumber"),
				@Result(property = "email", column = "email")
	})
	@Select(SELECT_BY_ID)
	Customer selectCustomer(Long id);

	@Insert(INSERT)
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insertCustomer(Customer customer);

	@Update(UPDATE)
	void updateCustomer(Customer customer);

	@Delete(DELETE)
	void deleteCustomer(Long id);
	
	@Select(SELECT_ALL)
	List<Customer> selectAllCustomers();
	
	@Select(SELECT_NEXT_ID)
	Long selectNextId();
}
