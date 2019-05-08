package com.quanpv.controller;

import com.quanpv.model.*;
import com.quanpv.service.*;
import com.quanpv.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Map;

@Controller
public class HomeController {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartDTOService cartDTOService;
    @Autowired
    private WebConfigService webConfigService;
    @Autowired
    private PostService postService;

    @RequestMapping(value={""})
    public String home(@CookieValue(value = "token", required = false) String token, Model model){
        if(token == null) {
            token = RequestContextHolder.currentRequestAttributes().getSessionId();
        }

        model.addAttribute("token", token);
        model.addAttribute("mapConfig", webConfigService.getAll());

        Page<Product> featuredProducts = productService.getFeatured(0, 6);
        model.addAttribute("featuredProducts", featuredProducts);

        Page<Product> popularProducts = productService.getPopular(0, 6);
        model.addAttribute("popularProducts", popularProducts);

        Page<Product> newProducts = productService.getLastProduct(0, 4);
        model.addAttribute("newProducts", newProducts);

        model.addAttribute("categories", categoryService.getAll());

        Page<Post> posts = postService.getLast(0, 6);
        model.addAttribute("posts", posts);

        String sessionID = RequestContextHolder.currentRequestAttributes().getSessionId();
        model.addAttribute("cart", cartDTOService.getByCart_IdAndCart_Status(sessionID));
        return "index";
    }

    @RequestMapping(value={"/{category}/"})
    public String category(Model model,
                           @PathVariable("category") String categoryUrl,
                           @RequestParam(value = "page", required = false) Integer page,
                           @RequestParam(value = "sort", required = false) String sortBy,
                           @CookieValue(value = "token", required = false) String token){
        if(token == null) {
            token = RequestContextHolder.currentRequestAttributes().getSessionId();
        }
        model.addAttribute("categories", categoryService.getAll());
        Category category = categoryService.getBySlug(categoryUrl);

        Map<String, String> mapConfig = webConfigService.getAll();
        mapConfig.put("breadcrumb", category.getName());
        mapConfig.put("isFeatured", "1");
        model.addAttribute("mapConfig", mapConfig);
        model.addAttribute("token", token);

        int offset = 0;
        if(page != null) {
            offset = page - 1;
        }

        Page<Product> featuredProducts = productService.getFeatured(0, 5);
        model.addAttribute("featuredProducts", featuredProducts);

        Page<Product> products = productService.getPopular(offset, 9);
        model.addAttribute("products", products);
        return "products";
    }

    @RequestMapping(value={"/{category}/{product}/"})
    public String productDetail(Model model,
                                @PathVariable("category") String categoryUrl,
                                @PathVariable("product") String productUrl,
                                @CookieValue(value = "token", required = false) String token){

        if(token == null) {
            token = RequestContextHolder.currentRequestAttributes().getSessionId();
        }
        model.addAttribute("token", token);

        Map<String, String> mapConfig = webConfigService.getAll();
        model.addAttribute("mapConfig", mapConfig);

        model.addAttribute("categories", categoryService.getAll());
        Product productItem = productService.findFirstBySlug(productUrl);

        model.addAttribute("productItem", productItem);
        mapConfig.put("breadcrumb", productItem.getName());
        Page<Product> featuredProducts = productService.getFeatured(0, 6);
        model.addAttribute("featuredProducts", featuredProducts);

        Page<Product> relatedProducts = productService.getByCategory_Id(productItem.getCategory().getId(), 0, 6);
        model.addAttribute("relatedProducts", relatedProducts);

        return "singleProduct";
    }

    @RequestMapping(value={"san-pham"})
    public String collectionAll(Model model,
                                @RequestParam(value = "page", required = false) Integer page,
                                @RequestParam(value = "sort", required = false) String sortBy,
                                @CookieValue(value = "token", required = false) String token){
        if(token == null) {
            token = RequestContextHolder.currentRequestAttributes().getSessionId();
        }

        model.addAttribute("categories", categoryService.getAll());
        Map<String, String> mapConfig = webConfigService.getAll();
        mapConfig.put("breadcrumb", "Tất cả sản phẩm");
        model.addAttribute("mapConfig", mapConfig);
        model.addAttribute("token", token);

        int offset = 0;
        if(page != null) {
            offset = page - 1;
        }
        Page<Product> products = productService.getAll(offset, 9);
        model.addAttribute("products", products);

        Page<Product> featuredProducts = productService.getFeatured(0, 5);
        model.addAttribute("featuredProducts", featuredProducts);

        return "products";
    }

