package com.lin.stock.utils.test;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.lin.stock.utils.FileDownload;

/**
 * @author Chen Lin
 * @date 2019-05-15
 */

public class FileDownloadTest {

	public static String FILE_URL = "http://quotes.money.163.com/service/chddata.html?code=1000611&start=19961008&end=20190513&fields=TCLOSE;HIGH;LOW;TOPEN;LCLOSE;CHG;PCHG;TURNOVER;VOTURNOVER;VATURNOVER;TCAP;MCAP";
	public static String FILE_NAME = "/Users/zdm/eclipse-workspace/StockProject/csvfiles/test.csv";
	
	@Test
	public void downloadWithAsyncHttpClientTest() {
		
		FileDownload.downloadWithAsyncHttpClient(FILE_URL, FILE_NAME);
		
		File file = new File(FILE_NAME);
		Assert.assertTrue("test.csv".equals(file.getName()));
		Assert.assertTrue(0 < file.getTotalSpace());
		System.out.println(file.getName());
		System.out.println(file.getTotalSpace());
	}
	
}
