package com.lin.analyse.app;

import java.util.ArrayList;
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
import com.lin.stock.model.Stock;
import com.lin.stock.service.PriceHistoryService;
import com.lin.stock.service.StockService;

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
	private PriceHistoryService priceHistoryService;
	
	
	@Autowired
	private StockService stockService;
	
	
	
	@Test
	public void getReportBaseOnDateRange() {
		
		List<Stock> stocks = stockService.getAllStock();
		List<String> yearRecorder = new ArrayList<String>();
		
		
		
		for(Stock stock : stocks) {
			
			int counter = 0;
			int sum = 0;
			for(int year = 1990; year < 2019; year++) {
				String beginDate = String.valueOf(year)+"0531";
				String endDate = String.valueOf(year)+"0630";
				PriceChange priceChange = priceHistoryService.getStockPriceChangeByDateRange(stock.getCode(), beginDate, endDate);
				
				if(null != priceChange) {
					if (priceChange.getChg() > 0 ) {
						counter ++;
					}else {
						yearRecorder.add(priceChange.getBeginDate());
					}
					sum ++;
				}
			}
			
			if(counter > 10) {
				System.out.println(stock.getCode()+","+counter+","+sum+","+new Float(counter)/new Float(sum));
			}
		}
		
		
		
		
		
	}
	
}
