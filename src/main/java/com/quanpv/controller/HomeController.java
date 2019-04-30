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

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartDTOService cartDTOService;
    @Autowired
    private WebConfigService webConfigService;
    @Autowired
    private PostService postService;

    @RequestMapping(value={""})
    public String home(Model model){
        model.addAttribute("token", RequestContextHolder.currentRequestAttributes().getSessionId());
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
    public String category(Model model, @PathVariable("category") String categoryUrl,
                                 @RequestParam(value = "page", required = false) Integer page,
                                 @RequestParam(value = "sort", required = false) String sortBy){
        model.addAttribute("categories", categoryService.getAll());
        Category category = categoryService.getBySlug(categoryUrl);

        Map<String, String> mapConfig = webConfigService.getAll();
        mapConfig.put("breadcrumb", category.getName());
        mapConfig.put("isFeatured", "1");
        model.addAttribute("mapConfig", mapConfig);
        model.addAttribute("token", RequestContextHolder.currentRequestAttributes().getSessionId());

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
                                @PathVariable("product") String productUrl){
        Map<String, String> mapConfig = webConfigService.getAll();
        model.addAttribute("mapConfig", mapConfig);

        model.addAttribute("token", RequestContextHolder.currentRequestAttributes().getSessionId());
        model.addAttribute("categories", categoryService.getAll());

        Product productItem = productService.findFirstBySlug(productUrl);
        logger.info(productItem.toString());
        model.addAttribute("productItem", productItem);
        mapConfig.put("breadcrumb", productItem.getName());
        Page<Product> featuredProducts = productService.getFeatured(0, 6);
        model.addAttribute("featuredProducts", featuredProducts);

        Page<Product> relatedProducts = productService.getByCategory_Id(productItem.getCategory().getId(), 0, 6);
        model.addAttribute("relatedProducts", relatedProducts);

        return "singleProduct";
    }

    @RequestMapping(value={"san-pham"})
    public String collectionAll(Model model, @RequestParam(value = "page", required = false) Integer page,
                                @RequestParam(value = "sort", required = false) String sortBy){
        model.addAttribute("categories", categoryService.getAll());
        Map<String, String> mapConfig = webConfigService.getAll();
        mapConfig.put("breadcrumb", "Tất cả sản phẩm");
        model.addAttribute("mapConfig", mapConfig);
        model.addAttribute("token", RequestContextHolder.currentRequestAttributes().getSessionId());

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
                                  @RequestParam(value = "sort", required = false) String sortBy){
        model.addAttribute("categories", categoryService.getAll());
        Map<String, String> mapConfig = webConfigService.getAll();
        mapConfig.put("breadcrumb", "Sản phẩm nổi bật");
        model.addAttribute("mapConfig", mapConfig);
        model.addAttribute("token", RequestContextHolder.currentRequestAttributes().getSessionId());

        Page<Product> featuredProducts = productService.getFeatured(0, 5);
        model.addAttribute("featuredProducts", featuredProducts);

        Page<Product> products = productService.getFeatured(0, 9);
        model.addAttribute("products", products);
        return "products";
    }

    @RequestMapping(value={"san-pham-ua-chuong"})
    public String popularProduct(Model model,
                                 @RequestParam(value = "page", required = false) Integer page,
                                 @RequestParam(value = "sort", required = false) String sortBy){
        model.addAttribute("categories", categoryService.getAll());

        Map<String, String> mapConfig = webConfigService.getAll();
        mapConfig.put("breadcrumb", "Sản phẩm ưa chuộng");
        mapConfig.put("isFeatured", "1");
        model.addAttribute("mapConfig", mapConfig);
        model.addAttribute("token", RequestContextHolder.currentRequestAttributes().getSessionId());

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
                             @RequestParam(value = "sort", required = false) String sortBy){
        model.addAttribute("categories", categoryService.getAll());
        Map<String, String> mapConfig = webConfigService.getAll();
        mapConfig.put("breadcrumb", "Sản phẩm mới");
        model.addAttribute("mapConfig", mapConfig);
        model.addAttribute("token", RequestContextHolder.currentRequestAttributes().getSessionId());

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
    public String contact(Model model){
        model.addAttribute("token", RequestContextHolder.currentRequestAttributes().getSessionId());
        model.addAttribute("categories", categoryService.getAll());
        Map<String, String> mapConfig = webConfigService.getAll();
        mapConfig.put("breadcrumb", "Liên hệ");
        model.addAttribute("mapConfig", mapConfig);
        return "contact";
    }

    @RequestMapping(value={"lien-he"}, method = RequestMethod.POST)
    public String contactInfo(Model model){
        model.addAttribute("token", RequestContextHolder.currentRequestAttributes().getSessionId());
        Map<String, String> mapConfig = webConfigService.getAll();

        mapConfig.put("breadcrumb", "Liên hệ");
        model.addAttribute("mapConfig", mapConfig);
        return "contact";
    }

    @RequestMapping(value={"gioi-thieu"})
    public String about(Model model){
        model.addAttribute("token", RequestContextHolder.currentRequestAttributes().getSessionId());
        model.addAttribute("categories", categoryService.getAll());
        Map<String, String> mapConfig = webConfigService.getAll();
        mapConfig.put("title", "Giới thiệu Hoàng Anh Food");
        mapConfig.put("breadcrumb", "Giới thiệu");
        model.addAttribute("mapConfig", mapConfig);
        return "about";
    }

    @RequestMapping(value={"tin-tuc"})
    public String news (Model model){
        model.addAttribute("categories", categoryService.getAll());
        Map<String, String> mapConfig = webConfigService.getAll();
        mapConfig.put("title", "Blog Hoang Anh Food");
        mapConfig.put("breadcrumb", "Blog");
        model.addAttribute("mapConfig", mapConfig);
        model.addAttribute("token", RequestContextHolder.currentRequestAttributes().getSessionId());

        Page<Post> newPosts = postService.getLast(0, 6);
        model.addAttribute("newPosts", newPosts);

        Page<Post> posts = postService.getLast(0, 12);
        model.addAttribute("posts", posts);
        return "blog";
    }


    @RequestMapping(value={"blog/{slug}/"})
    public String blogPost (Model model, @PathVariable("slug") String slug){
        model.addAttribute("categories", categoryService.getAll());
        Map<String, String> mapConfig = webConfigService.getAll();
        mapConfig.put("title", "Blog Hoang Anh Food");
        mapConfig.put("breadcrumb", "Blog");
        model.addAttribute("mapConfig", mapConfig);
        model.addAttribute("token", RequestContextHolder.currentRequestAttributes().getSessionId());

        Page<Post> posts = postService.getLast(0, 5);
        model.addAttribute("posts", posts);

        Post post = postService.findBySlug(slug);
        model.addAttribute("post", post);
        return "blogPost";
    }


    @RequestMapping(value="/403")
    public String Error403(){
        return "403";
    }

    @RequestMapping(value="/cart")
    public String cart(@RequestParam(value = "id", required = false) Integer id, Model model){
        model.addAttribute("categories", categoryService.getAll());
        Map<String, String> mapConfig = webConfigService.getAll();
        mapConfig.put("title", "Giỏ hàng của tôi");
        mapConfig.put("breadcrumb", "Giỏ hàng");
        model.addAttribute("mapConfig", mapConfig);
        model.addAttribute("token", RequestContextHolder.currentRequestAttributes().getSessionId());

        String sessionID = RequestContextHolder.currentRequestAttributes().getSessionId();
        if(id!=null) {
            cartDTOService.deleteByCardIdAndStatusAndProductId(sessionID, Constant.STATUS_NEW, id);
        }

        model.addAttribute("cart", cartDTOService.getByCart_IdAndCart_Status(sessionID));
        model.addAttribute("categories", categoryService.getAll());
        return "shoppingCart";
    }


    @RequestMapping(value={"login"})
    public String login(){
        return "login";
    }


    @RequestMapping(value="/cart", method = RequestMethod.POST)
    public String cartUpdate(@RequestParam(value = "quantity", required = false) List<Integer> quantityList,
                             Model model ){
        String sessionID = RequestContextHolder.currentRequestAttributes().getSessionId();
        model.addAttribute("categories", categoryService.getAll());

        if(quantityList!=null) {
            List<Item> items = itemService.getByCart_IdCustomAndCart_Status(sessionID, Constant.STATUS_NEW);
            for(int i=0; i <items.size(); i++) {
                Item item = items.get(i);
                item.setQuantity(quantityList.get(i));
                itemService.save(item);
            }
        }

        model.addAttribute("cart", cartDTOService.getByCart_IdAndCart_Status(sessionID));
        model.addAttribute("categories", categoryService.getAll());
        return "shoppingCart";
    }

    @RequestMapping(value="/checkout", method = RequestMethod.GET)
    public String checkOut(Model model){
        model.addAttribute("categories", categoryService.getAll());
        Map<String, String> mapConfig = webConfigService.getAll();
        mapConfig.put("title", "Giỏ hàng của tôi");
        mapConfig.put("breadcrumb", "Giỏ hàng");
        model.addAttribute("mapConfig", mapConfig);

        String sessionID = RequestContextHolder.currentRequestAttributes().getSessionId();
        model.addAttribute("cart", cartDTOService.getByCart_IdAndCart_Status(sessionID));
        return "checkout";
    }

    @RequestMapping(value="/checkout", method = RequestMethod.POST)
    public String checkOutSubmit(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                 @RequestParam(value = "address", required = false, defaultValue = "") String address,
                                 @RequestParam(value = "phone", required = false, defaultValue = "") String phone,
                                 Model model){
        model.addAttribute("categories", categoryService.getAll());
        String sessionID = RequestContextHolder.currentRequestAttributes().getSessionId();

        if(name.equals("")|| address.equals("")||phone.equals("")) {
            model.addAttribute("invalid", "Thông tin không hợp lệ");

        } else {
            Customer customer = new Customer(name, address, phone);

            Cart cart = cartService.getByIdCustom(sessionID);
            if(cart==null) {
                model.addAttribute("invalid", "Giỏ hàng chưa có sản phẩm nào");
            } else {
                cart.setCustomer(customer);
                cart.setStatus(Constant.STATUS_CHECKOUT);
                customerService.save(customer);
                cartService.save(cart);
                model.addAttribute("success", "Đặt hàng thành công");
            }
        }
        model.addAttribute("cart", cartDTOService.getByCart_IdAndCart_Status(sessionID));
        return "checkout";
    }
}
