package com.lin.stock.utils.test;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StopWatch;

import com.lin.constant.StringConstant;
import com.lin.stock.constant.FileConstant;
import com.lin.stock.model.FileDownloadURL;
import com.lin.stock.utils.FileDownload;
import com.lin.stock.utils.StockCodeGenerator;

/**
 * @author Chen Lin
 * @date 2019-05-16
 */

public class DownloadEffectiveTest {

	@Before
	public void setup() {

	}

	@Test
	public void test() throws IOException {

		StopWatch sw = new StopWatch();
		
		sw.start("Method downloadWithNIO");
		for (int i = 611; i < 612; i++) {
			FileDownload.downloadWithNIO(createUrl(StockCodeGenerator.generate(i)),
					StringConstant.CSVFILES_PATH + StockCodeGenerator.generate(i) + FileConstant.CSV_FILE_SUFFIX);
		}
		sw.stop();
	
//		sw.start("Method downloadWithAsyncHttpClient");
//		for (int i = 611; i < 641; i++) {
//			FileDownload.downloadWithAsyncHttpClient(createUrl(StockCodeGenerator.generate(i)),
//					StringConstant.CSVFILES_PATH + StockCodeGenerator.generate(i)+ FILE_SUFFIX);
//		}
//		sw.stop();
//
//		sw.start("Method downloadFile");
//		for (int i = 611; i < 641; i++) {
//			FileDownload.downloadFile(createUrl(StockCodeGenerator.generate(i)), StringConstant.CSVFILES_PATH + stockCodeGenerate(i) + FILE_SUFFIX);
//		}
//		sw.stop();

		System.out.println(sw.prettyPrint());

	}

	private String createUrl(String stockCode) {

		return new FileDownloadURL.Builder("1", stockCode, "1990101", "20190513").voturnover().vaturnover().turnover()
				.topen().tclose().tcap().pchg().mcap().low().lclose().high().chg().build().getURL();

	}

}
