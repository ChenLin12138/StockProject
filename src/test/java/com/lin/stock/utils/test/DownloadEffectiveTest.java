package com.lin.stock.utils.test;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StopWatch;

import com.lin.stock.model.FileDownloadURL;
import com.lin.stock.utils.FileDownload;

/**
 * @author Chen Lin
 * @date 2019-05-16
 */

public class DownloadEffectiveTest {

	public static String FILE_NAME = "/Users/zdm/eclipse-workspace/StockProject/csvfiles/";
	public static String FILE_SUFFIX = ".csv";

	@Before
	public void setup() {

	}

	@Test
	public void test() throws IOException {

		StopWatch sw = new StopWatch();
		
//		sw.start("Method downloadWithNIO");
//		for (int i = 611; i < 641; i++) {
//			FileDownload.downloadWithNIO(createUrl(stockCodeGenerate(i)),
//					FILE_NAME + stockCodeGenerate(i) + FILE_SUFFIX);
//		}
//		sw.stop();
		
		
		sw.start("Method downloadWithAsyncHttpClient");
		for (int i = 611; i < 641; i++) {
			FileDownload.downloadWithAsyncHttpClient(createUrl(stockCodeGenerate(i)),
					FILE_NAME + stockCodeGenerate(i) + FILE_SUFFIX);
		}
		sw.stop();
//
//		sw.start("Method downloadFile");
//		for (int i = 611; i < 641; i++) {
//			FileDownload.downloadFile(createUrl(stockCodeGenerate(i)), FILE_NAME + stockCodeGenerate(i) + FILE_SUFFIX);
//		}
//		sw.stop();

		System.out.println(sw.prettyPrint());

	}

	private String createUrl(String stockCode) {

		return new FileDownloadURL.Builder("1", stockCode, "1990101", "20190513").voturnover().vaturnover().turnover()
				.topen().tclose().tcap().pchg().mcap().low().lclose().high().chg().build().getURL();

	}

	private String stockCodeGenerate(int stockCode) {
		return String.format("%06d", stockCode);
	}

}
