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

import com.pos.onboarding.bean.Customer;
import com.pos.onboarding.bl.CustomerBl;
import com.pos.onboarding.connection.impl.ibatis.MyBatisUtil;
import com.pos.onboarding.persistence.CustomerManager;
import com.pos.onboarding.persistence.impl.postgres.mapper.CustomerMapper;

@Service("customerServicePostgres")
@Transactional
public class CustomerDAO implements CustomerManager{
	private static final Logger log = LogManager.getLogger(CustomerDAO.class);
	
	private SqlSessionFactory sessionFactory = MyBatisUtil
			.getSqlSessionFactory();

	@Override
	public Customer createCustomer(Customer customer) {
		log.trace("Enter method createCustomer. Method params: {}", customer);
		
		SqlSession session = sessionFactory.openSession();
		Customer newCustomer;
		try {
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
					session.commit();
				}
			}
			
			newCustomer = mapper.selectCustomer(customer.getId());
		} finally {
			session.close();
			log.trace("Return method createCustomer. Method params: {}.", customer);
		}

		return newCustomer;
	}

	@Override
	public boolean updateCustomer(Customer customer) {
		log.trace("Enter method updateCustomer. Method params: {}", customer);
		boolean result = false;
		
		CustomerBl customerBl = new CustomerBl();
		if (customerBl.validateCustomer(customer)) {
			SqlSession session = sessionFactory.openSession();
			try {
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
			} finally {
				session.close();
				log.trace(
						"Return method updateCustomer. Method params: {}. Result: {}",
						customer, result);
			}
		} else {
			log.error("The provided customer is invalid. Please verify and try again later");
		}
		
		return result;
	}

	@Override
	public boolean removeCustomer(Long customerId) {
		log.trace("Enter method removeCustomer. Method params: {}", customerId);
		boolean result = false;
		
		SqlSession session = sessionFactory.openSession();
		try {
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
		} finally {
			session.close();
			log.trace(
					"Return method removeCustomer. Method params: {}. Result: {}",
					customerId, result);
		}

		return result;
	}

	@Override
	public Customer getCustomerById(Long customerId) {
		log.trace("Enter method getCustomerById. Method params: {}", customerId);

		SqlSession session = sessionFactory.openSession();
		Customer customer = null;
		try {
			CustomerMapper mapper = session.getMapper(CustomerMapper.class);
			customer = mapper.selectCustomer(customerId);
		} finally {
			session.close();
			log.trace(
					"Return method getCustomerById. Method params: {}. Result: {}",
					customerId, customer);
		}

		return customer;
	}

	@Override
	public List<Customer> getAllCategories(int pageNumber, int pageSize) {
		log.trace("Enter method getAll.");

		List<Customer> result = new ArrayList<Customer>();
		SqlSession session = sessionFactory.openSession();
		try {
			CustomerMapper mapper = session.getMapper(CustomerMapper.class);

			int offset = ((pageNumber - 1) * pageSize);
			offset = offset < 0 ? 0 : offset;
			RowBounds rowBounds = new RowBounds(offset, pageSize);

			result = mapper.selectAllCustomers(rowBounds);
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
			CustomerMapper mapper = session.getMapper(CustomerMapper.class);
			result = mapper.selectCount();
		} finally {
			session.close();
			log.trace("Return method getCount. Result: {}", result);
		}

		return result;
	}
}
