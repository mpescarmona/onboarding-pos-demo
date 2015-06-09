package com.pos.onboarding.connection.impl.ibatis;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyBatisUtil {
	private static SqlSessionFactory sqlSessionFactory;
	private static final Logger log = LogManager.getLogger(MyBatisUtil.class);
	private static final String IBATIS_CONFIG_FILE = "mybatis-config.xml";

	static {
		log.trace("Starting ibatis session factory from file: {}",
				IBATIS_CONFIG_FILE);
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(IBATIS_CONFIG_FILE);
			sqlSessionFactory = new SqlSessionFactoryBuilder()
					.build(inputStream);
		} catch (IOException e) {
			log.error("There were errors trying to start ibatis from file: "
					+ IBATIS_CONFIG_FILE + ". Result: " + e.getMessage());
		}
	}

	public static SqlSessionFactory getSqlSessionFactory() {
		log.trace("Enter method getSqlSessionFactory. Result: {}",
				sqlSessionFactory);
		return sqlSessionFactory;
	}
}
