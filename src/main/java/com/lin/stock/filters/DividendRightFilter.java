package com.lin.stock.filters;
import org.springframework.stereotype.Component;

import com.lin.stock.model.PriceHistory;

/**
 * @author Chen Lin
 * @date 2019-10-06
 */

@Component
public class DividendRightFilter{
	
	public static final float LIMIT_DOWN  = -10.05f;
	
	//把那些除权的数据过滤掉
	//没有什么好的办法，只好用当日Pchg判断
	public boolean filer(PriceHistory priceHistory) {
		return priceHistory.getPchg() < LIMIT_DOWN;
	}
}
