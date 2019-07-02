package com.lin.stock.tasks;

import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;

import com.lin.stock.dao.mappers.PriceHistoryMapper;
import com.lin.stock.model.PriceChange;
import com.lin.stock.model.PriceHistory;
import com.lin.stock.service.PriceHistoryService;

/**
 * @author Chen Lin
 * @date 2019-07-02
 */

public class GetPriceChangeTask implements Callable<List<PriceHistory>> {

	private final String stockCode;
	private final String beginDate;
	private final String endDate;

	@Autowired
	private PriceHistoryMapper mapper;
	
	public GetPriceChangeTask(String stockCode, String beginDate, String endDate) {
		this.stockCode = stockCode;
		this.beginDate = beginDate;
		this.endDate = endDate;
	}

	@Override
	public List<PriceHistory> call() throws Exception {
		
		return mapper.selectByStockCodeAndDateRange(stockCode, beginDate, endDate);
		
	}
}
