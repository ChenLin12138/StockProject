package com.lin.stock.dao.mappers.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.lin.stock.config.DataSourceConfig;
import com.lin.stock.config.MybatisMapperConfig;
import com.lin.stock.dao.mappers.PriceHistoryMapper;
import com.lin.stock.model.PriceHistory;

/**
 * @author Chen Lin
 * @date 2019-05-18
 */
@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = { DataSourceConfig.class, MybatisMapperConfig.class })
public class PriceHistoryMapperTest {

	@Autowired
	PriceHistoryMapper mapper;
	
	@Test
	public void shouldReturnAPriceHistory() {
		PriceHistory history = new PriceHistory();
		
		history.setPk(1000000000l);
		history.setChg(1f);
		history.setCode("600030");
		history.setDate("19900101");
		history.setHigh(1f);
		history.setLow(1f);
		history.setPchg(1f);
		history.setTclose(1f);
		history.setTopen(1f);
		history.setTurnoverrate(1f);
		history.setVaturnover(1d);
		history.setVoturnover(1l);
		mapper.insert(history);	
		PriceHistory result = new PriceHistory();
		result.setPk(1000000000l);
		result = mapper.selectById(result);	
		assertTrue(result.equals(history));
	}
	
	@Test
	public void shouldBeTrueWhenSelectByStockCodeAndDate() {
		PriceHistory history = new PriceHistory();
		history.setDate("20091210");
		history.setCode("600001");
		PriceHistory result = mapper.selectByStockCodeAndDate(history);
		assertTrue("600001".equals(result.getCode()));
		assertTrue("20091210".equals(result.getDate()));
		assertTrue(44208092l == result.getVoturnover());
	}
	
	@Test
	public void shouldBeNullWhenSelectByStockCodeAndDateIs19800101() {
		PriceHistory history = new PriceHistory();
		history.setDate("19800101");
		history.setCode("600001");
		PriceHistory result = mapper.selectByStockCodeAndDate(history);	
		Assert.assertNull(result);
	}
	
	@Test
	public void shouldReturn22RecordsWhenStockCode600001Between20091101And20091201() {
		List<PriceHistory> results = mapper.selectByStockCodeAndDateRange("600001", "20091101", "20091201");
		Assert.assertTrue(22==results.size());
	}
	
	@Test
	public void should0RecordsWhenStockCode600001Between20100101And2010115() {
		List<PriceHistory> results = mapper.selectByStockCodeAndDateRange("600001", "20100101", "2010115");
		Assert.assertTrue(0==results.size());
	}
}
