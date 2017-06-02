package com.quanpv;

import com.quanpv.domain.*;
import com.quanpv.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@Override
	public void run(String... strings) throws Exception {

		Category category = new Category("Thời trang nam", "Thoi trang danh cho nam");
		Category category2 = new Category("Thời trang nữ", "Thoi trang danh cho nu");

		Product product = new Product("Áo phông nam", "Sản phẩm mới về", "product.jpg", 120000, 12 );
		Product product2 = new Product("Váy ngắn", "Sản phẩm mới về", "product2.jpg", 110000, 25);
		Product product3 = new Product("Quần Jean nam", "Sản phẩm mới về", "product3.jpg", 150000, 25);
		Product product4 = new Product("Quần âu nam", "Sản phẩm mới về", "product4.jpg", 110000, 35);
		Product product5 = new Product("Áo sơ mi", "Sản phẩm nhập khẩu về", "product5.jpg", 210000, 25);

		product.setCategory(category);
		product2.setCategory(category2);
		product3.setCategory(category);
		product4.setCategory(category);
		product5.setCategory(category2);

		categoryService.save(category);
		categoryService.save(category2);

		productService.save(product);
		productService.save(product2);
		productService.save(product3);
		productService.save(product4);
		productService.save(product5);
	}
}