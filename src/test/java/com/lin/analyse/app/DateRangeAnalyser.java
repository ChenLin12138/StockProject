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
import org.springframework.util.StopWatch;

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
	
	private int countThreshold = 10;
	private float rateThreshold = 0.6f;
	private String beginDateOfMonth = "0731";
	private String endDateOfMonth = "0831";
	private int startYear = 1990;
	private int endYear = 2019;

	@Autowired
	private PriceHistoryService priceHistoryService;
	
	@Autowired
	private StockService stockService;
	
	@Test
	public void getReportBaseOnDateRange() {
		
		StopWatch sw = new StopWatch();
		sw.start("PriceHistoryService.getReportBaseOnDateRange");
		List<Stock> stocks = stockService.getAllStock();
		List<PriceChange> priceChanges = new ArrayList<PriceChange>(50);
		
		for(Stock stock : stocks) {
			priceChanges.clear();
			float counter = 0.f;
			float sum = 0.f;
			for(int year = startYear; year < endYear; year++) {
				String beginDate = String.valueOf(year)+beginDateOfMonth;
				String endDate = String.valueOf(year)+endDateOfMonth;
				PriceChange priceChange = priceHistoryService.getStockPriceChangeByDateRange(stock.getCode(), beginDate, endDate);
				
				if(null != priceChange) {
					if (priceChange.getChg() > 0 ) {
						counter ++;
					} 
					priceChanges.add(priceChange);	
					sum ++;
				}
			}
			
			if(counter > countThreshold && counter/sum > rateThreshold) {
		
				float gain = 0.f;
				float loss = 0.f;
				
				for(PriceChange priceChange : priceChanges) {
		
					if(priceChange.getPchg() >= 0.f) {
						gain += priceChange.getPchg();
					}else {
						loss += priceChange.getPchg();
					}
				}
				System.out.println(stock.getCode()+","+counter+","+sum+","+counter/sum+","+gain * 100/sum+"%,"+ loss * 100/sum+"%");
			}
		}
		
		sw.stop();
        System.out.println(sw.prettyPrint());
	
	}
	
}
