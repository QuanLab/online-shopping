package com.quanpv.dao;

import com.quanpv.domain.Card;
import com.quanpv.domain.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CardRepository extends CrudRepository<Card, Integer> {
    Iterable<Card> findByCustomer_Id(int id);
    Iterable<Card> findByCustomer_IdAndAndStatus(int id, String status);
}
