package com.lin.stock.service.test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lin.stock.service.BusinessDateService;

/**
 * @author Chen Lin
 * @date 2019-10-03
 */

public class BusinessDateServiceTest extends BaseServiceTest {

	@Autowired
	private BusinessDateService service ;
	
	@Test
	public void shouldReturn19980217AsBusinessDate() {
		Assert.assertTrue("19980217".equals(service.getPreviousBusinessDate("600001", "19980218")));
	}
}
