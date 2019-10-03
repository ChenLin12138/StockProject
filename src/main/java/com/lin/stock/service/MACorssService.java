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
	BusinessDateService businessDateService;
	
	
	public boolean isMA5CrossMA30Up(String stockCode, String date) throws InValidDateException {
		
		float currentDateMA5 = movingAverageService.getAverage(date, 5, stockCode);
		float currentDateMA30 =  movingAverageService.getAverage(date, 30, stockCode);
		float previousDateMA5 = movingAverageService.getAverage(businessDateService.getPreviousBusinessDate(stockCode, date), 5, stockCode);
		
		if(previousDateMA5<currentDateMA30 && currentDateMA5>currentDateMA30 && movingAverageService.isMAUp(date, 5, stockCode)) {
			return true;
		}
		
		return false;
	}
	
	public boolean isMA5CorssMA30Down(String stockCode, String date) throws InValidDateException {

		float currentDateMA5 = movingAverageService.getAverage(date, 5, stockCode);
		float currentDateMA30 =  movingAverageService.getAverage(date, 30, stockCode);
		float previousDateMA5 = movingAverageService.getAverage(businessDateService.getPreviousBusinessDate(stockCode, date), 5, stockCode);
		
		if(previousDateMA5>currentDateMA30 && currentDateMA5<currentDateMA30 && movingAverageService.isMADown(date, 5, stockCode)) {
			return true;
		}
		
		return false;
	
	}
	
}
