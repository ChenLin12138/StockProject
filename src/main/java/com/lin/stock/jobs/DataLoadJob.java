package com.lin.stock.jobs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lin.stock.constant.FileConstant;
import com.lin.stock.constant.StockMarket;
import com.lin.stock.dao.mappers.PriceHistoryMapper;
import com.lin.stock.model.FileDownloadURL;
import com.lin.stock.model.PriceHistory;
import com.lin.stock.utils.FileDownload;
import com.lin.stock.utils.StockCodeGenerator;
import com.opencsv.CSVReader;

/**
 * @author Chen Lin
 * @date 2019-05-18
 */

@Component
public class DataLoadJob {
	
	@Autowired
	private PriceHistoryMapper mapper;
	
	public void fullLoad() throws IOException, ParseException {
		
		fullDownloadFor1Market(StockMarket.SHANGHAI.getValue());
//		fullDownloadFor1Market(StockMarket.SHENZHENG.getValue());
	}
	
	public void fullDownloadFor1Market(String marketCode) throws ParseException, IOException {
		
		for(int stockCode = 600050 ; stockCode <= 600051; stockCode++) {	
			FileDownloadURL url = new FileDownloadURL.Builder(marketCode, StockCodeGenerator.generate(stockCode), "19900101", "30000101")
					//这些变量的构造顺序决定了下载下来csv文件的顺序
					.tclose().high().low().topen().lclose().chg()
					.pchg().turnover().voturnover().vaturnover()
					.build();		
			FileDownload.downloadWithNIO(url.getURL(),getOutFileName(stockCode));		
			CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(getOutFileName(stockCode)),"UTF-8"));
			//Skip The header
			reader.skip(1);
			Iterator<String[]> iter = reader.iterator();
			while(iter.hasNext()) {
				String[] rowData = iter.next();
				PriceHistory priceHistory = new PriceHistory();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				priceHistory.setDate(sdf.parse(rowData[0]));
				priceHistory.setCode(removeQuoteForStockCode(rowData[1]));
				priceHistory.setTclose(Float.parseFloat(rowData[3]));
				priceHistory.setHigh(Float.parseFloat(rowData[4]));
				priceHistory.setLow(Float.parseFloat(rowData[5]));
				priceHistory.setTopen(Float.parseFloat(rowData[6]));
				priceHistory.setChg(Float.parseFloat(rowData[8]));
				priceHistory.setPchg(Float.parseFloat(rowData[9]));
				priceHistory.setTurnoverrate(Float.parseFloat(rowData[10]));
				priceHistory.setVoturnover(Integer.parseInt(rowData[11]));
				priceHistory.setVaturnover(Float.parseFloat(rowData[12]));
				mapper.insert(priceHistory);
			}
		}		
	}
	
	private String removeQuoteForStockCode(String rowString) {
		return rowString.substring(1);
	}
	
	private String getOutFileName(int stockCode){
		return FileConstant.CSVFILE_PATH+StockCodeGenerator.generate(stockCode)+FileConstant.CSV_FILE_SUFFIX;
	}
	
//	public static void main(String[] str) throws IOException {
//		DataLoadJob job = new DataLoadJob();
//		job.fullLoad();
//	}
	
}
