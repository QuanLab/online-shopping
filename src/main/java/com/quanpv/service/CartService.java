package com.quanpv.service;

import com.quanpv.dao.CartRepository;
import com.quanpv.model.Cart;
import com.quanpv.model.Item;
import com.quanpv.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository repository;

    public Iterable<Cart> getAll(){
        return repository.findAll();
    }

    public Cart getByIdCustom(String idCustom){
        Iterable<Cart> carts = repository.findByIdCustomAndStatus(idCustom, Constant.STATUS_NEW);
        if(carts.iterator().hasNext()) {
            return carts.iterator().next();
        }
        return null;
    }

    public Iterable<Cart> getByStatusCheckout(){
        return repository.findByStatus(Constant.STATUS_CHECKOUT);
    }

    public Cart getById(Integer id) {
        return repository.findById(id).orElseGet(null);
    }

    public Cart save(Cart cart){
        return repository.save(cart);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

}
