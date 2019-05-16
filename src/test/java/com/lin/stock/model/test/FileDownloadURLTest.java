package com.lin.stock.model.test;

import org.junit.Assert;
import org.junit.Test;

import com.lin.stock.model.FileDownloadURL;

/**
 * @author Chen Lin
 * @date 2019-05-16
 */

public class FileDownloadURLTest {
	
	public static String url = "http://quotes.money.163.com/service/chddata.html?code=1000611&start=19961008&end=20190513&fields=TCLOSE;HIGH;LOW;TOPEN;LCLOSE;CHG;PCHG;TURNOVER;VOTURNOVER;VATURNOVER;TCAP;MCAP";
	
	@Test
	public void testGetURL() {
		FileDownloadURL fd = 
				new FileDownloadURL.Builder("1","000611","19961006","20190513")
				.voturnover().vaturnover().turnover().topen()
				.tclose().tcap().pchg().mcap()
				.low().lclose().high().chg()
				.build();
		System.out.println(fd.getURL());	
	}
}
