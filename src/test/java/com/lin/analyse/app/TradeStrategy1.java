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
import com.lin.stock.exceptions.InValidDateException;
import com.lin.stock.model.PriceHistory;
import com.lin.stock.model.Trade;
import com.lin.stock.service.BusinessDateService;
import com.lin.stock.service.MACorssService;
import com.lin.stock.service.MovingAverageService;
import com.lin.stock.service.PriceHistoryService;
import com.lin.stock.service.TurnOverService;

/**
 * @author Chen Lin
 * @date 2019-10-03
 */

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = { DataSourceConfig.class, MybatisMapperConfig.class })
@ComponentScan(value="com.lin.stock.*")
public class TradeStrategy1 {
	
	@Autowired
	PriceHistoryService priceHistoryService;
	
	@Autowired
	MovingAverageService movingAverageService;
	
	@Autowired
	MACorssService maCorssService;
	
	@Autowired
	TurnOverService turnOverService;
	
	@Autowired
	BusinessDateService businessDateService;
	
	private Trade trade = new Trade();
	
	@Test
	public void test() {
		
//		List<Trade> trades = new ArrayList<Trade>(4000);
		List<String> stockCodes = priceHistoryService.getAllStockCode();
		for(String stockCode : stockCodes) {
			clearTrade();
			List<String> tradeDates = priceHistoryService.getAllBusinessDateByStockCode(stockCode);
			for(String date : tradeDates) {
				
				try {
					if("E".equals(trade.getStatus()) && maCorssService.isMA5CrossMA30Up(stockCode, date) && turnOverService.isIncreaseTimesWithMedian(stockCode, date, 10, 2, 3)) {
						String nextBusinessDate = businessDateService.getNextBusinessDate(stockCode, date);
						PriceHistory nextDatePriceHistory = priceHistoryService.getPriceHistoryWithStockCodeAndDate(stockCode, nextBusinessDate);
						trade.setBuyDate(nextBusinessDate);
						trade.setStatus("H");
						trade.setBuyPrice(nextDatePriceHistory.getTopen());
					}else if ("H".equals(trade.getStatus()) && maCorssService.isMA5CorssMA30Down(stockCode, date)){
						String nextBusinessDate = businessDateService.getNextBusinessDate(stockCode, date);
						PriceHistory nextDatePriceHistory = priceHistoryService.getPriceHistoryWithStockCodeAndDate(stockCode, nextBusinessDate);
						trade.setSellDate(nextBusinessDate);
						trade.setSellPrice(nextDatePriceHistory.getTopen());
						trade.setStatus("E");
					}
				} catch (InValidDateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println(trade);
		}
		
	}

	private void clearTrade() {
		this.trade.setBuyDate("");
		this.trade.setBuyPrice(0);
		this.trade.setSellDate("");
		this.trade.setSellPrice(0);
		this.trade.setStatus("E");
		
		
	}
	
	

}
