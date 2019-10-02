package com.lin.stock.service;

import java.text.DateFormat;
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
	
	public float getAverage(String date, int numberOfdays, String stockCode) throws InValidDateException, ParseException {
		
		List<Float> list = new ArrayList<Float>();
		
		int count = 0;
		String currentDate = date;
		String firstBusinessDate = priceHistoryService.getFirstBusinessDateByStockCode(stockCode);
		if(Integer.parseInt(date)<Integer.parseInt(firstBusinessDate)) {
			throw new InValidDateException("Invalid BusinessDate! "+"Stock Code "+stockCode+",date "+date+",NumberOfDate "+numberOfdays+".");
		}
		
		while(count < numberOfdays) {
			PriceHistory priceHistory = priceHistoryService.getPriceHistoryWithStockCodeAndDate(stockCode, currentDate);	
			if(null != priceHistory) {			
				list.add(priceHistory.getTclose());		
				count++;
			}
			
			if(count < numberOfdays &&currentDate.equals(firstBusinessDate)) {
				throw new InValidDateException("Reach the 1st business date! "+"Stock Code "+stockCode+",date "+date+",NumberOfDate "+numberOfdays+".");
			}
			currentDate = DateUtil.getPrevDate(currentDate);
		}
		
		return StatisticsUtil.getFloatAverageAround2(list);
	}
	
}
