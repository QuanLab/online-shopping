package com.quanpv.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private final String[] RESOURCE_ALLOWED = new String[]{
            "file:///home/quanpv/workspace/online-shopping/src/main/resources/", "file:///root/"
    };

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/san-pham").setViewName("product");
        registry.addViewController("/blog/*").setViewName("blog");
        registry.addViewController("/cart").setViewName("cart");
        registry.addViewController("/checkout").setViewName("checkout");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login").setViewName("login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/public", "classpath:/static/");
    }

}