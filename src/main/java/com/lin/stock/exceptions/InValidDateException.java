package com.lin.stock.exceptions;

/**
 * @author Chen Lin
 * @date 2019-10-02
 */

public class InValidDateException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -178388106080726627L;
	private int value;
    public InValidDateException() {
        super();
    }
    public InValidDateException(String msg,int value) {
        super(msg);
        this.value=value;
    }
    
    public InValidDateException(String msg) {
        super(msg);
    }
    
    public int getValue() {
        return value;
    }
}
