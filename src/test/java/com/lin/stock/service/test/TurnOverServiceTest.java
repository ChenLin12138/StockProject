package com.lin.stock.service.test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lin.stock.service.TurnOverService;

/**
 * @author Chen Lin
 * @date 2019-10-03
 */

public class TurnOverServiceTest extends BaseServiceTest{
	
	@Autowired
	private TurnOverService turnOverService;
	
	@Test
	public void shouldRetureTrueWhenDateIs19980716StockCode600001() {
		Assert.assertTrue(turnOverService.isIncreaseTimesWithMedian("600001", "19980716", 10, 2, 3));
	}
	
	@Test
	public void shoudlReturnFalseWhenDateIs19980714StockCode600001() {
		Assert.assertFalse(turnOverService.isIncreaseTimesWithMedian("600001", "19980713", 10, 2, 3));
	}

}
