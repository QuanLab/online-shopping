package com.quanpv.service;

import com.quanpv.dao.ProductRepository;
import com.quanpv.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Iterable<Product> getAll(int offset, int limit){
        Pageable pageable = new PageRequest(offset, limit);
        return repository.findAll(pageable);
    }

    public Product getById(Integer id){
        return repository.findById(id).orElse(null);
    }

    public Iterable<Product> getByCategoryId(int id){
        return repository.findByCategory_Id(id);
    }

    public Iterable<Product> getByNameContaining(String name){
        return repository.findByNameContaining(name);
    }

    public Iterable<Product> getTop3ByCategory_Id(int id){
        return repository.findTop3ByCategory_Id(id);
    }

    public void save(Product product){
        repository.save(product);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    public Iterable<Product> getLastProduct() {
        return repository.findTop4ByOrderByIdDesc();
    }

}
