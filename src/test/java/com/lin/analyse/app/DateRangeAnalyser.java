package com.lin.analyse.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.lin.stock.model.PriceHistory;
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
				//我们是否可以在这里启动多个线程去数据库获取值？并且把这个值放入priceChanges中
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
				System.out.println(stock.getCode()+","+(int)counter+","+(int)sum+","+counter/sum+","+gain * 100/sum+"%,"+ loss * 100/sum+"%");
			}
		}
		
		sw.stop();
        System.out.println(sw.prettyPrint());
	
	}
	
	@Test
	public void getReportBaseOnDateRangeOpt() {
		
		StopWatch sw = new StopWatch();
		sw.start("PriceHistoryService.getReportBaseOnDateRangeOpt");
		List<Stock> stocks = stockService.getAllStock();
		Map<String, List<PriceChange>> priceChangeListGroupByCode = new HashMap<String, List<PriceChange>>();
		
		for(int year = startYear; year < endYear; year++) {
			String beginDate = String.valueOf(year)+beginDateOfMonth;
			String endDate = String.valueOf(year)+endDateOfMonth;
			List<PriceHistory> priceHistories = priceHistoryService.getStockPriceByDateRange(beginDate, endDate);
			
			if(null != priceHistories && !priceHistories.isEmpty()) {
				//priceHistories里面装着指定日期范围内所有股票的信息
				//下面的Lambda表达式使得priceHistories按照股票代码分组
				Map<String, List<PriceHistory>> priceHistoriesByStockCode =
						priceHistories
						.stream()
						.collect(Collectors.groupingBy(PriceHistory::getCode));
				
				//没想到合适的lambda表达式暂时凑合
				//这里会计算某年某段时间范围内所有股票的涨跌情况
				for(Stock stock : stocks) {
					if(priceHistoriesByStockCode.containsKey(stock.getCode())) {
						List<PriceHistory> priceHistoryList = priceHistoriesByStockCode.get(stock.getCode());
						PriceChange priceChange = priceHistoryService.caculatePriceChange(priceHistoryList.get(0),priceHistoryList.get(priceHistoryList.size()-1));
						
						if(priceChangeListGroupByCode.containsKey(stock.getCode())){
							List<PriceChange> priceChanges = priceChangeListGroupByCode.get(stock.getCode());
							priceChanges.add(priceChange);
							priceChangeListGroupByCode.put(stock.getCode(), priceChanges);
						}else {
							List<PriceChange> priceChanges = new ArrayList<PriceChange>(50);
							priceChanges.add(priceChange);
							priceChangeListGroupByCode.put(stock.getCode(), priceChanges);
						}	
					}
				}
			}
		}
		
		for(Stock stock : stocks) {
			float counter = 0.f;
			float gain = 0.f;
			float loss = 0.f;
			List<PriceChange> priceChangesByStockCode = priceChangeListGroupByCode.get(stock.getCode());	
	
			if(priceChangeListGroupByCode.containsKey(stock.getCode())) {
				for(PriceChange priceChange : priceChangesByStockCode) {
					if(priceChange.getChg() > 0) {
						counter ++;
					}
					
					if(priceChange.getPchg() >= 0.f) {
						gain += priceChange.getPchg();
					}else {
						loss += priceChange.getPchg();
					}
					
				}
			}
		
			if(counter > countThreshold && counter/priceChangesByStockCode.size() > rateThreshold) {
					System.out.println(stock.getCode()+","+(int)counter+","+priceChangesByStockCode.size()+","+counter/priceChangesByStockCode.size()+","+gain * 100/priceChangesByStockCode.size()+"%,"+ loss * 100/priceChangesByStockCode.size()+"%");
			}	
		}
		
		sw.stop();
        System.out.println(sw.prettyPrint());
	
	}
	
}
