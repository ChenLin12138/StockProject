package com.lin.stock.jobs;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
	
	public void fullDownloadFor1Market(String marketCode) throws IOException {
		
		for(int stockCode = 600051 ; stockCode < 600051; stockCode++) {	
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
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			while(iter.hasNext()) {
				String[] rowData = iter.next();
				PriceHistory priceHistory = new PriceHistory();
				if(!isSuspendedTrading(rowData)) {
					try {
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
					}catch(ParseException e) {
						System.out.println("Date is : " + rowData[0] +"Stock is : "+ removeQuoteForStockCode(rowData[10]));
						e.printStackTrace();	
					}catch(NumberFormatException e) {
						System.out.println("Date is : " + rowData[0] +"Stock is : "+ removeQuoteForStockCode(rowData[10]));
						e.printStackTrace();
					}catch(Exception e) {
						System.out.println("Date is : " + rowData[0] +"Stock is : "+ removeQuoteForStockCode(rowData[10]));
						e.printStackTrace();
					}
				}
			}
		}		
	}
	
	private String removeQuoteForStockCode(String rowString) {
		return rowString.substring(1);
	}
	
	private boolean isSuspendedTrading(String[] rowData) {
		
		if("0".equals(rowData[11]) || "0".equals(rowData[12]) ) {
			return true;
		}

		return false;
	}
	
	private String getOutFileName(int stockCode){
		return FileConstant.CSVFILE_PATH+StockCodeGenerator.generate(stockCode)+FileConstant.CSV_FILE_SUFFIX;
	}
	
//	public static void main(String[] str) throws IOException {
//		DataLoadJob job = new DataLoadJob();
//		job.fullLoad();
//	}
	
}
