package com.lin.stock.service.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.lin.stock.config.DataSourceConfig;
import com.lin.stock.config.MybatisMapperConfig;
import com.lin.stock.exceptions.InSufficientDateException;
import com.lin.stock.service.MovingAverageService;

/**
 * @author Chen Lin
 * @date 2019-10-02
 */
@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = { DataSourceConfig.class, MybatisMapperConfig.class })
@ComponentScan(value="com.lin.stock.*")
public class MovingAverageServiceTest {
	
	@Autowired
	private MovingAverageService service ;
	
	@Test
	public void shouldReturn7don96WhenDateIs19890101CodeIs600001AsMA5() throws InSufficientDateException {
		System.out.println(service.getAverage("19980213", 5, "600001"));
		Assert.assertTrue(7.96f==service.getAverage("19980213", 5, "600001"));
	}
}
