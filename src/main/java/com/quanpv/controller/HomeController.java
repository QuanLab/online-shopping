package com.quanpv.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @RequestMapping(value={"/","home"})
    public String home(){
        return "index";
    }

    @RequestMapping(value={"/welcome"})
    public String welcome(){
        return "welcome";
    }

    @RequestMapping(value={"/login"})
    public String login(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "index";
        }
        return "login";
    }

    @RequestMapping(value={"/register"})
    public String register(){
        return "register";
    }

    @RequestMapping(value={"/contact"})
    public String contact(){
        return "contact";
    }

    @RequestMapping(value="/403")
    public String Error403(){
        return "403";
    }
}
