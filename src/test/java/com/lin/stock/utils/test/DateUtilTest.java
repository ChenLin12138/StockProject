package com.lin.stock.utils.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.lin.stock.utils.DateUtil;

/**
 * @author Chen Lin
 * @date 2019-05-28
 */

public class DateUtilTest {
	
	
	@Test
	public void shouldReturnTrueWhenInput2019AsYear04AsMonth() {
		
		assertTrue("20190430".equals(DateUtil.getLastDateOfMonth(2019, 04)));
		
	}
	
	@Test
	public void shouldReturnTrueWhenInput2019AsYear02AsMonth() {
		
		assertTrue("20190228".equals(DateUtil.getLastDateOfMonth(2019, 02)));
	}
	
	
	@Test
	public void shouldReturnTrueWhenInput2020AsYear02AsMonth() {
		
		assertTrue("20200229".equals(DateUtil.getLastDateOfMonth(2020, 02)));
	}
	
	@Test
	public void previousDateShouldBe20190330WhenInput20190331() {
		assertTrue("20190330".equals(DateUtil.getPrevDate(2019,3,31)));
	}
	
	@Test
	public void previousDateShouldBe20190330WhenInputString20190331() {
		assertTrue("20190330".equals(DateUtil.getPrevDate("20190331")));
	}
	
	@Test
	public void previousDateShouldBe20190331WhenInput20190401() {
		assertTrue("20190331".equals(DateUtil.getPrevDate(2019,4, 01)));
	}
	
	@Test
	public void previousDateShouldBe20190331WhenInputString20190401() {
		assertTrue("20190331".equals(DateUtil.getPrevDate("20190401")));
	}
	
	@Test
	public void nextDateShouldBe20190301WhenInput20190228() {
		assertTrue("20190301".equals(DateUtil.getNextDate(2019,2,28)));
	}
	
	@Test
	public void nextDateShouldBe20190301WhenInputString20190228() {
		assertTrue("20190301".equals(DateUtil.getNextDate("20190228")));
	}
	
	
	
}
