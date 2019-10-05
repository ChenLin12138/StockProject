package com.lin.stock.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lin.stock.cache.PriceHistoryCache;
import com.lin.stock.exceptions.InValidDateException;
import com.lin.stock.model.PriceHistory;
import com.lin.stock.utils.StatisticsUtil;

/**
 * @author Chen Lin
 * @date 2019-10-02
 */

@Service
public class MovingAverageService {
	
	public static int MA5= 5;
	public static int MA10= 10;
	public static int MA30= 30;
	
	@Autowired
	PriceHistoryService priceHistoryService;
	
	@Autowired
	BusinessDateService businessDateService;
	
	@Autowired
	PriceHistoryCache cache;
	
	public float getAverage(String date, int numberOfdays, String stockCode) throws InValidDateException{
		
		List<PriceHistory> priceHistories = new ArrayList<PriceHistory> ();	
		
		if(!cache.isEmpty()) {
			PriceHistory priceHistory = new PriceHistory();
			priceHistory.setCode(stockCode);
			priceHistory.setDate(date);
			List<PriceHistory> cachedList = cache.getStockTradeList(stockCode);
			int indexOfPriceHistory = cachedList.indexOf(priceHistory);

			if(indexOfPriceHistory + 1 < numberOfdays) {
				throw new InValidDateException("Invalid BusinessDate! "+"Stock Code "+stockCode+",date "+date+",NumberOfDate "+numberOfdays+".");
			}
			
			for(int i = indexOfPriceHistory ; i > indexOfPriceHistory - numberOfdays; i--) {
				priceHistories.add(cachedList.get(i));
			}
		}else {
			//获取传入日期最后几天的股票信息
			priceHistories = priceHistoryService.getLastInfosByDate(stockCode, date, numberOfdays);
			if(priceHistories.size() < numberOfdays) {
				throw new InValidDateException("Invalid BusinessDate! "+"Stock Code "+stockCode+",date "+date+",NumberOfDate "+numberOfdays+".");
			}
		}
		
		//从对象列表中抽取每个元素的Tclose组成新的列表
		List<Float> list = priceHistories.stream().map(PriceHistory::getTclose).collect(Collectors.toList());
		return StatisticsUtil.getFloatAverageAround2(list);
	}
	
	//MA均线是否向上
	public boolean isMAUp(String date, int numberOfdays, String stockCode) throws InValidDateException {
		//这有一个bug，如若传入的当天和他的前一天都没有股票交易数据。那么向上和向下无法计算。
		return getAverage(date,numberOfdays,stockCode) - getAverage(businessDateService.getPreviousBusinessDate(stockCode, date),numberOfdays,stockCode) > 0;
	}
	
	//MA均线是否向下
	public boolean isMADown(String date, int numberOfdays, String stockCode) throws InValidDateException {
		//这有一个bug，如若传入的当天和他的前一天都没有股票交易数据。那么向上和向下无法计算。	
		return getAverage(date,numberOfdays,stockCode) - getAverage(businessDateService.getPreviousBusinessDate(stockCode, date),numberOfdays,stockCode) < 0;
	}
	
	//其实只有一只股票开盘前五天是没有五日均线的，为了这个错误进行大量IO操作，我觉得是不值得的。
	//验证先写在这里，最后是否取消这个验证，我再考虑
	//可能需要一个driver表来驱动，这样就可以少很多验证的情况
	//现在我们不调用这个函数，默认传入的日期是正确的
	private boolean isValidBusinessDate(String date,String stockCode) {
		PriceHistory priceHistory = priceHistoryService.getPriceHistoryWithStockCodeAndDate(stockCode, date);
		return null == priceHistory ? false : true;
	}
}
