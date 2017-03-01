package com.example.models;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
@Embeddable
public class QuartCompositeKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1751243164523052179L;
	private int stockID;
	private int quarterly;

	public QuartCompositeKey() {

	}

	public int getStockID() {
		return stockID;
	}

	public void setStockID(int stockID) {
		this.stockID = stockID;
	}

	public int getQuarterly() {
		return quarterly;
	}

	public void setQuarterly(int quarterly) {
		this.quarterly = quarterly;
	}
	
	

}
