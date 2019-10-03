package com.lin.stock.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lin.stock.model.PriceHistory;
import com.lin.stock.utils.StatisticsUtil;

/**
 * @author Chen Lin
 * @date 2019-10-03
 */

@Service
public class TurnOverService {

	@Autowired
	private PriceHistoryService priceHistoryService;
	
	//stock 指定股票
	//date 指定日期
	//days 指定日期前多少天
	//中位数的倍数
	//超过次数指定倍数的次数
	public boolean isIncreaseTimesWithMedian(String stockCode, String date, int days, int times, int threshold) {
		List<PriceHistory> priceHistories = priceHistoryService.getLastInfosByDate(stockCode, date, days);	
		List<Long>tureOverList = priceHistories.stream().map(PriceHistory::getVoturnover).collect(Collectors.toList());
		long meidan = StatisticsUtil.getLongMedian(tureOverList);
		int count = 0;
		for(Long element:tureOverList) {
			if(element >= meidan * times) {
				count ++;
			}
		}
		
		return count >= threshold ? true : false;
	}
	
}
