package com.lin.stock.service.test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lin.stock.model.PriceChange;
import com.lin.stock.model.PriceHistory;
import com.lin.stock.service.PriceHistoryService;

/**
 * @author Chen Lin
 * @date 2019-06-01
 */

public class PriceHistoryServiceTest extends BaseServiceTest{
	
	@Autowired
	private PriceHistoryService service ;
	
	@Test
	public void shouldReturnNullWhenDateIs19890101() {
		PriceHistory history =service.getPriceHistoryWithStockCodeAndDate("600001", "19890101");
		Assert.assertNull(history);
	}
	
	@Test
	public void shouldReturnNullWhenStockCodeIs000000() {
		PriceHistory history =service.getPriceHistoryWithStockCodeAndDate("000000", "20091210");
		Assert.assertNull(history);
	}
	
	@Test
	public void shouldTrueWhenStockCodeIs600001DateIs20091210() {
		PriceHistory history =service.getPriceHistoryWithStockCodeAndDate("600001", "20091210");
		Assert.assertNotNull(history);
	}
	
	@Test
	public void shouldReturnRecordCHGIs0net72AndPCHGIS0net1469WhenStockCodeIsIs600001DateFrom20091101To20091201() {
		PriceChange change = service.getStockPriceChangeByDateRange("600001","20091101","20091201");
		Assert.assertTrue(0.7199998f == change.getChg());
		Assert.assertTrue(0.14693868f == change.getPchg());
		Assert.assertTrue("600001".equals(change.getCode()));		
		Assert.assertTrue("20091101".compareTo(change.getBeginDate()) <= 0);
		Assert.assertTrue("20091201".compareTo(change.getEndDate()) >= 0);
	}
	
	@Test
	public void shouldReturn19980122AsDate() {
		Assert.assertTrue("19980122".equals(service.getFirstBusinessDateInfoByStockCode("600001").getDate()));
		Assert.assertTrue("19980122".equals(service.getFirstBusinessDateByStockCode("600001")));
	}
}
