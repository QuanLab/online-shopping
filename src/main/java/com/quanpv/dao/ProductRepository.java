package com.quanpv.dao;

import com.quanpv.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author quanpv
 */
@Transactional
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {


    /**
     * find all product with pageable
     * @param pageable
     * @return
     */
    Page<Product> findAll(Pageable pageable);

    /**
     * get list product by id of category
     * @param id
     * @return
     */
    Iterable<Product> findByCategory_Id(int id);

    /**
     * get top3 by category id
     * @param id
     * @return
     */
    Iterable<Product> findTop3ByCategory_Id(int id);

    /**
     *
     * @param name
     *  find product by name
     * @return
     */
    Iterable<Product> findByNameContaining(String name);


    /**
     * find top 4 newest product
     */
    Iterable<Product> findTop4ByOrderByIdDesc();


    Page<Product> findByIsFeatureIsTrue(Pageable pageable);


    Page<Product> findByIsPopularIsTrue(Pageable pageable);
}
