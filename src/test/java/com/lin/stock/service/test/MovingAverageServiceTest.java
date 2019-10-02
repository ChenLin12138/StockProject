package com.lin.stock.service.test;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import com.lin.stock.exceptions.InValidDateException;
import com.lin.stock.service.MovingAverageService;

/**
 * @author Chen Lin
 * @date 2019-10-02
 */

public class MovingAverageServiceTest extends BaseServiceTest{	
	
	@Autowired
	private MovingAverageService service ;
	
	@Test
	public void shouldReturn7don96WhenDateIs19890101CodeIs600001AsMA5() throws InValidDateException, ParseException {
		Assert.assertTrue(7.96f==service.getAverage("19980213", 5, "600001"));
	}
	
	
	@Test
	public void shouldInValidDateExceptionWhenDateIs19890210CodeIs600001AsMA5() throws InValidDateException, ParseException {
		expectedEx.expect(InValidDateException.class);
		service.getAverage("19890210", 5, "600001");
		expectedEx.expectMessage("Invalid BusinessDate!");
	}
	
	
	@Test
	public void shouldInValidDateExceptionWhenDateIs19980210CodeIs600001AsMA5() throws InValidDateException, ParseException {
		expectedEx.expect(InValidDateException.class);
		service.getAverage("19980210", 5, "600001");
		expectedEx.expectMessage("Reach the 1st business date!");
	}
}