    @RequestMapping(value={"san-pham-noi-bat"})
    public String featuredProduct(Model model,
                                  @RequestParam(value = "page", required = false) Integer page,
                                  @RequestParam(value = "sort", required = false) String sortBy,
                                  @CookieValue(value = "token", required = false) String token){
        model.addAttribute("categories", categoryService.getAll());
        Map<String, String> mapConfig = webConfigService.getAll();
        mapConfig.put("breadcrumb", "Sản phẩm nổi bật");
        model.addAttribute("mapConfig", mapConfig);

        if(token == null) {
            token = RequestContextHolder.currentRequestAttributes().getSessionId();
        }
        model.addAttribute("token", token);

        Page<Product> featuredProducts = productService.getFeatured(0, 5);
        model.addAttribute("featuredProducts", featuredProducts);

        Page<Product> products = productService.getFeatured(0, 9);
        model.addAttribute("products", products);
        return "products";
    }

    @RequestMapping(value={"san-pham-ua-chuong"})
    public String popularProduct(Model model,
                                 @RequestParam(value = "page", required = false) Integer page,
                                 @RequestParam(value = "sort", required = false) String sortBy,
                                 @CookieValue(value = "token", required = false) String token){
        model.addAttribute("categories", categoryService.getAll());

        Map<String, String> mapConfig = webConfigService.getAll();
        mapConfig.put("breadcrumb", "Sản phẩm ưa chuộng");
        mapConfig.put("isFeatured", "1");
        model.addAttribute("mapConfig", mapConfig);

        if(token == null) {
            token = RequestContextHolder.currentRequestAttributes().getSessionId();
        }
        model.addAttribute("token", token);

        int offset = 0;
        if(page != null) {
            offset = page - 1;
        }

        Page<Product> featuredProducts = productService.getFeatured(0, 5);
        model.addAttribute("featuredProducts", featuredProducts);

        Page<Product> products = productService.getPopular(offset, 9);
        model.addAttribute("products", products);
        return "products";
    }

    @RequestMapping(value={"san-pham-moi"})
    public String newProduct(Model model,
                             @RequestParam(value = "page", required = false) Integer page,
                             @RequestParam(value = "sort", required = false) String sortBy,
                             @CookieValue(value = "token", required = false) String token){
        model.addAttribute("categories", categoryService.getAll());
        Map<String, String> mapConfig = webConfigService.getAll();
        mapConfig.put("breadcrumb", "Sản phẩm mới");
        model.addAttribute("mapConfig", mapConfig);

        if(token == null) {
            token = RequestContextHolder.currentRequestAttributes().getSessionId();
        }
        model.addAttribute("token", token);

        Page<Product> featuredProducts = productService.getFeatured(0, 5);
        model.addAttribute("featuredProducts", featuredProducts);

        int offset = 0;
        if(page != null) {
            offset = page - 1;
        }
        Page<Product> products = productService.getLastProduct(offset, 9);
        model.addAttribute("products", products);
        return "products";
    }

    @RequestMapping(value={"lien-he"}, method = RequestMethod.GET)
    public String contact(Model model, @CookieValue(value = "token", required = false) String token){
        if(token == null) {
            token = RequestContextHolder.currentRequestAttributes().getSessionId();
        }
        model.addAttribute("token", token);
        model.addAttribute("categories", categoryService.getAll());
        Map<String, String> mapConfig = webConfigService.getAll();
        mapConfig.put("breadcrumb", "Liên hệ");
        model.addAttribute("mapConfig", mapConfig);
        return "contact";
    }

