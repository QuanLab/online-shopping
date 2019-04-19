package com.quanpv.service;

import com.quanpv.dao.CustomerRepository;
import com.quanpv.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public Iterable<Customer> getAll(){
        return repository.findAll();
    }

    public Customer getById(Integer id){
        return repository.findById(id).orElse(null);
    }

    public void save(Customer product){
        repository.save(product);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

}
