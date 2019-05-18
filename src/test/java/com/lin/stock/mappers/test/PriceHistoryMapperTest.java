package com.lin.stock.mappers.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.lin.stock.config.DataSourceConfig;
import com.lin.stock.config.MybatisMapperConfig;
import com.lin.stock.mappers.PriceHistoryMapper;
import com.lin.stock.model.PriceHistory;

/**
 * @author Chen Lin
 * @date 2019-05-18
 */
@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = { DataSourceConfig.class, MybatisMapperConfig.class })
public class PriceHistoryMapperTest {

	@Autowired
	PriceHistoryMapper mapper;
	
	@Rollback(false)
	public void shouldReturnAPriceHistory() {
		mapper.insert(new PriceHistory());
	}
}
