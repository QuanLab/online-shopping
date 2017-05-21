package com.quanpv.service;

import com.quanpv.dao.CategoryRepository;
import com.quanpv.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;


    public Iterable<Category> getAll(){
        return repository.findAll();
    }

    public Category getById(int id){
        return repository.findOne(id);
    }

    public void save(Category product){
        repository.save(product);
    }

    public void delete(int id) {
        repository.delete(id);
    }

}
