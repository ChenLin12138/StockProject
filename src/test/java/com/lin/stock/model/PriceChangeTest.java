package com.lin.stock.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Chen Lin
 * @date 2020-01-12
 */

public class PriceChangeTest {

	@Test
	public void test() {
		
		PriceHistory begin = new PriceHistory();
		begin.setCode("600009");
		begin.setDate("19990104");
		begin.setTclose(8.88f);
		
		PriceHistory end = new PriceHistory();
		end.setCode("600009");
		end.setDate("19990129");
		end.setTclose(9.48f);
		
		PriceChange priceChange = new PriceChange(begin,end);
		
		Assert.assertTrue("19990104".equals(priceChange.getBeginDate()));
		Assert.assertTrue("19990129".equals(priceChange.getEndDate()));
		Assert.assertTrue("600009".equals(priceChange.getCode()));
		Assert.assertTrue(0.6f == priceChange.getPchg());
		Assert.assertTrue("6.76%".equals(priceChange.getChg()));
	}
	
}
