package com.lin.stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lin.stock.exceptions.InValidDateException;

/**
 * @author Chen Lin
 * @date 2019-10-03
 */

@Service
public class MACorssService {

	@Autowired
	MovingAverageService movingAverageService;
	
	@Autowired
	PriceHistoryService priceHistoryService;
	
	
	@Autowired
	BusinessDateService businessDateService;
	
	
	public boolean isMA5CrossMA30Up(String stockCode, String date) throws InValidDateException {
		
		float currentDateMA5 = movingAverageService.getAverage(date, MovingAverageService.MA5, stockCode);
		float currentDateMA30 =  movingAverageService.getAverage(date, MovingAverageService.MA30, stockCode);
		float previousDateMA5 = movingAverageService.getAverage(businessDateService.getPreviousBusinessDate(stockCode, date), MovingAverageService.MA5, stockCode);
		float previousDateMA30 = movingAverageService.getAverage(businessDateService.getPreviousBusinessDate(stockCode, date), MovingAverageService.MA30, stockCode);
		
		if(previousDateMA5 < previousDateMA30 && currentDateMA5 > currentDateMA30 && movingAverageService.isMAUp(date, MovingAverageService.MA5, stockCode)) {
			return true;
		}
		
		return false;
	}
	
	public boolean isMA5CorssMA30Down(String stockCode, String date) throws InValidDateException {

		float currentDateMA5 = movingAverageService.getAverage(date, MovingAverageService.MA5, stockCode);
		float currentDateMA30 =  movingAverageService.getAverage(date, MovingAverageService.MA30, stockCode);
		float previousDateMA5 = movingAverageService.getAverage(businessDateService.getPreviousBusinessDate(stockCode, date), MovingAverageService.MA5, stockCode);
		float previousDateMA30 = movingAverageService.getAverage(businessDateService.getPreviousBusinessDate(stockCode, date), MovingAverageService.MA30, stockCode);
		
		if(previousDateMA5 > previousDateMA30 && currentDateMA5 < currentDateMA30 && movingAverageService.isMADown(date, MovingAverageService.MA5, stockCode)) {
			return true;
		}
		
		return false;
	
	}
	
	public boolean isTclosePriceUnderMA10(String stockCode, String date) throws InValidDateException {
		float currentDateMA10 = movingAverageService.getAverage(date, MovingAverageService.MA10, stockCode);
		//这个还没走缓存，可以修改
		float tclose = priceHistoryService.getTcloseWithStockCodeAndDate(stockCode, date);
		return tclose < currentDateMA10;
	}
	
}
