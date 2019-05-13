package com.quanpv.controller.rest;

import com.quanpv.controller.entity.CheckoutWrapper;
import com.quanpv.controller.entity.IdsWrapper;
import com.quanpv.controller.entity.ImageWrapper;
import com.quanpv.controller.entity.ResponseWrapper;
import com.quanpv.model.*;
import com.quanpv.service.*;
import com.quanpv.storage.StorageService;
import com.quanpv.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;


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
    @Autowired
    private CartService cartService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private WebConfigService webConfigService;

    @RequestMapping(value="deleteCategory", method = RequestMethod.POST)
    public ResponseWrapper deleteCategory(@RequestBody IdsWrapper wrapper){
        categoryService.delete(wrapper.getList());
        logger.info("deleteCategory " + wrapper.getList());
        return new ResponseWrapper(200, "SUCCESS");
    }

    @RequestMapping(value="deleteProduct", method = RequestMethod.POST)
    public ResponseWrapper deleteProduct(@RequestBody IdsWrapper wrapper){
        logger.info("deleteProduct " + wrapper.getList());
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
        if(product.getId() == null) {
            product.setCreatedDate(new Date());
        }
        product.setUpdatedDate(new Date());
        product.setCategory(categoryService.getById(product.getCategory().getId()));
        logger.info("Create new product: " + product.toString());
        productService.save(product);
        return new ResponseWrapper(200, "SUCCESS");
    }

    @RequestMapping(value="product", method = RequestMethod.GET)
    public Product getProduct(@RequestParam(value = "id") Integer id){
        logger.info(productService.getById(id).toString());
        return productService.getById(id);
    }


    @RequestMapping(value="category", method = RequestMethod.POST)
    public ResponseWrapper createCategory(@RequestBody Category category){
        logger.info("Save category : " + category.toString());
        categoryService.save(category);
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

    @RequestMapping(value="setting", method = RequestMethod.POST)
    public ResponseWrapper saveSetting(@RequestBody Map<String, String> mapConfig){
        logger.info("Save mapConfig : " + mapConfig.toString());
        List<WebConfig> list = new ArrayList<>();
        for(String key : mapConfig.keySet()) {
            list.add(new WebConfig(key, mapConfig.get(key)));
        }
        webConfigService.save(list);
        return new ResponseWrapper(200, "SUCCESS");
    }

    @RequestMapping(value="about", method = RequestMethod.POST)
    public ResponseWrapper saveAbout(@RequestBody Map<String, String> mapConfig){
        logger.info("Save mapConfig : " + mapConfig.toString());
        webConfigService.save(new WebConfig("about", mapConfig.get("about")));
        return new ResponseWrapper(200, "SUCCESS");
    }


    @RequestMapping("images")
    public List<String> listUploadedFiles(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                          @RequestParam(value = "limit", required = false, defaultValue = "60") Integer limit) throws IOException {
        Iterator<Path> pathIterator = storageService.loadAll().skip(offset).limit(limit).iterator();
        List<String> imageList = new ArrayList<>();
        while (pathIterator.hasNext()) {
            Path path = pathIterator.next();
            String fileName = "/images/products/" + path.getFileName().toFile().getName();
            imageList.add(fileName);
        }
        return imageList;
    }

    @RequestMapping(value="category", method = RequestMethod.GET)
    public Category createPost(@RequestParam("id") Integer id){
        return categoryService.getById(id);
    }

    @RequestMapping(value="checkout", method = RequestMethod.POST)
    public ResponseWrapper checkOut(@RequestBody CheckoutWrapper wrapper){
        logger.info(wrapper.toString());
        Customer customer = new Customer(wrapper.getName(), wrapper.getEmail(), wrapper.getPhone(), wrapper.getAddress());

        Cart cart = cartService.getByIdCustom(wrapper.getToken());
        cart.setCustomer(customer);
        cart.setCustomer(customer);
        cart.setStatus(Constant.STATUS_CHECKOUT);
        cart.setNote(wrapper.getNote());
        customerService.save(customer);
        cartService.save(cart);
        return new ResponseWrapper(200, "Đặt hàng thành công");
    }
}
