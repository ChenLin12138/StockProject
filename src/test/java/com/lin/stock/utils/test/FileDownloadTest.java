package com.lin.stock.utils.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.lin.constant.StringConstant;
import com.lin.stock.utils.FileDownload;

/**
 * @author Chen Lin
 * @date 2019-05-15
 */

public class FileDownloadTest {

	public static String FILE_URL = "http://quotes.money.163.com/service/chddata.html?code=1000611&start=19961008&end=20190513&fields=TCLOSE;HIGH;LOW;TOPEN;LCLOSE;CHG;PCHG;TURNOVER;VOTURNOVER;VATURNOVER;TCAP;MCAP";
	public static String FILE_NAME = StringConstant.CSVFILES_PATH+"test.csv";
	public static String NO_CONTENT_FILE_URL = "http://quotes.money.163.com/service/chddata.html?code=1000624&start=19961008&end=20190513&fields=TCLOSE;HIGH;LOW;TOPEN;LCLOSE;CHG;PCHG;TURNOVER;VOTURNOVER;VATURNOVER;TCAP;MCAP";
	public static String NO_CONTENT_FILE_NAME =	StringConstant.CSVFILES_PATH+"nocontent.csv";	
	
	@Test
	public void returnFileSizeGT0() {
		FileDownload.downloadWithNIO(FILE_URL, FILE_NAME);
		File file = new File(FILE_NAME);
		Assert.assertTrue("test.csv".equals(file.getName()));
		Assert.assertTrue(0 < file.getTotalSpace());
		System.out.println(file.getName());
		System.out.println(file.getTotalSpace());
	}
	
	@Test 
	public void noContentFileShouldNotExists() throws IOException {
		FileDownload.downloadWithNIO(NO_CONTENT_FILE_URL, NO_CONTENT_FILE_NAME);
		File file = new File(NO_CONTENT_FILE_NAME);
		assertTrue(!file.exists());
	}
	
}
