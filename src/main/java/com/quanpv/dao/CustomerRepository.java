package com.quanpv.dao;

import com.quanpv.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CustomerRepository extends CrudRepository<Customer, Integer>{

}
