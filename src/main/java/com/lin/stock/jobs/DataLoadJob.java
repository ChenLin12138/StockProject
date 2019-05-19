package com.lin.stock.jobs;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
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
	/*
	 * 	SHANGHAI1("0600"),
	 *	SHANGHAI2("0601"),
	 *	SHANGHAI3("0603"),
	 *	SHENZHENG("1000"),
	 *	SMALL("1002"),
	 *	SECOND("1300");
	 * */
	public void fullLoad() throws IOException, ParseException {
		fullDownloadFor1Market(StockMarket.SHANGHAI1.getValue());
		fullDownloadFor1Market(StockMarket.SHANGHAI2.getValue());
		fullDownloadFor1Market(StockMarket.SHANGHAI3.getValue());
		fullDownloadFor1Market(StockMarket.SHENZHENG.getValue());
		fullDownloadFor1Market(StockMarket.SMALL.getValue());
		fullDownloadFor1Market(StockMarket.SECOND.getValue());
	}
	
	/*
	 * 此下载的股票格式的描述
	 * rowData[0]:交易日期
	 * rowData[1]:股票代码
	 * rowData[2]:股票名称
	 * rowData[3]:收盘价
	 * rowData[4]:最高价
	 * rowData[5]:最低价
	 * rowData[6]:开盘价
	 * rowData[7]:昨日收盘
	 * rowData[8]:涨跌额
	 * rowData[9]:涨跌幅
	 * rowData[10]:换手率
	 * rowData[11]:成交量
	 * rowData[12]:成交金额
	 * */
	public void fullDownloadFor1Market(String marketCode) throws IOException {
		
		for(int stockCode = 16 ; stockCode < 999; stockCode++) {	
			FileDownloadURL url = new FileDownloadURL.Builder(marketCode, StockCodeGenerator.generate(stockCode), "19900101", "30000101")
					//这些变量的构造顺序决定了下载下来csv文件的顺序
					.tclose().high().low().topen().lclose().chg()
					.pchg().turnover().voturnover().vaturnover()
					.build();		
			if(FileDownload.downloadWithNIO(url.getURL(),getOutFileName(marketCode,stockCode))) {
				loadFile2DataBase(getOutFileName(marketCode,stockCode));
			}
		}		
	}
	
	public void loadFile2DataBase(String filePath) throws IOException {
		
		System.out.println("Start Loading Stock File: "+filePath);
		CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(filePath),"UTF-8"));
		//Skip The header
		reader.skip(1);
		Iterator<String[]> iter = reader.iterator();
		while(iter.hasNext()) {
			String[] rowData = iter.next();
			PriceHistory priceHistory = new PriceHistory();
			if(!isSuspendedTrading(rowData)) {
				try {
					priceHistory.setDate(rowData[0].replaceAll("-", ""));
					priceHistory.setCode(removeQuoteForStockCode(rowData[1]));
					priceHistory.setTclose(Float.parseFloat(rowData[3]));
					priceHistory.setHigh(Float.parseFloat(rowData[4]));
					priceHistory.setLow(Float.parseFloat(rowData[5]));
					priceHistory.setTopen(Float.parseFloat(rowData[6]));
					priceHistory.setChg(chgParse(rowData[8]));
					priceHistory.setPchg(pChgParse(rowData[9]));
					priceHistory.setTurnoverrate(Float.parseFloat(rowData[10]));
					priceHistory.setVoturnover(Integer.parseInt(rowData[11]));
					priceHistory.setVaturnover(Float.parseFloat(rowData[12]));
					mapper.insert(priceHistory);
				}catch(NumberFormatException e) {
					System.out.println("Date is : " + rowData[0] +" Stock is : "+ removeQuoteForStockCode(rowData[1]));
					e.printStackTrace();
				}catch(Exception e) {
					System.out.println("Date is : " + rowData[0] +" Stock is : "+ removeQuoteForStockCode(rowData[1]));
					e.printStackTrace();
				}
			}
		}
	}
	
	private String removeQuoteForStockCode(String rowString) {
		return rowString.substring(1);
	}
	
	private Float chgParse(String rowData) {
		if(isFirstTradDate(rowData)) {
			return 0f;
		}
		return Float.parseFloat(rowData);
	}
	
	private Float pChgParse(String rowData) {
		if(isFirstTradDate(rowData)) {
			return 0f;
		}
		return Float.parseFloat(rowData);
	}
	
	private boolean isFirstTradDate(String rowData) {
		if("None".equals(rowData)) {
			return true;
		}
		return false;
	}
	
	private boolean isSuspendedTrading(String[] rowData) {
		
		if("0".equals(rowData[11]) || "0".equals(rowData[12]) ) {
			return true;
		}

		return false;
	}
	
	private String getOutFileName(String marketCode, int stockCode){
		return FileConstant.CSVFILE_PATH+marketCode.substring(1)+StockCodeGenerator.generate(stockCode)+FileConstant.CSV_FILE_SUFFIX;
	}
	
	
}
