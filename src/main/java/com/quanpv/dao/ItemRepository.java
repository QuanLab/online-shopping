package com.quanpv.dao;

import com.quanpv.domain.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ItemRepository extends CrudRepository<Item, Integer> {

    Iterable<Item> findByCard_Id(int id);

}
