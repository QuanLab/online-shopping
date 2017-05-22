package com.quanpv;

import com.quanpv.domain.Category;
import com.quanpv.domain.Customer;
import com.quanpv.domain.Product;
import com.quanpv.service.CategoryService;
import com.quanpv.service.CustomerService;
import com.quanpv.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    @Autowired
	private ProductService productService;

	@Autowired
    private CategoryService categoryService;

	@Autowired
    private CustomerService customerService;

    @Override
    public void run(String... strings) throws Exception {

        Category category = new Category("Thoi trang nam", "Thoi trang danh cho nam");
        Category category2 = new Category("Thoi trang nu", "Thoi trang danh cho nu");


        Product product = new Product("Ao nam", "Sản phẩm mới về", "image.png", 120000, 12 );
        Product product2 = new Product("Ao nu", "Sản phẩm mới về", "image.png", 110000, 25);

        product.setCategory(category);
        product2.setCategory(category2);

        categoryService.save(category);
        categoryService.save(category2);

        productService.save(product);
        productService.save(product2);

        customerService.save(new Customer("Quan", "Pham", "admin@gmail.com", "Hai Duong", "01202125201"));
        customerService.save(new Customer("Hoa", "Nguyen", "author@gmail.com", "Ha Noi", "1252152441"));

	}
}
