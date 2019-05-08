package com.quanpv.service;

import com.quanpv.dao.ItemRepository;
import com.quanpv.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository repository;

    public Iterable<Item> getAll(){
        return repository.findAll();
    }

    public Item getById(Integer id){
        return repository.findById(id).orElse(null);
    }

    public List<Item> getByCartId(Integer id){
        return repository.findByCart_Id(id);
    }

    public List<Item> getByCart_IdCustomAndCart_Status(String idCustom, String status){
        return repository.findByCart_IdCustomAndCart_Status(idCustom, status);
    }


    public Item getByCardIdAndStatusAndProductId(String idCustom, String status, int productId){
        return repository.findByCart_IdCustomAndCart_StatusAndProduct_Id(idCustom, status, productId);
    }


    public void deleteByCardIdAndStatusAndProductId(String id, String status, int productId){
        repository.deleteByCart_IdCustomAndCart_StatusAndProduct_Id(id, status, productId);
    }


    public void updateQuantity(String id, String status, int productId, int quantity){
        Item item = repository.findByCart_IdCustomAndCart_StatusAndProduct_Id (id, status, productId);
        item.setQuantity(quantity);
        repository.save(item);
    }

    public Item save(Item item){
        return repository.save(item);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

}
