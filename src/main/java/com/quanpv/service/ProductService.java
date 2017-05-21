package com.quanpv.service;

import com.quanpv.dao.ProductRepository;
import com.quanpv.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Iterable<Product> getAll(){
        return repository.findAll();
    }

    public Product getById(int id){
        return repository.findOne(id);
    }

    public Iterable<Product> getByCategoryId(int id){
        return repository.findByCategory_Id(id);
    }

    public void save(Product product){
        repository.save(product);
    }

    public void delete(int id) {
        repository.delete(id);
    }

}
