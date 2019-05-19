package com.lin.stock.constant;

/**
 * @author Chen Lin
 * @date 2019-05-18
 */

public enum StockMarket {
	SHANGHAI("0"),
	SHENZHENG("1");
	
	private final String value;
	
	StockMarket(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
