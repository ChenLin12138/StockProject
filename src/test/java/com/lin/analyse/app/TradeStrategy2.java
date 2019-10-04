package com.lin.analyse.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.util.StopWatch;

import com.lin.stock.exceptions.InValidDateException;
import com.lin.stock.model.PriceHistory;
import com.lin.stock.model.Trade;
import com.lin.stock.utils.FileUtil;

/**
 * @author Chen Lin
 * @date 2019-10-04
 */

/*
 * 买入策略：
 * 1.五日均线上穿30日线
 * 2.成交量在10个交易日内出现3次大于10日中位数的2倍
 * 卖出策略：
 * 1.收盘价下破10日均线
 * */
public class TradeStrategy2 extends BaseTradeStrategy{

	@Test
	public void start() throws IOException {
		StopWatch sw = new StopWatch();
		sw.start("TradeStrategy2");
		List<String> stockCodes = priceHistoryService.getAllStockCode();
		List<String> report = new ArrayList<String>(50000);
		report.add("StockCode,BuyDate,SellDate,BuyPrice,SellPrice,Change,Rate");
		for(String stockCode : stockCodes) {
			clearTrade();
			List<String> tradeDates = priceHistoryService.getAllBusinessDateByStockCode(stockCode);
			for(String date : tradeDates.subList(30, tradeDates.size())) {
				
				try {
					if(Trade.EMPTY.equals(trade.getStatus()) && maCorssService.isMA5CrossMA30Up(stockCode, date) && turnOverService.isIncreaseTimesWithMedian(stockCode, date, 10, 2, 3)) {
						String nextBusinessDate = businessDateService.getNextBusinessDate(stockCode, date);
						PriceHistory nextDatePriceHistory = priceHistoryService.getPriceHistoryWithStockCodeAndDate(stockCode, nextBusinessDate);
						trade.setBuyDate(nextBusinessDate);
						trade.setStatus(Trade.HOLDING);
						trade.setBuyPrice(nextDatePriceHistory.getTopen());
						trade.setStockCode(stockCode);
					}else if (Trade.HOLDING.equals(trade.getStatus()) && maCorssService.isTclosePriceUnderMA10(stockCode, date)){
						String nextBusinessDate = businessDateService.getNextBusinessDate(stockCode, date);
						PriceHistory nextDatePriceHistory = priceHistoryService.getPriceHistoryWithStockCodeAndDate(stockCode, nextBusinessDate);
						trade.setSellDate(nextBusinessDate);
						trade.setSellPrice(nextDatePriceHistory.getTopen());
						trade.setStatus(Trade.EMPTY);
						System.out.println(trade);
						report.add(trade.getReportLayout());
					}
				} catch (InValidDateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		FileUtil.write("TradeStratety2.csv", report);
		sw.stop();
        System.out.println(sw.prettyPrint());
	}
}
