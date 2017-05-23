package com.quanpv.service;

import com.quanpv.dao.CardRepository;
import com.quanpv.dao.ProductRepository;
import com.quanpv.domain.Card;
import com.quanpv.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    @Autowired
    private CardRepository repository;

    public Iterable<Card> getAll(){
        return repository.findAll();
    }

    public Card getById(int id){
        return repository.findOne(id);
    }

    public Iterable<Card> getByCustomerId(int id){
        return repository.findByCustomer_Id(id);
    }

    public void save(Card card){
        repository.save(card);
    }

    public void delete(int id) {
        repository.delete(id);
    }

}
