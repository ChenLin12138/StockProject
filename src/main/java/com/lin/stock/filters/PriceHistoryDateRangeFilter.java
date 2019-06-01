package com.lin.stock.filters;

import java.util.ArrayList;
import java.util.List;

import com.lin.stock.model.PriceHistory;

/**
 * @author Chen Lin
 * @date 2019-06-01
 */

public class PriceHistoryDateRangeFilter {
	
	public List<PriceHistory>  filter(List<PriceHistory> list, String beginDate, String endDate) {
		
		List<PriceHistory> result = new ArrayList<PriceHistory>();
		
		for(PriceHistory e : list) {
			if(e.getDate().compareTo(beginDate) >= 0 && e.getDate().compareTo(endDate) <= 0)  {
				result.add(e);	
			}
			
		}
		
		return result;
	}
	
}
