package com.lin.stock.filters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lin.stock.cache.PriceHistoryCache;
import com.lin.stock.exceptions.InValidDateException;
import com.lin.stock.model.PriceHistory;

/**
 * @author Chen Lin
 * @date 2019-10-06
 */

@Component
public class DividendRightFilter{
	
	@Autowired
	PriceHistoryCache priceHistoryCache;
	
	public static final float LIMIT_DOWN  = -10.05f;
	public static final float RATE_LIMIT_DOWN  = 0.1005f;
	
	
	//把那些除权的数据过滤掉
	//没有什么好的办法，只好用当日Pchg判断
	public boolean filer(PriceHistory priceHistory) throws InValidDateException {
	
		if(priceHistory.getPchg() < LIMIT_DOWN) {
			return true;
		}
		//有些数据他的pcng都有问题
		String previousDate = priceHistoryCache.getNextPriceHistoryDate(priceHistory.getCode(), priceHistory.getDate());
		PriceHistory previousPriceHistory = priceHistoryCache.getPriceHistoryInfo(priceHistory.getCode(), previousDate);
		float chg = previousPriceHistory.getTopen() - priceHistory.getTclose();
		float chgRate = Math.abs(chg) / previousPriceHistory.getTclose();
		if(chgRate > RATE_LIMIT_DOWN) {
			return true;
		}
		
		
		return false;
	}
}
