package com.quanpv.controller;

import com.quanpv.model.Product;
import com.quanpv.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author quanpv
 *
 */
@Controller
@RequestMapping(value="/admin")
public class AdminController {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartDTOService cartDTOService;

    @RequestMapping(value="", method = RequestMethod.GET)
    public String dashboard(Model model){

        model.addAttribute("products", productService.getAll(0, 12));
        return "dashboard";
    }


    @RequestMapping(value="products", method = RequestMethod.GET)
    public String dashboardProducts(Model model){

        model.addAttribute("products", productService.getAll(0, 12));
        return "adminProducts";
    }


    @RequestMapping(value="product", method = RequestMethod.GET)
    public String dashboardProductCreate(Model model, @RequestParam(value = "id", required = false) Integer id,
                                         @RequestParam(value = "create", required = false) Integer create){
        model.addAttribute("categories", categoryService.getAll());
        if (create!= null && create == 1) {
            model.addAttribute("create", 1);
            model.addAttribute("product", null);
            return "adminProductEditor";
        }
        model.addAttribute("product", productService.getById(id));
        return "adminProductEditor";
    }

    @RequestMapping(value="product", method = RequestMethod.POST)
    public String dashboardProductCreate(@ModelAttribute("product") Product product,
//                                             @RequestParam(value = "file", required = false, defaultValue = "") MultipartFile file,
                                         Model model){

        logger.info("create new product: " + product.toString());

//        if (!file.isEmpty()) {
//            try {
//                byte[] bytes = file.getBytes();
//                Path path = Paths.get(Constant.FOLDER_UPLOAD + file.getOriginalFilename());
//                Files.write(path, bytes);
//                String fileName = file.getOriginalFilename();
//                product.setFeatureImage(fileName);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

//        Category category = categoryService.getById(categoryId);
//        product.setCategory(category);
        productService.save(product);

        model.addAttribute("products", productService.getAll(0, 12));
        return "adminProducts";
    }



    @RequestMapping(value="blog/", method = RequestMethod.GET)
    public String adminBlog(Model model){

//        model.addAttribute("product", productService.getById(id));
//        model.addAttribute("categories", categoryService.getAll());
        return "adminBlog";
    }


    @RequestMapping(value="users/", method = RequestMethod.GET)
    public String adminUser(Model model){

//        model.addAttribute("product", productService.getById(id));
//        model.addAttribute("categories", categoryService.getAll());
        return "adminBlog";
    }


    @RequestMapping(value="page/", method = RequestMethod.GET)
    public String adminPage(Model model){

//        model.addAttribute("customers", customerService.getAll());
        return "adminPage";
    }


    @RequestMapping(value="setting/", method = RequestMethod.GET)
    public String adminSetting(Model model){

//        model.addAttribute("customer", customerService.getById(id));
        return "adminSetting";
    }


    @RequestMapping(value="orders", method = RequestMethod.GET)
    public String dashboardOrders(Model model){

        model.addAttribute("orders", cartService.getByStatusCheckout());
        return "dashboardOrders";
    }


    @RequestMapping(value="orders/{id}", method = RequestMethod.GET)
    public String dashboardOrder(@PathVariable("id") int id, Model model){

        System.out.println(cartDTOService.getByCart_Id(id).toString());
        model.addAttribute("cart", cartDTOService.getByCart_Id(id));
        return "dashboardOrder";
    }
}
