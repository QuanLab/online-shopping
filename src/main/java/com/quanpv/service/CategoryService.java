package com.quanpv.service;

import com.quanpv.dao.CategoryRepository;
import com.quanpv.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;


    public Iterable<Category> getAll(){
        return repository.findAll();
    }

    public Category getById(int id){
        return repository.findById(id).orElse(null);
    }

    public Category getBySlug(String slug){
        return repository.findTop1BySlug(slug);
    }

    public void save(Category product){
        repository.save(product);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public void delete(List<Integer> ids) {
        for(Integer id: ids) {
            repository.deleteById(id);
        }
    }
}
