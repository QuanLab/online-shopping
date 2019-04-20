package com.quanpv.service;

import com.quanpv.dao.ProductRepository;
import com.quanpv.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Page<Product> getAll(int offset, int limit){
        Pageable pageable = PageRequest.of(offset, limit);
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

    /**
     *
     * @return
     */
    public Page<Product> getLastProduct(Integer offset, Integer limit) {
        Sort sort = new Sort(
                new Sort.Order(Sort.Direction.DESC, "id")
        );
        Pageable pageable = PageRequest.of(offset, limit, sort);
        return repository.findAll(pageable);
    }


    /**
     *
     * @return
     */
    public Iterable<Product> getLastProduct() {
        return repository.findTop4ByOrderByIdDesc();
    }

    /**
     *
     * @return
     */
    public Page<Product> getFeatured(Integer offset, Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return repository.findByIsFeatureIsTrue(pageable);
    }

    /**
     *
     * @return
     */
    public Page<Product> getPopular(Integer offset, Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return repository.findByIsPopularIsTrue(pageable);
    }

}
