package com.quanpv.controller;


import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/")
public class ProductController {

    @RequestMapping(value = "/productList", method = RequestMethod.GET)
    public String home(){

        return "This is a product list!";
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public String getProduct(@PathVariable(value = "id") String id){

        return "This is product details\n"  + id;
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.POST)
    public String updateProduct(@PathVariable(value = "id") String id){
        return "This is product details\n"  + id;
    }
}
