package com.lin.stock.jobs;

import java.io.File;
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
	
	private static final  String beginDate = "20190521";
	private static final  String endDate = "20190930";
	
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
		loadFiles2Database();
		
	}
	
	public void loadFiles2Database() throws IOException{
		
		for(int stockCode = 1 ; stockCode < 999; stockCode++) {	
			loadFile2DataBase(getOutFileName(StockMarket.SHANGHAI1.getValue(),stockCode));
			loadFile2DataBase(getOutFileName(StockMarket.SHANGHAI2.getValue(),stockCode));
			loadFile2DataBase(getOutFileName(StockMarket.SHANGHAI3.getValue(),stockCode));
			loadFile2DataBase(getOutFileName(StockMarket.SHENZHENG.getValue(),stockCode));
			loadFile2DataBase(getOutFileName(StockMarket.SMALL.getValue(),stockCode));
			loadFile2DataBase(getOutFileName(StockMarket.SECOND.getValue(),stockCode));
		}
	
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
		
		for(int stockCode = 1 ; stockCode < 999; stockCode++) {	
			FileDownloadURL url = new FileDownloadURL.Builder(marketCode, StockCodeGenerator.generate(stockCode), beginDate, endDate)
					//这些变量的构造顺序决定了下载下来csv文件的顺序
					.tclose().high().low().topen().lclose().chg()
					.pchg().turnover().voturnover().vaturnover()
					.build();		
			FileDownload.downloadWithNIO(url.getURL(),getOutFileName(marketCode,stockCode));
			
//			if(FileDownload.downloadWithNIO(url.getURL(),getOutFileName(marketCode,stockCode))) {
//				loadFile2DataBase(getOutFileName(marketCode,stockCode));
//			}
		}		
	}
	
	public void loadFile2DataBase(String filePath) throws IOException {	
		
		if(new File(filePath).exists()) {
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
						priceHistory.setChg(floatParse(rowData[8]));
						priceHistory.setPchg(floatParse(rowData[9]));
						priceHistory.setTurnoverrate(floatParse(rowData[10]));
						priceHistory.setVoturnover(longParse(rowData[11]));
						priceHistory.setVaturnover(doubleParse(rowData[12]));
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
			reader.close();
		}

	}
	
	private String removeQuoteForStockCode(String rowString) {
		return rowString.substring(1);
	}
	
	private Float floatParse(String rowData) {
		if(isDataNoneDate(rowData)) {
			return 0f;
		}
		return Float.parseFloat(rowData);
	}
	
	private Long longParse(String rowData) {
		if(isDataNoneDate(rowData)) {
			return 0l;
		}
		return Long.parseLong(rowData);
	}
	
	private Double doubleParse(String rowData) {
		if(isDataNoneDate(rowData)) {
			return 0d;
		}
		return Double.parseDouble(rowData);
	}

	private boolean isDataNoneDate(String rowData) {
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
