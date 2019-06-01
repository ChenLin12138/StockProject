package com.lin.stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lin.stock.dao.mappers.PriceHistoryMapper;
import com.lin.stock.model.PriceHistory;

/**
 * @author Chen Lin
 * @date 2019-05-29
 */

@Service
public class TradeService {
	
	@Autowired
	private PriceHistoryMapper mapper;
	
	public PriceHistory getTrade(String StockCode, String Date) {
		
		PriceHistory priceHistory = new PriceHistory();
		priceHistory.setCode(StockCode);
		priceHistory.setDate(Date);
		priceHistory = mapper.selectByStockCodeAndDate(priceHistory);	
		return (null == priceHistory) ? null : priceHistory;
		
	}
}
