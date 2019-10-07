package com.lin.analyse.app.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

import com.lin.analyse.app.BaseTradeStrategy;
import com.lin.stock.cache.PriceHistoryCache;
import com.lin.stock.exceptions.InValidDateException;
import com.lin.stock.filters.DividendRightFilter;
import com.lin.stock.model.PriceHistory;
import com.lin.stock.utils.FileUtil;

/**
 * @author Chen Lin
 * @date 2019-10-07
 */


/*
 * 买入策略：
 * 1.五日均线上穿30日线
 * 卖出策略：
 * 1.收盘价下破10日均线
 * */
public class TradeStrategy3Search extends BaseTradeStrategy {

	public static final int STATISTICS_START_DATE = 30;
	
	public static final String SearchDate = "20190930";
	public static final String cacheStartDate = "20190601";
	public static final String cachedEndDate = "20190930";
	
	@Autowired
	PriceHistoryCache  priceHistoryCache;
	
	@Autowired
	DividendRightFilter dividendRightFilter;
	
	@Test
	public void search() throws IOException, InValidDateException {
		StopWatch sw = new StopWatch();
		sw.start("DataLoad");
		priceHistoryCache.LoadCacheByDateRange(cacheStartDate,cachedEndDate);
		sw.stop();
        System.out.println(sw.prettyPrint());
		sw.start("TradeStrategy3Search");
		List<String> stockCodes = priceHistoryService.getAllStockCode();
		List<String> report = new ArrayList<String>(200);
		report.add("StockCode");
		for(String stockCode : stockCodes) {
			List<String> tradeDates = priceHistoryCache.getStockTradeList(stockCode).stream().map(PriceHistory::getDate).collect(Collectors.toList());			
			if(tradeDates.size() > STATISTICS_START_DATE) { 
				try {
					if(maCorssService.isMA5CrossMA30Up(stockCode, SearchDate)) {
						report.add(stockCode);			
					}
				}catch(InValidDateException e) {
					e.printStackTrace();
				}
			}
		}
		FileUtil.write("TradeStratety3Search"+SearchDate+".csv", report);
		sw.stop();
        System.out.println(sw.prettyPrint());
	}

}
