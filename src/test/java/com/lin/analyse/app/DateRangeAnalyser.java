package com.lin.analyse.app;

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
import com.lin.stock.service.PriceHistoryService;

/**
 * @author Chen Lin
 * @date 2019-06-01
 */

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = { DataSourceConfig.class, MybatisMapperConfig.class })
@ComponentScan(value="com.lin.stock.*")
public class DateRangeAnalyser {

	@Autowired
	private PriceHistoryService service;
	
	
	@Test
	public void getReportBaseOnDateRange() {
		
		PriceHistory history = new PriceHistory();
		
		for() {
			
		}
		
		for() {
			service.getStockPriceChangeByDateRange(stockCode, beginDate, endDate)
		}
	
		
	}
	
}
