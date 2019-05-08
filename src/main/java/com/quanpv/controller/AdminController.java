package com.quanpv.controller;

import com.quanpv.model.Cart;
import com.quanpv.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;


@Controller
@RequestMapping(value="/admin")
public class AdminController {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private PostService postService;
    @Autowired
    private WebConfigService webConfigService;

    @RequestMapping(value="", method = RequestMethod.GET)
    public String dashboard(Model model,
                            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit){
        Integer offset = 0;
        if(page > 0) {
            offset = page - 1;
        }
        model.addAttribute("products", productService.getAll(offset, limit));
        model.addAttribute("limit", limit);
        return "dashboard";
    }


    @RequestMapping(value="products", method = RequestMethod.GET)
    public String adminProducts(Model model,
                                @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit){
        Integer offset = 0;
        if(page > 0) {
            offset = page - 1;
        }
        logger.info("limit " + limit);
        model.addAttribute("products", productService.getAll(offset, limit));
        model.addAttribute("limit", limit);
        return "adminProducts";
    }

    @RequestMapping(value="product", method = RequestMethod.GET)
    public String createProduct(Model model, @RequestParam(value = "id", required = false) Integer id,
                                         @RequestParam(value = "create", required = false) Integer create){
        model.addAttribute("categories", categoryService.getAll());
        if (create!= null && create == 1) {
            model.addAttribute("product", null);
        } else {
            model.addAttribute("product", productService.getById(id));
        }
        return "adminProduct";
    }

    @RequestMapping(value="categories", method = RequestMethod.GET)
    public String adminCategory(Model model){

        model.addAttribute("categories", categoryService.getAll());
        return "adminCategory";
    }

    @RequestMapping(value="blog/", method = RequestMethod.GET)
    public String adminBlog(Model model){
        model.addAttribute("posts", postService.findAll());
        return "adminBlogs";
    }


    @RequestMapping(value="blog/edit", method = RequestMethod.GET)
    public String adminEditBlog(Model model, @RequestParam(value = "id", required = false) Integer id){
        model.addAttribute("post", postService.findById(id));
        return "adminBlog";
    }

    @RequestMapping(value="blog/create", method = RequestMethod.GET)
    public String adminCreateBlog(){
        return "adminBlog";
    }

    @RequestMapping(value="page/", method = RequestMethod.GET)
    public String adminPage(Model model){
        model.addAttribute("mapConfig", webConfigService.getAll());
        return "adminAbout";
    }


    @RequestMapping(value="setting/", method = RequestMethod.GET)
    public String adminSetting(Model model){
        model.addAttribute("mapConfig", webConfigService.getAll());
        return "adminSetting";
    }


    @RequestMapping(value="orders/", method = RequestMethod.GET)
    public String dashboardOrders(Model model){
        Iterator<Cart> iterator = cartService.getByStatusCheckout().iterator();
        while (iterator.hasNext()) {
            logger.info(iterator.next().toString());
        }
        model.addAttribute("orders", cartService.getByStatusCheckout());
        return "adminOrders";
    }


    @RequestMapping(value="order/{id}", method = RequestMethod.GET)
    public String dashboardOrder( Model model, @PathVariable("id") Integer id){
        logger.info(cartService.getById(id).toString());
        model.addAttribute("cart", cartService.getById(id));
        logger.info(itemService.getByCartId(id).toString());
        model.addAttribute("items", itemService.getByCartId(id));
        return "adminOrder";
    }
}
