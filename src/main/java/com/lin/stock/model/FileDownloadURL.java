package com.lin.stock.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chen Lin
 * @date 2019-05-16
 */

public class FileDownloadURL {

	private final String DEFAULT_SITE = "http://quotes.money.163.com/service/chddata.html";
	private final String market_code;
	private final String stock_code;
	private final String start;
	private final String end;
	private final List<String> fields = new ArrayList<String>();
	
	//使用Java嵌套类的机制构造builder
	public static class Builder {
	// required parameters
	private final String market_code;
	private final String stock_code;
	private final String start;
	private final String end;
	// optional parameters
	private final List<String> fields = new ArrayList<String>();
	//构造器必填选项
	public Builder(String market_code, String stock_code, String start, String end) {
		this.market_code = market_code;
		this.stock_code = stock_code;
		this.start = start;
		this.end = end;
	}
	//构造器选填选项，返回Builder本身
	public Builder tclose() {
		fields.add("TCLOSE");
		return this;
	}
	
	public Builder high() {
		fields.add("HIGH");
		return this;
	}
	
	public Builder low() {
		fields.add("LOW");
		return this;
	}
	
	public Builder topen() {
		fields.add("TOPEN");
		return this;
	}
	
	public Builder lclose() {
		fields.add("LCLOSE");
		return this;
	}
	
	public Builder chg() {
		fields.add("CHG");
		return this;
	}
	
	public Builder pchg() {
		fields.add("PCHG");
		return this;
	}
	
	public Builder turnover() {
		fields.add("TURNOVER");
		return this;
	}
	
	public Builder voturnover() {
		fields.add("VOTURNOVER");
		return this;
	}
	
	public Builder vaturnover() {
		fields.add("VATURNOVER");
		return this;
	}
	
	public Builder tcap() {
		fields.add("TCAP");
		return this;
	}
	
	public Builder mcap() {
		fields.add("MCAP");
		return this;
	}
	
	//通过嵌套类的build方法构造外部类类型
	public FileDownloadURL build() {
		return new FileDownloadURL(this);
		}
	}
	//私有化外部类的构造方法
	private FileDownloadURL(Builder builder) {
		this.market_code = builder.market_code;
		this.stock_code = builder.stock_code;
		this.start = builder.start;
		this.end = builder.end;
		this.fields.addAll(builder.fields);
	}
	
	public String getURL() {
		
		return DEFAULT_SITE+"?"+"code="+this.market_code+this.stock_code
				+"&"+"start="+start
				+"&"+"end="+end
				+"&"+"fields="+praseFieldsToUrl();
	}
	
	private String praseFieldsToUrl() {
		StringBuffer sb = new StringBuffer();
		for(String field : this.fields) {
			sb.append(field)
				.append(";");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
}
