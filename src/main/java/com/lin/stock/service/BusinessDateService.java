package com.lin.stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lin.stock.cache.PriceHistoryCache;
import com.lin.stock.dao.mappers.PriceHistoryMapper;
import com.lin.stock.exceptions.InValidDateException;

/**
 * @author Chen Lin
 * @date 2019-10-03
 */

@Service
public class BusinessDateService {

	@Autowired
	private PriceHistoryService service;
	
	@Autowired
	PriceHistoryCache priceHistoryCache;
	
	public String getPreviousBusinessDate(String stockCode, String date) throws InValidDateException {
		if(!priceHistoryCache.isEmpty()) {
			return priceHistoryCache.getPreviousPriceHistoryDate(stockCode,date);
		}else
		{
			return service.getPreviousBusinessInfo(stockCode, date).getDate();
		}
		
	}
	
	public String getNextBusinessDate(String stockCode, String date) throws InValidDateException {
		if(!priceHistoryCache.isEmpty()) {
			return priceHistoryCache.getNextPriceHistoryDate(stockCode,date);
		}else {
			return service.getNextBusinessInfo(stockCode, date).getDate();
		}
		
	}
}
