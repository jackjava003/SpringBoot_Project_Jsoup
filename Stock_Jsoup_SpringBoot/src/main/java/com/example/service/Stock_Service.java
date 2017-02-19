package com.example.service;

import org.springframework.stereotype.Component;

import com.example.models.Stock_infoBean;

//@Component tell Spring this is a object, to do Di injection while application is starting
@Component
public interface Stock_Service {
	
	public long recordCount();
	
	public boolean checkExist(int stock_id);

	public Stock_infoBean addStock(Stock_infoBean info);
	
	public Stock_infoBean getStock(int stock_id);
	

}
