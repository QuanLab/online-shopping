package com.quanpv.dao;

import com.quanpv.model.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ItemRepository extends CrudRepository<Item, Integer> {
    List<Item> findByCart_IdCustomAndCart_Status(String id, String status);
    Item findByCart_IdCustomAndCart_StatusAndProduct_Id(String id, String status, int productId);
    List<Item> findByCart_Id(int id);
    void deleteByCart_IdCustomAndCart_StatusAndProduct_Id(String id, String status, int productId);
}
