package com.example.models;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
@Embeddable
public class MonthCompositeKey implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2727205674542665692L;
	private int stockID;
    private String month;
    
    public MonthCompositeKey(){
    	
    }
    
	public int getStockID() {
		return stockID;
	}
	public void setStockID(int stockID) {
		this.stockID = stockID;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
    
    
}
