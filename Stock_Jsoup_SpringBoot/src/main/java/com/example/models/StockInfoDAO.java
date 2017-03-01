package com.example.models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

//tell Spring this class is data related transaction
//CrudRepository is a CRUD(create,read,update,delete) class provide by Spring
//<entity class name, primary key>


public interface StockInfoDAO extends CrudRepository<StockInfoBean, Integer>, Repository<StockInfoBean, Integer> {
	StockInfoBean findByStockID(int stock_id);

}
