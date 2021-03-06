package com.lin.stock.model;

/**
 * @author Chen Lin
 * @date 2019-10-03
 */

public class Trade {

	public static final String EMPTY = "E";
	public static final String HOLDING = "H";
	
	private float buyPrice;
	private float sellPrice;
	private String buyDate;
	private String sellDate;
	private String status;
	private String stockCode;

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public float getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(float buyPrice) {
		this.buyPrice = buyPrice;
	}

	public float getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(float sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(String buyDate) {
		this.buyDate = buyDate;
	}

	public float getChg() {
		return (float)Math.round((sellPrice-buyPrice)*100)/100;
	}

	public float getRate() {
		
		return (float)Math.round(((sellPrice-buyPrice)/buyPrice)*100)/100;
	}

	public String getSellDate() {
		return sellDate;
	}

	public void setSellDate(String sellDate) {
		this.sellDate = sellDate;
	}
	
	@Override
	public String toString() {	
		return "StockCode:"+this.stockCode+",BuyDate:"+this.buyDate+",SellDate:"+this.sellDate+",BuyPrice:"+this.buyPrice+",SellPrice:"+this.sellPrice+",Chg:"+getChg()+",Rate:"+getRate()*100+"%";
		
	}
	
	public String getReportLayout() {
		return this.stockCode+","+this.buyDate+","+this.sellDate+","+this.buyPrice+","+this.sellPrice+","+getChg()+","+getRate()*100+"%";
	}

}
