package com.quanpv.controller;

import com.quanpv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/admin")
    public String admin(){
        return "admin";
    }

    @RequestMapping(value="/admin/users")
    public String userList(Model model){
        model.addAttribute("users", userService.getAllUser());
        return "users";
    }
}
