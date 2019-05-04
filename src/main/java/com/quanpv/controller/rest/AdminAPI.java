package com.quanpv.controller.rest;


import com.quanpv.controller.FileUploadController;
import com.quanpv.controller.entity.ListCat;
import com.quanpv.controller.entity.ResponseWrapper;
import com.quanpv.model.Category;
import com.quanpv.model.Post;
import com.quanpv.model.Product;
import com.quanpv.service.CategoryService;
import com.quanpv.service.PostService;
import com.quanpv.service.ProductService;
import com.quanpv.storage.StorageService;
import com.quanpv.utils.Slug;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/")
public class AdminAPI {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private PostService postService;

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

    @RequestMapping(value="post", method = RequestMethod.POST)
    public ResponseWrapper createPost(@RequestBody Post post){
        if(post.getSlug() == null || post.getSlug().isEmpty()) {
            post.initSlug();
        }
        post.setUpdatedDate(new Date());
        logger.info("Save post: " + post.toString());
        postService.save(post);
        return new ResponseWrapper(200, "SUCCESS");
    }


    @RequestMapping("images")
    public List<String> listUploadedFiles(@RequestParam("offset") Integer offset,
                                          @RequestParam("limit") Integer limit) throws IOException {
        Iterator<Path> pathIterator = storageService.loadAll().skip(offset).limit(limit).iterator();
        List<String> imageList = new ArrayList<>();
        while (pathIterator.hasNext()) {
            Path path = pathIterator.next();
            String fileName = "/images/products/" + path.getFileName().toFile().getName();
            imageList.add(fileName);
        }
        return imageList;
    }

}
