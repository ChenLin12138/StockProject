package com.lin.stock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lin.stock.dao.mappers.PriceHistoryMapper;
import com.lin.stock.model.PriceChange;
import com.lin.stock.model.PriceHistory;
import com.lin.stock.utils.DateUtil;

/**
 * @author Chen Lin
 * @date 2019-05-29
 */

@Service
public class PriceHistoryService {
	
	@Autowired
	private PriceHistoryMapper mapper;
	
	
	//检查指定股票指定日期是否为交易日
	//若是交易日返回信息
	//若不是交易日返回null
	public PriceHistory getPriceHistoryWithStockCodeAndDate(String stockCode, String date) {
		PriceHistory priceHistory = new PriceHistory();
		priceHistory.setCode(stockCode);
		priceHistory.setDate(date);
		priceHistory = mapper.selectByStockCodeAndDate(priceHistory);	
		return (null == priceHistory) ? null : priceHistory;
	}
	
	public List<PriceHistory> getStockPriceByDateRange(String stockCode, String beginDate, String endDate){
		return mapper.selectByStockCodeAndDateRange(stockCode, beginDate, endDate);
	}
	
	public List<PriceHistory> getStockPriceByDateRange(String beginDate, String endDate){
		return mapper.selectByDateRange(beginDate, endDate);
	} 
	
	//查询该股票的第一个交易日信息
	public PriceHistory getFirstBusinessDateInfoByStockCode(String stockCode) {
		return mapper.selectFirstBusinessInfo(stockCode);
	}
	
	//查询该股票的第一个交易日
	public String getFirstBusinessDateByStockCode(String stockCode) {
		return getFirstBusinessDateInfoByStockCode(stockCode).getDate();
	}
	
	public PriceChange getStockPriceChangeByDateRange (String stockCode, String beginDate, String endDate) {
		List<PriceHistory> priceHistories = mapper.selectByStockCodeAndDateRange(stockCode, beginDate, endDate);
		if(0 != priceHistories.size()) {
			return caculatePriceChange(priceHistories.get(0),priceHistories.get(priceHistories.size() -1 ));
		}
		
		return null;
	} 
		
	//暂时打开这个方法让外面的类调用
	public PriceChange caculatePriceChange(PriceHistory begin, PriceHistory end) {
		
		PriceChange priceChange =  new PriceChange();
		//其实这里应该减去startDate前一天的收盘价，我就偷懒了
		//可以理解在这样的区间(begin,end]
		priceChange.setChg(end.getTclose() - begin.getTclose());
		priceChange.setCode(begin.getCode());
		priceChange.setPchg(end.getTclose()/begin.getTclose() -1);
		priceChange.setBeginDate(begin.getDate());
		priceChange.setEndDate(end.getDate());
		return priceChange;
	}
	

	
	//这个方法可以和下面的方法通过lambda表达式合并成为一个方法。
	private String getActualFromDate(String stockCode, String date) {
		
		while(null == getPriceHistoryWithStockCodeAndDate(stockCode, date)) {
			date = DateUtil.getNextDate(date);
		}
		
		return date;
	}
	
	//这个方法可以和上面的方法通过lambda表达式合并成为一个方法。
	private String getActualEndDate(String stockCode, String date) {
		
		while(null == getPriceHistoryWithStockCodeAndDate(stockCode, date)) {
			date = DateUtil.getPrevDate(date);
		}
		
		return date;
	}
	
}
