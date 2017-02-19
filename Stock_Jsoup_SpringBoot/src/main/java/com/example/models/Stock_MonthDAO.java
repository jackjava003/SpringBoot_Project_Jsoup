package com.example.models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

//tell Spring this class is data related transaction
//CrudRepository is a CRUD(create,read,update,delete) class provide by Spring
//<entity class name, primary key>

@Transactional
public interface Stock_MonthDAO extends CrudRepository<Stock_monthBean, Integer>, Repository<Stock_monthBean, Integer> {

}
