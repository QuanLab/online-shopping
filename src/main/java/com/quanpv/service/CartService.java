package com.quanpv.service;

import com.quanpv.dao.CartRepository;
import com.quanpv.domain.Cart;
import com.quanpv.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Cart getById(int id){
        return repository.findOne(id);
    }

    public Iterable<Cart> getByStatusCheckout(){
        return repository.findByStatus(Constant.STATUS_CHECKOUT);
    }

    public void save(Cart cart){
        repository.save(cart);
    }

    public void delete(int id) {
        repository.delete(id);
    }

}
