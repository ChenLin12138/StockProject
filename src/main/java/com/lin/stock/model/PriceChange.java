package com.lin.stock.model;

import java.math.BigDecimal;

import com.lin.stock.exceptions.InvaildStockCodeException;

/**
 * @author Chen Lin
 * @date 2019-06-01
 */

public class PriceChange {
	
	private PriceHistory beginPriceHistory;
	private PriceHistory endPriceHistory;
	private String code;
	private String beginDate;
	private String endDate;
	//表示%的涨跌幅
	private String chg;
	//表示价格变化
	private Float pchg;
	
	public PriceChange(PriceHistory begin, PriceHistory end) {
		if(!begin.getCode().equals(end.getCode())) {
			throw new InvaildStockCodeException("Begin and end Stock Code is not same, begin code is :"+begin.getCode()+", end code is :"+end.getCode());
		}
		this.beginPriceHistory = begin;
		this.endPriceHistory = end;
		this.code = begin.getCode();
		this.beginDate = begin.getDate();
		this.endDate = end.getDate();
		this.pchg  = calPchg(begin,end);
		this.chg = calChg(begin,end);
		
	}
	
	public String getCode() {
		return code;
	}
	
	public String getBeginDate() {
		return beginDate;
	}
	
	public String getEndDate() {
		return endDate;
	}
	
	public Float getPchg() {
		return pchg;
	}
	
	public String getChg() {
		return chg;
	}
	
	private Float calPchg(PriceHistory begin, PriceHistory end) {
		BigDecimal  bigDecimal = new BigDecimal(end.getTclose() - begin.getTclose());
		Float result = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		return result;
	}
	
	private String calChg(PriceHistory begin, PriceHistory end) {
		BigDecimal  bigDecimal = new BigDecimal(calPchg(begin,end)* 100/begin.getTclose());
		String result = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
		return result+"%";
	}
}
