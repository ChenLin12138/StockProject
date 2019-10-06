package com.lin.analyse.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

import com.lin.stock.cache.PriceHistoryCache;
import com.lin.stock.exceptions.InValidDateException;
import com.lin.stock.filters.DividendRightFilter;
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
	
	public static final int STATISTICS_START_DATE = 30;
	
	@Autowired
	PriceHistoryCache  priceHistoryCache;
	
	@Autowired
	DividendRightFilter dividendRightFilter;
	
	@Test
	public void start() throws IOException {
		StopWatch sw = new StopWatch();
		sw.start("DataLoad");
		priceHistoryCache.loadCache();
//		priceHistoryCache.LoadCacheByStock("600608");
//		priceHistoryCache.LoadCacheByStock("000002");
//		priceHistoryCache.LoadCacheByStock("000003");
//		priceHistoryCache.LoadCacheByStock("000004");
//		priceHistoryCache.LoadCacheByStock("000005");
		sw.stop();
        System.out.println(sw.prettyPrint());
		sw.start("TradeStrategy2");
		List<String> stockCodes = priceHistoryService.getAllStockCode();
		List<String> report = new ArrayList<String>(50000);
		report.add("StockCode,BuyDate,SellDate,BuyPrice,SellPrice,Change,Rate");
		for(String stockCode : stockCodes) {
			clearTrade();
			List<String> tradeDates = priceHistoryCache.getStockTradeList(stockCode).stream().map(PriceHistory::getDate).collect(Collectors.toList());
			if(tradeDates.size() > STATISTICS_START_DATE) { 
				for(String date : tradeDates.subList(STATISTICS_START_DATE, tradeDates.size())) {
					try {
						if(Trade.EMPTY.equals(trade.getStatus()) && maCorssService.isMA5CrossMA30Up(stockCode, date) && turnOverService.isIncreaseTimesWithMedian(stockCode, date, 10, 2, 3)) {
							try {
								String nextBusinessDate = businessDateService.getNextBusinessDate(stockCode, date);
								PriceHistory nextDatePriceHistory = priceHistoryCache.getPriceHistoryInfo(stockCode, nextBusinessDate);
								trade.setBuyDate(nextBusinessDate);
								trade.setStatus(Trade.HOLDING);
								trade.setBuyPrice(nextDatePriceHistory.getTopen());
								trade.setStockCode(stockCode);
							}catch(InValidDateException e) {
								e.printStackTrace();
							}			
						}else if (Trade.HOLDING.equals(trade.getStatus())){
							if(dividendRightFilter.filer(priceHistoryCache.getPriceHistoryInfo(stockCode, date))) {
								clearTrade();
							}else if(maCorssService.isTclosePriceUnderMA10(stockCode, date)){
								PriceHistory datePriceHistory = priceHistoryCache.getPriceHistoryInfo(stockCode, date);
								trade.setSellDate(date);
								trade.setSellPrice(datePriceHistory.getTopen());
								trade.setStatus(Trade.EMPTY);
								System.out.println(trade);
								report.add(trade.getReportLayout());
							}
						}
					} catch (InValidDateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		FileUtil.write("TradeStratety2.csv", report);
		sw.stop();
        System.out.println(sw.prettyPrint());
	}
}
