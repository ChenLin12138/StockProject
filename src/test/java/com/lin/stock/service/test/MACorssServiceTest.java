package com.lin.stock.service.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lin.stock.cache.PriceHistoryCache;
import com.lin.stock.exceptions.InValidDateException;
import com.lin.stock.service.MACorssService;

/**
 * @author Chen Lin
 * @date 2019-10-03
 */

public class MACorssServiceTest extends BaseServiceTest{

	@Autowired
	MACorssService maCrossService;
	
	@Autowired
	PriceHistoryCache priceHistoryCache;
	
	@Before
	public void loadCache() {
//		priceHistoryCache.LoadCacheByStock("000001");
		priceHistoryCache.LoadCacheByStock("000783");
	}
	
	@Test
	public void shouldReturnTrueWhen20190213Stock000783() throws InValidDateException {
		Assert.assertTrue(maCrossService.isMA5CrossMA30Up("000783", "20190213"));
	}
	
	@Test
	public void shouldReturnTrueWhen20190212Stock000783() throws InValidDateException {
		Assert.assertFalse(maCrossService.isMA5CrossMA30Up("000783", "20190212"));
	}
	
	@Test
	public void shouldReturnTrueWhen20190130Stock000783() throws InValidDateException {
		Assert.assertTrue(maCrossService.isMA5CorssMA30Down("000783", "20190131"));
	}
	
	@Test
	public void shouldReturnTrueWhen20190129Stock000783() throws InValidDateException {
		Assert.assertFalse(maCrossService.isMA5CorssMA30Down("000783", "20190130"));
	}
	
	
//	仅缓存000001的时候才生效
//	@Test
//	public void shouldReturnTrueWhen19940531Stock000001() throws InValidDateException {
//		Assert.assertTrue(maCrossService.isMA5CorssMA30Down("000001", "19940531"));
//	}
//	
//	@Test
//	public void shouldReturnTrueWhenDate19950512Stock000001() throws InValidDateException {
//		Assert.assertTrue(maCrossService.isTclosePriceUnderMA10("000001", "19940513"));
//	}
}
