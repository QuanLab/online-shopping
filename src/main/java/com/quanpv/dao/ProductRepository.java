package com.quanpv.dao;

import com.quanpv.domain.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductRepository extends CrudRepository<Product, Integer> {
    Iterable<Product> findByCategory_Id(int id);
    Iterable<Product> findTop3ByCategory_Id(int id);
    Iterable<Product> findByNameContaining(String name);
}
