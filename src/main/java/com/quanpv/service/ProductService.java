package com.quanpv.service;

import com.quanpv.dao.ProductRepository;
import com.quanpv.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product get(int id){
        return productRepository.findOne(id);
    }

    public void save(Product product){
        productRepository.save(product);
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }
}
