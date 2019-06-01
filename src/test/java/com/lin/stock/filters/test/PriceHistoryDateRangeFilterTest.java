package com.lin.stock.filters.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.lin.stock.filters.PriceHistoryDateRangeFilter;
import com.lin.stock.model.PriceHistory;

/**
 * @author Chen Lin
 * @date 2019-06-01
 */

public class PriceHistoryDateRangeFilterTest {

	private List<PriceHistory> priceHistories = new ArrayList<PriceHistory>();
	private PriceHistoryDateRangeFilter filter = new PriceHistoryDateRangeFilter();
	
	@Before
	public void setup() {
		PriceHistory history0 = new PriceHistory();
		history0.setDate("20091203");
		PriceHistory history1 = new PriceHistory();
		history1.setDate("20100103");
		PriceHistory history2 = new PriceHistory();
		history2.setDate("20100105");
		PriceHistory history3 = new PriceHistory();
		history3.setDate("20100110");
		PriceHistory history4 = new PriceHistory();
		history4.setDate("20100113");
		PriceHistory history5 = new PriceHistory();
		history5.setDate("20100202");
		PriceHistory history6 = new PriceHistory();
		history6.setDate("20100102");
		PriceHistory history7 = new PriceHistory();
		history7.setDate("20100112");
		PriceHistory history8 = new PriceHistory();
		history8.setDate("20100101");
		
		priceHistories.add(history0);
		priceHistories.add(history1);
		priceHistories.add(history2);
		priceHistories.add(history3);
		priceHistories.add(history4);
		priceHistories.add(history5);
		priceHistories.add(history6);
		priceHistories.add(history7);
		priceHistories.add(history8);
	}
	
	@Test
	public void shouldReturnListDateBetween20100102To20100112() {
		List<PriceHistory> result = filter.filter(priceHistories, "20100102", "20100112");
		for(PriceHistory e : result) {
			Assert.assertTrue(e.getDate().compareTo("20100102") >= 0);
			Assert.assertTrue(e.getDate().compareTo("20100112") <= 0);		
			System.out.println(e.getDate());
		}
	}
}
