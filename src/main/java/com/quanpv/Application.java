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

	@Autowired
    private CustomerService customerService;

	@Autowired
    private CardService cardService;

	@Autowired
    private ItemService itemService;

    @Override
    public void run(String... strings) throws Exception {

        Category category = new Category("Thoi trang nam", "Thoi trang danh cho nam");
        Category category2 = new Category("Thoi trang nu", "Thoi trang danh cho nu");


        Product product = new Product("Ao nam", "Sản phẩm mới về", "image.png", 120000, 12 );
        Product product2 = new Product("Ao nu", "Sản phẩm mới về", "image.png", 110000, 25);

        Customer customer = new Customer("Quan Pham", "admin@gmail.com", "Hai Duong", "01202125201");
        Customer customer2 = new Customer("Hoa Nguyen", "author@gmail.com", "Ha Noi", "1252152441");

        Card card = new Card(customer, "NEW");

        product.setCategory(category);
        product2.setCategory(category2);

        Item item = new Item(card, product, 2);
        Item item2 = new Item(card, product2, 3);

        categoryService.save(category);
        categoryService.save(category2);

        productService.save(product);
        productService.save(product2);

        customerService.save(customer);
        customerService.save(customer2);

        cardService.save(card);
        itemService.save(item);
        itemService.save(item2);

	}
}
