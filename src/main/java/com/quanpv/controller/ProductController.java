package com.quanpv.controller;

import com.quanpv.model.Cart;
import com.quanpv.model.Item;
import com.quanpv.model.Product;
import com.quanpv.service.*;
import com.quanpv.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Date;

/**
 *
 */
//@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private CartDTOService cartDTOService;

    @RequestMapping(method = RequestMethod.GET)
    public String getAllProducts(@RequestParam(value = "q", defaultValue = "") String query, Model model){

        String sessionID = RequestContextHolder.currentRequestAttributes().getSessionId();
        model.addAttribute("cart", cartDTOService.getByCart_IdAndCart_Status(sessionID));

        Iterable<Product> products;
        if(query.equals("")) {
            products= productService.getAll(0, 12);
        } else {
            products = productService.getByNameContaining(query);
        }

        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("products", products);
        return "products";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getProduct(@PathVariable("id") int id,  Model model){

        Product product = productService.getById(id);

        Iterable<Product> relatedProduct = productService.getTop3ByCategory_Id(
                product.getCategory().getId());

        model.addAttribute("product", productService.getById(id));
        model.addAttribute("products", relatedProduct);
        model.addAttribute("categories", categoryService.getAll());
        String sessionID = RequestContextHolder.currentRequestAttributes().getSessionId();
        model.addAttribute("cart", cartDTOService.getByCart_IdAndCart_Status(sessionID));

        return "singleProduct";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String addToCart(@PathVariable("id") int id, Model model){

        String sessionID = RequestContextHolder.currentRequestAttributes().getSessionId();
        Cart existingCart = cartService.getByIdCustom(sessionID);

        if(existingCart ==null) {
            System.out.println("NO EXISTING CART");
            Cart cart = new Cart(sessionID, new Date(), Constant.STATUS_NEW);
            cartService.save(cart);

            Item item = new Item(cart, productService.getById(id), 1);
            itemService.save(item);

        } else {

            Item item = itemService.getByCardIdAndStatusAndProductId(
                    sessionID, Constant.STATUS_NEW, id);

            if(item!=null) {
                item.setQuantity(item.getQuantity() + 1);
                itemService.save(item);

            } else {
                itemService.save(new Item(existingCart, productService.getById(id), 1));
            }
        }

        model.addAttribute("product", productService.getById(id));
        model.addAttribute("products", productService.getTop3ByCategory_Id(id));
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("cart", cartDTOService.getByCart_IdAndCart_Status(sessionID));

        return "singleProduct";
    }

    @RequestMapping(value = "/categories/{id}", method = RequestMethod.GET)
    public String getProductByCategory(@PathVariable("id") int id, Model model){

        Iterable<Product> products = productService.getByCategoryId(id);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("products", products);

        String sessionID = RequestContextHolder.currentRequestAttributes().getSessionId();
        model.addAttribute("cart", cartDTOService.getByCart_IdAndCart_Status(sessionID));
        return "products";
    }

}
