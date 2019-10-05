package com.lin.stock.service.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lin.stock.cache.PriceHistoryCache;
import com.lin.stock.exceptions.InValidDateException;
import com.lin.stock.service.BusinessDateService;

/**
 * @author Chen Lin
 * @date 2019-10-03
 */

public class BusinessDateServiceTest extends BaseServiceTest {

	@Autowired
	private BusinessDateService service;
	
	@Autowired
	private PriceHistoryCache priceHistoryCache;
	
	
	@Before
	public void loadCache() {
		priceHistoryCache.LoadCacheByStock("600001");
	}
	
	@Test
	public void shouldReturn19980217AsBusinessDate() throws InValidDateException{
		Assert.assertTrue("19980217".equals(service.getPreviousBusinessDate("600001", "19980218")));
	}
	
	
	@Test
	public void shouldReturn19980227AsBusinessDate() throws InValidDateException {
		Assert.assertTrue("19980302".equals(service.getNextBusinessDate("600001", "19980227")));
	}
}
