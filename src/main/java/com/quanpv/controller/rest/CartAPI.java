package com.quanpv.controller.rest;

import com.quanpv.controller.entity.ListCat;
import com.quanpv.controller.entity.ResponseWrapper;
import com.quanpv.model.Cart;
import com.quanpv.model.Category;
import com.quanpv.model.Item;
import com.quanpv.service.CartService;
import com.quanpv.service.ItemService;
import com.quanpv.service.ProductService;
import com.quanpv.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/")
public class CartAPI {

    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ItemService itemService;

    @RequestMapping(value="addCart", method = RequestMethod.POST)
    public ResponseWrapper deleteCategory(@RequestBody ListCat categories){
        String sessionID = RequestContextHolder.currentRequestAttributes().getSessionId();
        Cart existingCart = cartService.getByIdCustom(sessionID);
        Integer id = 0;
        if(existingCart == null) {
            logger.info("Cart does not exists");
            Cart cart = new Cart(sessionID, new Date(), Constant.STATUS_NEW);
            cartService.save(cart);
            Item item = new Item(cart, productService.getById(id), 1);
            itemService.save(item);
        } else {
            Item item = itemService.getByCardIdAndStatusAndProductId(sessionID, Constant.STATUS_NEW, id);
            if(item!=null) {
                item.setQuantity(item.getQuantity() + 1);
                itemService.save(item);
            } else {
                itemService.save(new Item(existingCart, productService.getById(id), 1));
            }
        }
        return new ResponseWrapper(200, "SUCCESS");
    }

}
