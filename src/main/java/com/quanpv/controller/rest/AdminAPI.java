package com.quanpv.controller.rest;


import com.quanpv.model.Category;
import com.quanpv.model.Product;
import com.quanpv.service.CategoryService;
import com.quanpv.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping("/api/v1/")
public class AdminAPI {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value="deleteCategory", method = RequestMethod.POST)
    public ResponseWrapper deleteCategory(@RequestBody ListCat categories){
        for(Category category : categories.getList()) {
            logger.info("Delete categories");
            categoryService.delete(category.getId());
        }
        return new ResponseWrapper(200, "SUCCESS");
    }


    @RequestMapping(value="product", method = RequestMethod.POST)
    public ResponseWrapper createProduct(@RequestBody Product product){
        product.setUpdatedDate(new Date());
        product.setCategory(categoryService.getById(product.getCategory().getId()));
        logger.info("Create new product: " + product.toString());
        productService.save(product);
        return new ResponseWrapper(200, "SUCCESS");
    }


}
