package com.quanpv.service;

import com.quanpv.model.Item;
import com.quanpv.dto.CartDTO;
import com.quanpv.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartDTOService {

    @Autowired
    private ItemService itemService;

    public CartDTO getByCart_IdAndCart_Status(String id){
        List<Item> items = itemService.getByCart_IdCustomAndCart_Status(id, Constant.STATUS_NEW);
        return new CartDTO(id, items);
    }

    public CartDTO getByCart_Id(int id){
        List<Item> items = itemService.getByCartId(id);
        return new CartDTO(String.valueOf(id), items);
    }


    public void deleteByCardIdAndStatusAndProductId(String id, String status, int productId){
        itemService.deleteByCardIdAndStatusAndProductId(id, status, productId);
    }

}
