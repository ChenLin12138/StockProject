package com.lin.stock.constant;

/**
 * @author Chen Lin
 * @date 2019-05-18
 */


/*
 * 股票代码规则：
 * 网易下载里面有7位0代码上海，1代表深证和创业板
 * 上海的A股票开头字符分别是：
 * 0600xxx
 * 0601xxx
 * 0603xxx
 * 深证A股票开头为：
 * 1000xxx
 * 中小板为：
 * 1002xxx
 * 创业板
 * 1300xxx
 * */
public enum StockMarket {
	SHANGHAI1("0600"),
	SHANGHAI2("0601"),
	SHANGHAI3("0603"),
	SHENZHENG("1000"),
	SMALL("1002"),
	SECOND("1300");
	
	private final String value;
	
	StockMarket(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
