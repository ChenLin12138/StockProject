package com.lin.stock.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lin.stock.dao.mappers.PriceHistoryMapper;
import com.lin.stock.dao.mappers.StockMapper;

/**
 * @author Chen Lin
 * @date 2019-05-18
 */

@Configuration
public class MybatisMapperConfig {
	
	@Autowired
	BasicDataSource dataSource;

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource);
		return (SqlSessionFactory) sqlSessionFactory.getObject();
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate() throws Exception {
		SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
		sessionTemplate.getConfiguration().addMappers("com.lin.stock.dao.mappers");
		return sessionTemplate;
	}
	
	@Bean
	public PriceHistoryMapper priceHistoryMapper() throws Exception {

		return sqlSessionTemplate().getMapper(PriceHistoryMapper.class);

	}
	
	@Bean
	public StockMapper stockMapper() throws Exception {

		return sqlSessionTemplate().getMapper(StockMapper.class);

	}
}
