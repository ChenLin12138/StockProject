package com.lin.stock.utils;

/**
 * @author Chen Lin
 * @date 2019-05-18
 */

public class StockCodeGenerator {

	public static String generate(int stockCode) {
		return String.format("%03d", stockCode);
	}
	
}
