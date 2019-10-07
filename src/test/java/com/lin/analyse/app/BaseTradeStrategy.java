package com.lin.analyse.app;

import org.springframework.beans.factory.annotation.Autowired;

import com.lin.stock.model.Trade;
import com.lin.stock.service.BusinessDateService;
import com.lin.stock.service.MACorssService;
import com.lin.stock.service.MovingAverageService;
import com.lin.stock.service.PriceHistoryService;
import com.lin.stock.service.TurnOverService;
import com.lin.stock.service.test.BaseServiceTest;

/**
 * @author Chen Lin
 * @date 2019-10-04
 */

public class BaseTradeStrategy extends BaseServiceTest {

	@Autowired
	protected PriceHistoryService priceHistoryService;
	
	@Autowired
	protected MovingAverageService movingAverageService;
	
	@Autowired
	protected MACorssService maCorssService;
	
	@Autowired
	protected TurnOverService turnOverService;
	
	@Autowired
	protected BusinessDateService businessDateService;
	
	protected Trade trade = new Trade();
	
	protected void clearTrade() {
		this.trade.setBuyDate("");
		this.trade.setBuyPrice(0);
		this.trade.setSellDate("");
		this.trade.setSellPrice(0);
		this.trade.setStatus(Trade.EMPTY);
		this.trade.setStockCode("");
	}
}
