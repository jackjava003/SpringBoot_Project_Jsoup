package com.example.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.models.Stock_infoBean;
import com.example.models.Stock_infoDAO;

@Component
public class Stock_ServiceImpl implements Stock_Service {

	@Autowired
	Stock_infoDAO infoDAO;
	
	@Override
	public long recordCount(){
		return infoDAO.count();
	}
	
	//檢查資料庫是否有資料
	@Override
	public boolean checkExist(int stock_id) {
		Stock_infoBean info = infoDAO.findByStockID(stock_id);
		if (info == null) {
			return false;
		} else {
			return true;
		}

	}
	
	//新增資料到資料庫
	@Override
	public Stock_infoBean addStock(Stock_infoBean info) {
		// if not exists	
		if (info!=null && !checkExist(info.getStockID())) {
			infoDAO.save(info);
			return info;
		}

		return null;

	}
	
	//資料庫有資料 則從資料庫抓取資料
	@Override
	public Stock_infoBean getStock(int stock_id){
		Stock_infoBean sib = infoDAO.findByStockID(stock_id);
		//System.out.println(sib.getStock_monthBeans().size());
		return sib;
		
	}

}
