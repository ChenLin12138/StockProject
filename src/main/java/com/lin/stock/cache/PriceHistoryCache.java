package com.lin.stock.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.list.TreeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	private Map<String,List<PriceHistory>> cacheMap = new HashMap<String,List<PriceHistory>>(4096);
	
//	public PriceHistoryCache() {
//		loadCache();
//	}
	
	public void loadCache() {
		List<String> stockCodes = priceHistoryService.getAllStockCode();
		//加载部分数据测试使用
		for(String stockCode : stockCodes.subList(0, 5)) {
			System.out.println(stockCode);
			List<PriceHistory> tradeDates = priceHistoryService.getPriceHistoryByStockCode(stockCode);
			TreeList treeList = new TreeList(tradeDates);
			cacheMap.put(stockCode, treeList);
		}
	}
	
	public List<PriceHistory> getStockTradeList(String stockCode){
		return cacheMap.get(stockCode);
	}
	
	public boolean isEmpty() {
		return cacheMap.isEmpty();
	}
	
}
