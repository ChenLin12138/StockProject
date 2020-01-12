package com.lin.analyse.app;

import java.util.List;

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
import com.lin.stock.model.PriceChange;
import com.lin.stock.model.PriceHistory;
import com.lin.stock.service.PriceHistoryService;

/**
 * @author Chen Lin
 * @date 2020-01-11
 */

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = { DataSourceConfig.class, MybatisMapperConfig.class })
@ComponentScan(value="com.lin.stock.*")
public class OneStockHistoryAnalyser {

	
	private String stockCode = "600009";
	private String beginDateOfMonth = "0201";
	private String endDateOfMonth = "0229";
	private int startYear = 1990;
	private int endYear = 2019;
	
	@Autowired
	PriceHistoryService priceHistoryService;
	
	@Test
	public void oneStockHistoryReport() {
		
		for(int year = startYear; year <= endYear; year++) {
			String beginDate = String.valueOf(year)+beginDateOfMonth;
			String endDate = String.valueOf(year)+endDateOfMonth;
			List<PriceHistory> priceHistories = priceHistoryService.getStockPriceByDateRange(stockCode,beginDate, endDate);
			
			if(null != priceHistories && !priceHistories.isEmpty()) {					
				PriceChange priceChange = priceHistoryService.caculatePriceChange(priceHistories.get(0),priceHistories.get(priceHistories.size()-1));
				System.out.println(stockCode+","+year+","+priceChange.getPchg()*100+"%");
			}
		}
	}
	
}
