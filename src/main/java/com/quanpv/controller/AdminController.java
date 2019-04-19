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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author quanpv
 *
 */
//@Controller
@RequestMapping(value="/dashboard")
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
        return "dashboardProducts";
    }


    @RequestMapping(value="products/new", method = RequestMethod.GET)
    public String dashboardProductCreate(Model model){

        model.addAttribute("product", productService.getById(1));
        model.addAttribute("categories", categoryService.getAll());
        return "dashboardProductNew";
    }

    @RequestMapping(value="products/new", method = RequestMethod.POST)
    public String dashboardProductCreateSave(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                             @RequestParam(value = "description", required = false, defaultValue = "") String description,
                                             @RequestParam(value = "price", required = false, defaultValue = "") Float price,
                                             @RequestParam(value = "category.id", required = false, defaultValue = "") Integer categoryId,
                                             @RequestParam(value = "quantity", required = false, defaultValue = "") Integer quantity,
                                             @RequestParam("file") MultipartFile file, Model model){

        Product product = new Product("", name, description, "", "", price, quantity);
        logger.info("create new product: " + product.toString());

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(Constant.FOLDER_UPLOAD + file.getOriginalFilename());
                Files.write(path, bytes);
                String fileName = file.getOriginalFilename();
                product.setImage(fileName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Category category = categoryService.getById(categoryId);
        product.setCategory(category);
        productService.save(product);

        model.addAttribute("products", productService.getAll(0, 12));
        return "dashboardProducts";
    }



    @RequestMapping(value="products/{id}", method = RequestMethod.GET)
    public String dashboardProduct( @PathVariable("id") int id, Model model){

        model.addAttribute("product", productService.getById(id));
        model.addAttribute("categories", categoryService.getAll());
        return "dashboardProduct";
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
                    product.setImage(fileName);

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
            return "dashboardProducts";
        }

        return "dashboardProduct";
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
