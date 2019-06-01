package com.lin.stock.service.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.lin.stock.config.DataSourceConfig;
import com.lin.stock.config.MybatisMapperConfig;
import com.lin.stock.model.PriceHistory;
import com.lin.stock.service.TradeService;

/**
 * @author Chen Lin
 * @date 2019-06-01
 */

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = { DataSourceConfig.class, MybatisMapperConfig.class })
@ComponentScan(value="com.lin.stock.*")
public class TradeServiceTest {
	
	@Autowired
	private TradeService service ;
	
	@Test
	public void shouldReturnNullWhenDateIs19890101() {
		PriceHistory history =service.getTrade("600001", "19890101");
		Assert.assertNull(history);
	}
	
	@Test
	public void shouldReturnNullWhenStockCodeIs000000() {
		PriceHistory history =service.getTrade("000000", "20091210");
		Assert.assertNull(history);
	}
	
	@Test
	public void shouldTrueWhenStockCodeIs600001DateIs20091210() {
		PriceHistory history =service.getTrade("600001", "20091210");
		Assert.assertNotNull(history);
	}
	
}
