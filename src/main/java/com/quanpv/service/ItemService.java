package com.quanpv.service;

import com.quanpv.dao.ItemRepository;
import com.quanpv.dao.ProductRepository;
import com.quanpv.domain.Item;
import com.quanpv.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    private ItemRepository repository;

    public Iterable<Item> getAll(){
        return repository.findAll();
    }

    public Item getById(int id){
        return repository.findOne(id);
    }

    public Iterable<Item> getByCardId(int id){
        return repository.findByCard_Id(id);
    }

    public void save(Item item){
        repository.save(item);
    }

    public void delete(int id) {
        repository.delete(id);
    }

}
