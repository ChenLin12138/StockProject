package com.lin.stock.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.list.TreeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lin.stock.exceptions.InValidDateException;
import com.lin.stock.model.PriceHistory;
import com.lin.stock.service.PriceHistoryService;

/**
 * @author Chen Lin
 * @date 2019-10-04
 */

@Component
public class PriceHistoryCache {

	@Autowired
	PriceHistoryService priceHistoryService;
	
	//大小为2的12次方
	private static final Map<String,List<PriceHistory>> cacheMap = new HashMap<String,List<PriceHistory>>(4096);
	
//	public PriceHistoryCache() {
//		loadCache();
//	}
	
	public void loadCache() {
		List<String> stockCodes = priceHistoryService.getAllStockCode();
		for(String stockCode : stockCodes) {
//			System.out.println(stockCode);
			List<PriceHistory> tradeDates = priceHistoryService.getPriceHistoryByStockCode(stockCode);
			TreeList treeList = new TreeList(tradeDates);
			cacheMap.put(stockCode, treeList);
		}
	}
	
	public void LoadCacheByStock(String stockCode) {	
		//加载某只股票所有交易记录
		List<PriceHistory> tradeDates = priceHistoryService.getPriceHistoryByStockCode(stockCode);
		TreeList treeList = new TreeList(tradeDates);
		cacheMap.put(stockCode, treeList);
	}
	
	public List<PriceHistory> getStockTradeList(String stockCode){
		return cacheMap.get(stockCode);
	}
	
	public PriceHistory getPriceHistoryInfo(String stockCode, String date) {
		PriceHistory priceHistory = new PriceHistory();
		priceHistory.setCode(stockCode);
		priceHistory.setDate(date);
		return getPriceHistoryInfo(priceHistory);
	}
	
	public PriceHistory getPriceHistoryInfo(PriceHistory priceHistory){
		List<PriceHistory> list = getStockTradeList(priceHistory.getCode());
		return list.get(list.indexOf(priceHistory));	
	}
		
	public List<PriceHistory> getPreviousPriceHistoryInfos(String stockCode, String date, int numberOfdays) throws InValidDateException{
		PriceHistory priceHistory = new PriceHistory();
		priceHistory.setCode(stockCode);
		priceHistory.setDate(date);
		return getPreviousPriceHistoryInfos(priceHistory,numberOfdays);
	}
	
	public List<PriceHistory> getPreviousPriceHistoryInfos(PriceHistory priceHistory, int numberOfdays) throws InValidDateException{
		List<PriceHistory> list = getStockTradeList(priceHistory.getCode());
		int index = list.indexOf(priceHistory);
	 	List<PriceHistory> results = new ArrayList<PriceHistory>(numberOfdays);
		if(index + 1 < numberOfdays) {
			throw new InValidDateException("Invalid BusinessDate! "+"Stock Code "+priceHistory.getCode()+",date "+priceHistory.getDate()+",NumberOfDate "+numberOfdays+".");
		}
		for(int i = index ; i > index - numberOfdays; i--) {
			results.add(list.get(i));
		}
		return results;
	}
	
	public List<PriceHistory> getNextPriceHistoryInfos(String stockCode, String date, int numberOfdays) throws InValidDateException{
		PriceHistory priceHistory = new PriceHistory();
		priceHistory.setCode(stockCode);
		priceHistory.setDate(date);
		return getNextPriceHistoryInfos(priceHistory,numberOfdays);
	}
	
	public List<PriceHistory> getNextPriceHistoryInfos(PriceHistory priceHistory, int numberOfdays) throws InValidDateException{
		List<PriceHistory> list = getStockTradeList(priceHistory.getCode());
		int index = list.indexOf(priceHistory);
	 	List<PriceHistory> results = new ArrayList<PriceHistory>(numberOfdays);
		for(int i = index ; i < index + numberOfdays; i++) {
			results.add(list.get(i));
		}
		return results;
	}
	
	public boolean isEmpty() {
		return cacheMap.isEmpty();
	}
	
}
