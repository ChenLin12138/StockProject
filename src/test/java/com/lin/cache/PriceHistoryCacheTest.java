package com.lin.cache;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lin.stock.cache.PriceHistoryCache;
import com.lin.stock.exceptions.InValidDateException;
import com.lin.stock.model.PriceHistory;
import com.lin.stock.service.test.BaseServiceTest;

/**
 * @author Chen Lin
 * @date 2019-10-05
 */

public class PriceHistoryCacheTest extends BaseServiceTest{

	@Autowired
	PriceHistoryCache priceHistoryCache;
	
	@Before
	public void load() {
		priceHistoryCache.LoadCacheByStock("000001");
	}
	
	//600001邯郸钢铁是退市股票，交易条数应该不会改变
//	@Test
//	public void shouldReturn2753AsSize() {
//		Assert.assertTrue(2753==priceHistoryCache.getStockTradeList("600001").size());;
//	}
	
	@Test
	public void shouldReturn12don55AsTcloseWhenAsDateis20190225StockCodeis000001() {
		Assert.assertTrue(12.55f==priceHistoryCache.getPriceHistoryInfo("000001", "20190225").getTclose());
	}
	
	@Test
	public void getNextPriceHistoryInfosTest() throws InValidDateException {
		List<PriceHistory> list = priceHistoryCache.getNextPriceHistoryInfos("000001", "20190225", 5);
		Assert.assertTrue(5==list.size());
		Assert.assertTrue("20190225".equals(list.get(0).getDate()));
		Assert.assertTrue("20190226".equals(list.get(1).getDate()));
		Assert.assertTrue("20190227".equals(list.get(2).getDate()));
		Assert.assertTrue("20190228".equals(list.get(3).getDate()));
		Assert.assertTrue("20190301".equals(list.get(4).getDate()));
	}
	
	@Test
	public void getPreviousPriceHistoryInfosTest() throws InValidDateException {
		List<PriceHistory> list = priceHistoryCache.getPreviousPriceHistoryInfos("000001", "20190225", 5);
		Assert.assertTrue(5==list.size());
		Assert.assertTrue("20190225".equals(list.get(0).getDate()));
		Assert.assertTrue("20190222".equals(list.get(1).getDate()));
		Assert.assertTrue("20190221".equals(list.get(2).getDate()));
		Assert.assertTrue("20190220".equals(list.get(3).getDate()));
		Assert.assertTrue("20190219".equals(list.get(4).getDate()));
	}
	
	@Test
	public void shouldReturn20190301AsNextDateWhenStockCodeIs000001AndDateIs20190228() throws InValidDateException {
		Assert.assertTrue("20190301".equals(priceHistoryCache.getNextPriceHistoryDate("000001", "20190228")));
	}
	
	@Test
	public void shouldReturn20190222AsPreviousDateWhenStockCodeIs000001AndDateIs20190225() throws InValidDateException {
		Assert.assertTrue("20190222".equals(priceHistoryCache.getPreviousPriceHistoryDate("000001", "20190225")));
	}
	
}
