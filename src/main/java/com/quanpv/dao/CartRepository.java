package com.quanpv.dao;

import com.quanpv.domain.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CartRepository extends CrudRepository<Cart, Integer> {
    Iterable<Cart> findByIdCustomAndStatus(String idCustom, String status);
    Iterable<Cart> findByStatus(String status);
}
