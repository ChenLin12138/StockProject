package com.lin.stock.dao.mappers.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lin.stock.dao.mappers.StockMapper;
import com.lin.stock.model.Stock;

/**
 * @author Chen Lin
 * @date 2019-06-03
 */

public class StockMapperTest extends BaseMybatisMappterTest{

	@Autowired
	private StockMapper mapper;
	
	@Test
	public void shouldReturnListSizeIs3707() {
		List<Stock> stocks = mapper.selectAll();
		Assert.assertTrue(3752 == stocks.size());
	}
}
