package com.quanpv.controller;

import com.quanpv.model.Cart;
import com.quanpv.model.Customer;
import com.quanpv.model.Item;
import com.quanpv.service.*;
import com.quanpv.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

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

    @RequestMapping(value={""})
    public String home(Model model){
        model.addAttribute("mapConfig", webConfigService.getAll());
        model.addAttribute("products", productService.getAll(0, 12));
        model.addAttribute("categories", categoryService.getAll());
        String sessionID = RequestContextHolder.currentRequestAttributes().getSessionId();
        model.addAttribute("cart", cartDTOService.getByCart_IdAndCart_Status(sessionID));
        return "index";
    }

    @RequestMapping(value={"login"})
    public String login(){
        return "login";
    }

    @RequestMapping(value={"contact"})
    public String contact(){
        return "contact";
    }

    @RequestMapping(value="/403")
    public String Error403(){
        return "403";
    }

    @RequestMapping(value="/cart")
    public String cart(@RequestParam(value = "id", required = false) Integer id, Model model){

        String sessionID = RequestContextHolder.currentRequestAttributes().getSessionId();
        if(id!=null) {
            cartDTOService.deleteByCardIdAndStatusAndProductId(sessionID, Constant.STATUS_NEW, id);
        }

        model.addAttribute("cart", cartDTOService.getByCart_IdAndCart_Status(sessionID));
        model.addAttribute("categories", categoryService.getAll());
        return "shoppingCart";
    }

    @RequestMapping(value="/cart", method = RequestMethod.POST)
    public String cartUpdate(@RequestParam(value = "quantity", required = false) List<Integer> quantityList,
                             Model model ){

        String sessionID = RequestContextHolder.currentRequestAttributes().getSessionId();

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
        String sessionID = RequestContextHolder.currentRequestAttributes().getSessionId();
        model.addAttribute("cart", cartDTOService.getByCart_IdAndCart_Status(sessionID));
        return "checkout";
    }

    @RequestMapping(value="/checkout", method = RequestMethod.POST)
    public String checkOutSubmit(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                 @RequestParam(value = "address", required = false, defaultValue = "") String address,
                                 @RequestParam(value = "phone", required = false, defaultValue = "") String phone,
                                 Model model){
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
