package com.lin.stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lin.stock.dao.mappers.PriceHistoryMapper;

/**
 * @author Chen Lin
 * @date 2019-10-03
 */

@Service
public class BusinessDateService {

	@Autowired
	private PriceHistoryMapper mapper;
	
	@Autowired
	private PriceHistoryService service;
	
	public String getPreviousBusinessDate(String stockCode, String date) {
		return service.getPreviousBusinessInfo(stockCode, date).getDate();
	}
}
