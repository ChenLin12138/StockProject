package com.lin.stock.jobs.app;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import com.lin.stock.config.DataSourceConfig;
import com.lin.stock.config.MybatisMapperConfig;
import com.lin.stock.constant.FileConstant;
import com.lin.stock.jobs.DataLoadJob;

/**
 * @author Chen Lin
 * @date 2019-05-18
 */

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = { DataSourceConfig.class, MybatisMapperConfig.class })
@ComponentScan(value="com.lin.stock.*")
public class DataLoadApp {

	
	@Autowired
	DataLoadJob job;
	
	@Test
	@Rollback(false)
	public void fullDataLoad() throws IOException, ParseException {
				
		StopWatch sw = new StopWatch();	
		sw.start("Method downloadWithNIO");
		job.fullLoad();
		sw.stop();
		System.out.println(sw.prettyPrint());

	}
	
	@Test
	@Rollback(false)
	public void loadFile2DataBaseTest() throws IOException {
		job.loadFile2DataBase(FileConstant.CSVFILE_PATH+"300739"+FileConstant.CSV_FILE_SUFFIX);
	}
	
	@Test
	@Rollback(false)
	public void loadAllFiles2Database() throws IOException {
		job.fullLoadFromFile();
	}
}
