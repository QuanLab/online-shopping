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
    Page<Product> findByCategory_Id(int id, Pageable pageable);

    /**
     *
     * @param name
     *  find product by name
     * @return
     */
    Iterable<Product> findByNameContaining(String name);


    /**
     * lay san pham noi bat
     */
    Page<Product> findByIsFeatureIsTrue(Pageable pageable);


    /**
     * lay san pham ua chuong
     * @param pageable
     * @return
     */
    Page<Product> findByIsPopularIsTrue(Pageable pageable);



    Product findFirstBySlug(String slug);

}

