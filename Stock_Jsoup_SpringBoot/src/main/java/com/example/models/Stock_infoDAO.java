package com.example.models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

//tell Spring this class is data related transaction
//CrudRepository is a CRUD(create,read,update,delete) class provide by Spring
//<entity class name, primary key>

@Transactional
public interface Stock_infoDAO extends CrudRepository<Stock_infoBean, Integer>, Repository<Stock_infoBean, Integer> {
	Stock_infoBean findByStockID(int stock_id);

}
