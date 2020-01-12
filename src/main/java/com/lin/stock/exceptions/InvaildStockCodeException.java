package com.lin.stock.exceptions;

/**
 * @author Chen Lin
 * @date 2020-01-12
 */

public class InvaildStockCodeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 841120466994067746L;
	private int value;
	
    public InvaildStockCodeException() {
        super();
    }
    public InvaildStockCodeException(String msg,int value) {
        super(msg);
        this.value=value;
    }
    
    public InvaildStockCodeException(String msg) {
        super(msg);
    }
    
    public int getValue() {
        return value;
    }
}
