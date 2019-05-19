package com.lin.stock.dao.mappers.test;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
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
		
		history.setPk(1l);
		history.setChg(1f);
		history.setCode("600030");
		history.setDate("19900101");
		history.setHigh(1f);
		history.setLow(1f);
		history.setPchg(1f);
		history.setTclose(1f);
		history.setTopen(1f);
		history.setTurnoverrate(1f);
		history.setVaturnover(1f);
		history.setVoturnover(1);
		mapper.insert(history);
		
		PriceHistory result = new PriceHistory();
		result.setPk(1l);
		result = mapper.selectById(result);
		
		assertTrue(result.equals(history));
	}
}
