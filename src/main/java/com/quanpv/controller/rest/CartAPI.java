package com.quanpv.controller.rest;

import com.quanpv.controller.entity.CartWrapper;
import com.quanpv.controller.entity.ResponseWrapper;
import com.quanpv.dto.CartDTO;
import com.quanpv.model.Cart;
import com.quanpv.model.Item;
import com.quanpv.service.CartDTOService;
import com.quanpv.service.CartService;
import com.quanpv.service.ItemService;
import com.quanpv.service.ProductService;
import com.quanpv.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/cart")
public class CartAPI {

    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private CartService cartService;
    @Autowired
    private CartDTOService cartDTOService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ItemService itemService;

    @RequestMapping(value="", method = RequestMethod.GET)
    public CartDTO getCart(@RequestParam(value = "token", required = false) String token){
        cartDTOService.getByCart_IdAndCart_Status(token);
        return cartDTOService.getByCart_IdAndCart_Status(token);
    }

    /**
     * add product to cart
     * @param cartWrapper
     * @return
     */
    @RequestMapping(value="add", method = RequestMethod.POST)
    public ResponseWrapper addCart(@RequestBody CartWrapper cartWrapper){
        logger.info(cartWrapper.toString());
        String token = cartWrapper.getToken();

        Cart existingCart = cartService.getByIdCustom(token);
        Integer id = Integer.valueOf(cartWrapper.getProductId());
        Integer quantity = Integer.valueOf(cartWrapper.getQuantity());

        ResponseWrapper responseWrapper = new ResponseWrapper(200, "SUCCESS");
        if(existingCart == null) {
            logger.info("Cart does not exists");
            Cart cart = new Cart(token, new Date(), Constant.STATUS_NEW);
            cartService.save(cart);
            Item item = new Item(cart, productService.getById(id), quantity);
            itemService.save(item);
            responseWrapper.setObject(item.getProduct());
        } else {
            Item item = itemService.getByCardIdAndStatusAndProductId(token, Constant.STATUS_NEW, id);
            if(item!=null) {
                item.setQuantity(item.getQuantity() + quantity);
                itemService.save(item);
            } else {
                item = itemService.save(new Item(existingCart, productService.getById(id), quantity));
            }
            responseWrapper.setObject(item.getProduct());
        }
        return responseWrapper;
    }
}
