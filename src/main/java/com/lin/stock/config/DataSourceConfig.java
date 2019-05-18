package com.lin.stock.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Chen Lin
 * @date 2019-05-18
 */

@Configuration
public class DataSourceConfig {

	@Bean
	public BasicDataSource dataSrouce() {
		BasicDataSource dataSrouce = new BasicDataSource();
		dataSrouce.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSrouce.setUrl("jdbc:mysql://localhost:3306/stock?useSSL=false");
		dataSrouce.setUsername("root");
		dataSrouce.setPassword("3306");
		dataSrouce.setInitialSize(1);
		dataSrouce.setMaxActive(2);
		return dataSrouce;	
		
	}

}
