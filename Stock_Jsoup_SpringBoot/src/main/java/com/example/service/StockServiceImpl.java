package com.example.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.models.StockInfoBean;
import com.example.models.StockInfoDAO;
@Transactional
@Component
public class StockServiceImpl implements StockService {

	@Autowired
	StockInfoDAO infoDAO;
	
	@Override
	public long recordCount(){
		return infoDAO.count();
	}
	
	//檢查資料庫是否有資料
	@Override
	public boolean checkExist(int stockId) {
		StockInfoBean info = infoDAO.findByStockID(stockId);
		if (info == null) {
			return false;
		} else {
			return true;
		}

	}
	
	//新增資料到資料庫
	@Override
	public StockInfoBean addStock(StockInfoBean info) {
		// if not exists	
		if (info!=null && !checkExist(info.getStockID())) {
			infoDAO.save(info);
			return info;
		}

		return null;

	}
	
	//資料庫有資料 則從資料庫抓取資料
	@Override
	public StockInfoBean getStock(int stockId){
		StockInfoBean sib = infoDAO.findByStockID(stockId);
		//System.out.println(sib.getStock_monthBeans().size());
		return sib;
		
	}

}
