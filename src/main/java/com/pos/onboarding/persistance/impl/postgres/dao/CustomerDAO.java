package com.pos.onboarding.persistance.impl.postgres.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pos.onboarding.beans.Customer;
import com.pos.onboarding.bl.CustomerBl;
import com.pos.onboarding.connection.impl.ibatis.MyBatisUtil;
import com.pos.onboarding.persistance.CustomerManager;
import com.pos.onboarding.persistance.impl.postgres.mapper.CustomerMapper;

@Service("customerServicePostgres")
@Transactional
public class CustomerDAO implements CustomerManager{
	private static final Logger log = LogManager.getLogger(CustomerDAO.class);
	
	@Override
	public Customer createCustomer(Customer customer) {
		log.trace("Enter method createCustomer. Method params: {}", customer);
		
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		CustomerMapper mapper = session.getMapper(CustomerMapper.class);
		Long nextId = mapper.selectNextId();
		nextId = nextId != null ? nextId : 1l;
		if (customer.getId() == null) {
			customer.setId(nextId);
		}
		Customer result = mapper.selectCustomer(customer.getId());
		if (result != null) {
			log.error("The provided customer already exists");
		} else {
			CustomerBl customerBl = new CustomerBl();
			if (customerBl.validateCustomer(customer)) {
				mapper.insertCustomer(customer);
			}
		}
		
		Customer newCustomer = mapper.selectCustomer(customer.getId());
		session.commit();
		session.close();

		log.trace("Return method createCustomer. Method params: {}.", customer);
		return newCustomer;
	}

	@Override
	public boolean updateCustomer(Customer customer) {
		log.trace("Enter method updateCustomer. Method params: {}", customer);
		boolean result = false;
		
		CustomerBl customerBl = new CustomerBl();
//		if (customerBl.validateCustomer(customer)) {
			SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
			CustomerMapper mapper = session.getMapper(CustomerMapper.class);
			
			Customer existingCustomer = mapper.selectCustomer(customer.getId());
			if (existingCustomer == null) {
				log.debug("The provided customer does not exists. Adding to the database.");
				mapper.insertCustomer(customer);
			} else {
				log.debug("The provided customer exists. Updating the changes to the database.");
				mapper.updateCustomer(customer);
				result = true;
			}
			
			session.commit();
			session.close();
//		}
		
		log.trace(
				"Return method updateCustomer. Method params: {}. Result: {}",
				customer, result);
		return result;
	}

	@Override
	public boolean removeCustomer(Long customerId) {
		log.trace("Enter method removeCustomer. Method params: {}", customerId);
		boolean result = false;
		
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		CustomerMapper mapper = session.getMapper(CustomerMapper.class);
		
		Customer existingCustomer = mapper.selectCustomer(customerId);
		if (existingCustomer == null) {
			log.error(
					"The provided customer does not exists. Method prams: {}. Result: {}",
					customerId, result);
		} else {
			mapper.deleteCustomer(customerId);
			result = true;
			log.debug(
					"The provided customer was successfully removed. Method prams: {}. Result: {}",
					customerId, result);
		}
		session.commit();
		session.close();

		log.trace(
				"Return method removeCustomer. Method params: {}. Result: {}",
				customerId, result);
		return result;
	}

	@Override
	public Customer getCustomerById(Long customerId) {
		log.trace("Enter method getCustomerById. Method params: {}", customerId);

		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		CustomerMapper mapper = session.getMapper(CustomerMapper.class);
		Customer customer = mapper.selectCustomer(customerId);
		session.close();
		
		log.trace(
				"Return method getCustomerById. Method params: {}. Result: {}",
				customerId, customer);

		return customer;
	}

	@Override
	public List<Customer> getAllCategories() {
		log.trace("Enter method getAll.");

		List<Customer> result = new ArrayList<Customer>();
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		CustomerMapper mapper = session.getMapper(CustomerMapper.class);
		result = mapper.selectAllCustomers();
		session.close();

		log.trace("Return method getAll. Result: {}", result);

		return result;
	}
}
