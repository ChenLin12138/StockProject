package com.lin.stock.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lin.stock.exceptions.InValidDateException;
import com.lin.stock.model.PriceHistory;
import com.lin.stock.utils.DateUtil;
import com.lin.stock.utils.StatisticsUtil;

/**
 * @author Chen Lin
 * @date 2019-10-02
 */

@Service
public class MovingAverageService {

	@Autowired
	PriceHistoryService priceHistoryService;
	
	//这个计算值不一定是传入日期的均线值，而是传入日期或者日期的最近一个交易日的均线值
	public float getAverage(String date, int numberOfdays, String stockCode) throws InValidDateException, ParseException {
		
		List<Float> list = new ArrayList<Float>();
		
		int count = 0;
		String currentDate = date;
		if(!isValidBusinessDate(date, stockCode)) {
			throw new InValidDateException("Invalid BusinessDate! "+"Stock Code "+stockCode+",date "+date+",NumberOfDate "+numberOfdays+".");
		}
		
		while(count < numberOfdays) {
			PriceHistory priceHistory = priceHistoryService.getPriceHistoryWithStockCodeAndDate(stockCode, currentDate);	
			if(null != priceHistory) {			
				list.add(priceHistory.getTclose());		
				count++;
			}
			
			currentDate = DateUtil.getPrevDate(currentDate);
			if(!isValidBusinessDate(currentDate,stockCode)) {
				throw new InValidDateException("Reach the 1st business date! "+"Stock Code "+stockCode+",date "+date+",NumberOfDate "+numberOfdays+".");
			}
		}
		
		return StatisticsUtil.getFloatAverageAround2(list);
	}
	
	//MA均线是否向上
	public boolean isMAUp(String date, int numberOfdays, String stockCode) throws InValidDateException, ParseException {
		//这有一个bug，如若传入的当天和他的前一天都没有股票交易数据。那么向上和向下无法计算。
		return getAverage(date,numberOfdays,stockCode) - getAverage(DateUtil.getPrevDate(date),numberOfdays,stockCode) > 0;
	}
	
	//MA均线是否向下
	public boolean isMADown(String date, int numberOfdays, String stockCode) throws InValidDateException, ParseException {
		//这有一个bug，如若传入的当天和他的前一天都没有股票交易数据。那么向上和向下无法计算。
		return getAverage(date,numberOfdays,stockCode) - getAverage(DateUtil.getPrevDate(date),numberOfdays,stockCode) < 0;
	}
	
	//其实只有一只股票开盘前五天是没有五日均线的，为了这个错误进行大量IO操作，我觉得是不值得的。
	//验证先写在这里，最后是否取消这个验证，我再考虑
	//可能需要一个driver表来驱动，这样就可以少很多验证的情况
	private boolean isValidBusinessDate(String date,String stockCode) {
		String firstBusinessDate = priceHistoryService.getFirstBusinessDateByStockCode(stockCode);
		if(Integer.parseInt(date)<Integer.parseInt(firstBusinessDate)) {
			return false;
		}
		return true;
	}
	
}
