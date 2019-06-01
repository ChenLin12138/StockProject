package com.lin.stock.model;

/**
 * @author Chen Lin
 * @date 2019-06-01
 */

public class PriceChange {
	
	private String code;
	private String beginDate;
	private String endDate;
	private Float chg;
	private Float pchg;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Float getChg() {
		return chg;
	}
	public void setChg(Float chg) {
		this.chg = chg;
	}
	public Float getPchg() {
		return pchg;
	}
	public void setPchg(Float pchg) {
		this.pchg = pchg;
	}
	
	
}
