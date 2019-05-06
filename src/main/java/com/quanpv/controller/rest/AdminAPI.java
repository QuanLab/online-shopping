package com.quanpv.controller.rest;


import com.quanpv.controller.entity.IdsWrapper;
import com.quanpv.controller.entity.ImageWrapper;
import com.quanpv.controller.entity.ResponseWrapper;
import com.quanpv.model.Post;
import com.quanpv.model.Product;
import com.quanpv.service.CategoryService;
import com.quanpv.service.PostService;
import com.quanpv.service.ProductService;
import com.quanpv.storage.StorageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


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
    public ResponseWrapper deleteCategory(@RequestBody IdsWrapper wrapper){
//        categoryService.deteletAllById(imageWrapper.getList());
        logger.info(wrapper.getList());
        return new ResponseWrapper(200, "SUCCESS");
    }


    @RequestMapping(value="deleteProduct", method = RequestMethod.POST)
    public ResponseWrapper deleteProduct(@RequestBody IdsWrapper wrapper){
        logger.info(wrapper.getList());
        productService.delete(wrapper.getList());
        return new ResponseWrapper(200, "SUCCESS");
    }

    @RequestMapping(value="deleteImage", method = RequestMethod.POST)
    public ResponseWrapper deleteImage(@RequestBody ImageWrapper wrapper){
        logger.info(wrapper.getList());
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