    @RequestMapping(value={"lien-he"}, method = RequestMethod.POST)
    public String contactInfo(Model model, @CookieValue(value = "token", required = false) String token){
        if(token == null) {
            token = RequestContextHolder.currentRequestAttributes().getSessionId();
        }
        model.addAttribute("token", token);
        Map<String, String> mapConfig = webConfigService.getAll();

        mapConfig.put("breadcrumb", "Liên hệ");
        model.addAttribute("mapConfig", mapConfig);
        return "contact";
    }

    @RequestMapping(value={"gioi-thieu"})
    public String about(Model model, @CookieValue(value = "token", required = false) String token){
        if(token == null) {
            token = RequestContextHolder.currentRequestAttributes().getSessionId();
        }
        model.addAttribute("token", token);
        model.addAttribute("categories", categoryService.getAll());
        Map<String, String> mapConfig = webConfigService.getAll();
        mapConfig.put("title", "Giới thiệu Hoàng Anh Food");
        mapConfig.put("breadcrumb", "Giới thiệu");
        model.addAttribute("mapConfig", mapConfig);
        return "about";
    }

    @RequestMapping(value={"tin-tuc"})
    public String news (Model model, @CookieValue(value = "token", required = false) String token){
        model.addAttribute("categories", categoryService.getAll());
        Map<String, String> mapConfig = webConfigService.getAll();
        mapConfig.put("title", "Blog Hoang Anh Food");
        mapConfig.put("breadcrumb", "Blog");
        model.addAttribute("mapConfig", mapConfig);
        if(token == null) {
            token = RequestContextHolder.currentRequestAttributes().getSessionId();
        }
        model.addAttribute("token", token);

        Page<Post> newPosts = postService.getLast(0, 6);
        model.addAttribute("newPosts", newPosts);

        Page<Post> posts = postService.getLast(0, 12);
        model.addAttribute("posts", posts);
        return "blog";
    }


    @RequestMapping(value={"blog/{slug}/"})
    public String blogPost (Model model,
                            @PathVariable("slug") String slug ,
                            @CookieValue(value = "token", required = false) String token){
        model.addAttribute("categories", categoryService.getAll());
        Map<String, String> mapConfig = webConfigService.getAll();
        mapConfig.put("title", "Blog Hoang Anh Food");
        mapConfig.put("breadcrumb", "Blog");
        model.addAttribute("mapConfig", mapConfig);

        if(token == null) {
            token = RequestContextHolder.currentRequestAttributes().getSessionId();
        }
        model.addAttribute("token", token);

        Page<Post> posts = postService.getLast(0, 5);
        model.addAttribute("posts", posts);

        Post post = postService.findBySlug(slug);
        model.addAttribute("post", post);
        return "blogPost";
    }

    @RequestMapping(value="/cart")
    public String cart(@CookieValue(value = "token", required = false) String token,
                       Model model){
        String cartId = token;
        if(token == null) {
            token = RequestContextHolder.currentRequestAttributes().getSessionId();
        }
        model.addAttribute("token", token);
        model.addAttribute("categories", categoryService.getAll());
        Map<String, String> mapConfig = webConfigService.getAll();
        mapConfig.put("title", "Giỏ hàng của tôi");
        mapConfig.put("breadcrumb", "Giỏ hàng");
        model.addAttribute("mapConfig", mapConfig);

        model.addAttribute("cart", cartDTOService.getByCart_IdAndCart_Status(cartId));
        model.addAttribute("categories", categoryService.getAll());
        return "shoppingCart";
    }

    @RequestMapping(value={"login"})
    public String login(){
        return "login";
    }

    @RequestMapping(value="/checkout", method = RequestMethod.GET)
    public String checkOut(Model model, @CookieValue(value = "token", required = false) String token){
        model.addAttribute("categories", categoryService.getAll());
        Map<String, String> mapConfig = webConfigService.getAll();
        mapConfig.put("title", "Giỏ hàng của tôi");
        mapConfig.put("breadcrumb", "Giỏ hàng");
        model.addAttribute("mapConfig", mapConfig);

        String cartId = token;
        model.addAttribute("cart", cartDTOService.getByCart_IdAndCart_Status(cartId));
        return "checkout";
    }

    @RequestMapping(value="/403")
    public String Error403(){
        return "403";
    }

}
