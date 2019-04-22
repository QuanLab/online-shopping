package com.quanpv.controller;

import com.quanpv.model.Category;
import com.quanpv.model.Product;
import com.quanpv.service.*;
import com.quanpv.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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



    @RequestMapping(value="products/{id}", method = RequestMethod.GET)
    public String dashboardProduct( @PathVariable("id") int id, Model model){

        model.addAttribute("product", productService.getById(id));
        model.addAttribute("categories", categoryService.getAll());
        return "adminProducts";
    }

    @RequestMapping(value="products/{id}", method = RequestMethod.POST)
    public String dashboardProductSave(@RequestParam(required = false, name = "save") String save,
                                       @RequestParam(required = false, name = "delete") String delete,
                                       @RequestParam(value = "name", required = false, defaultValue = "") String name,
                                       @RequestParam(value = "description", required = false, defaultValue = "") String description,
                                       @RequestParam(value = "price", required = false, defaultValue = "") Float price,
                                       @RequestParam(value = "category.id", required = false, defaultValue = "") Integer categoryId,
                                       @RequestParam(value = "quantity", required = false, defaultValue = "") Integer quantity,
                                       @RequestParam("file") MultipartFile file,
                                       @PathVariable("id") int id, Model model){

        model.addAttribute("categories", categoryService.getAll());

        if(save!=null) {
            Product product = productService.getById(id);

            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(Constant.FOLDER_UPLOAD + file.getOriginalFilename());
                    Files.write(path, bytes);
                    String fileName = file.getOriginalFilename();
                    product.setFeatureImage(fileName);
                    System.out.println(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Category category = categoryService.getById(categoryId);

            product.setCategory(category);
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setQuantity(quantity);

            productService.save(product);
            model.addAttribute("product", productService.getById(id));
        }

        if(delete!=null) {
            productService.delete(id);
            model.addAttribute("products", productService.getAll(0, 12));
            return "adminProducts";
        }

        return "adminProducts";
    }


    @RequestMapping(value="customers", method = RequestMethod.GET)
    public String dashboardCustomers(Model model){

        model.addAttribute("customers", customerService.getAll());
        return "dashboardCustomers";
    }


    @RequestMapping(value="customers/{id}", method = RequestMethod.GET)
    public String dashboardCustomer(@PathVariable("id") int id, Model model){

        model.addAttribute("customer", customerService.getById(id));
        return "dashboardCustomer";
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
