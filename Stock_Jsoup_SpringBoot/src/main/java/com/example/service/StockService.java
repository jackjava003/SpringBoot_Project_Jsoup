package com.example.service;

import org.springframework.stereotype.Component;

import com.example.models.StockInfoBean;

//@Component tell Spring this is a object, to do Di injection while application is starting
@Component

public interface StockService {
	
	public long recordCount();
	
	public boolean checkExist(int stockId);

	public StockInfoBean addStock(StockInfoBean info);
	
	public StockInfoBean getStock(int stockId);
	

}
