package com.lin.stock.service.test;

import java.util.List;

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
	private PriceHistoryService service;
	
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
//		Assert.assertTrue(0.7199998f == change.getChg());
		Assert.assertTrue("14.69%".contentEquals(change.getChg()));
//		Assert.assertTrue(0.14693868f == change.getPchg());
		Assert.assertTrue(0.72f == change.getPchg());
		
		Assert.assertTrue("600001".equals(change.getCode()));		
		Assert.assertTrue("20091101".compareTo(change.getBeginDate()) <= 0);
		Assert.assertTrue("20091201".compareTo(change.getEndDate()) >= 0);
	}
	
	@Test
	public void shouldReturn19980122AsDate() {
		Assert.assertTrue("19980122".equals(service.getFirstBusinessDateInfoByStockCode("600001").getDate()));
		Assert.assertTrue("19980122".equals(service.getFirstBusinessDateByStockCode("600001")));
	}
	
	@Test
	public void shouldReturn19980217AsDate() {
		Assert.assertTrue("19980217".equals(service.getPreviousBusinessInfo("600001", "19980218").getDate()));
	}
	
	@Test
	public void shouldReturn19980217AsBeginDate() {
		List<PriceHistory> results = service.getPreviousBusinessInfos("600001","19980218",5);
		Assert.assertTrue("19980217".equals(results.get(0).getDate()));
		Assert.assertTrue("19980216".equals(results.get(1).getDate()));
		Assert.assertTrue("19980213".equals(results.get(2).getDate()));
		Assert.assertTrue("19980212".equals(results.get(3).getDate()));
		Assert.assertTrue("19980211".equals(results.get(4).getDate()));	
	}
	
	@Test
	public void shouldReturn19980218AsBeginDate() {
		List<PriceHistory> results = service.getLastInfosByDate("600001","19980218",5);
		Assert.assertTrue("19980218".equals(results.get(0).getDate()));
		Assert.assertTrue("19980217".equals(results.get(1).getDate()));
		Assert.assertTrue("19980216".equals(results.get(2).getDate()));
		Assert.assertTrue("19980213".equals(results.get(3).getDate()));
		Assert.assertTrue("19980212".equals(results.get(4).getDate()));	
	}
	
	@Test
	public void shouldReturn19980219AsDate() {
		Assert.assertTrue("19980219".equals(service.getNextBusinessInfo("600001", "19980218").getDate()));
	}
	
	@Test
	public void shouldReturn19980219AsBeginDate() {
		List<PriceHistory> results = service.getNextBusinessInfos("600001","19980218",5);
		Assert.assertTrue("19980219".equals(results.get(0).getDate()));
		Assert.assertTrue("19980220".equals(results.get(1).getDate()));
		Assert.assertTrue("19980223".equals(results.get(2).getDate()));
		Assert.assertTrue("19980224".equals(results.get(3).getDate()));
		Assert.assertTrue("19980225".equals(results.get(4).getDate()));	
	}
	
	@Test
	public void shouldRetureSize2753WhenStockCodeIs600001() {
		Assert.assertTrue(2753==service.getAllBusinessDateByStockCode("600001").size());
	}
	
	//上市公司的总数，这个case可能会因为更新数据库导致无法通过
	@Test
	public void shouldReturnSize3752AsSize() {
		Assert.assertTrue(3752==service.getAllStockCode().size());
	}
	
	@Test
	public void shoudReturnChg6don76isPchgis0don6() {
		PriceHistory begin = new PriceHistory();
		begin.setCode("600009");
		begin.setDate("19990104");
		begin.setTclose(8.88f);
		
		PriceHistory end = new PriceHistory();
		end.setCode("600009");
		end.setDate("19990129");
		end.setTclose(9.48f);
		
		Assert.assertTrue(0.6f==service.caculatePriceChange(begin, end).getPchg());
		Assert.assertTrue("6.76%".equals(service.caculatePriceChange(begin, end).getChg()));
	}
}
