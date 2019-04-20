package com.quanpv;

import com.quanpv.model.*;
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
	private WebConfigService webConfigService;

	@Override
	public void run(String... strings) {

		setupDefaultConfig();

		Category category = new Category("Thời trang nam", "Thoi trang danh cho nam");
		Category category2 = new Category("Thời trang nữ", "Thoi trang danh cho nu");

		Product product = new Product("", "Áo phông nam", "Sản phẩm mới về", "product.jpg", "product.jpg", 120000, 12 );
		Product product2 = new Product("", "Váy ngắn", "Sản phẩm mới về", "", "product2.jpg", 110000, 25);
		Product product3 = new Product("", "Quần Jean nam", "Sản phẩm mới về", "",  "product3.jpg", 150000, 25);
		Product product4 = new Product("", "Quần âu nam", "Sản phẩm mới về","", "product4.jpg", 110000, 35);
		Product product5 = new Product("", "Áo sơ mi", "Sản phẩm nhập khẩu về", "", "product5.jpg", 210000, 25);

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

	/**
	 * setup default config for web
	 */
	private void setupDefaultConfig() {
		webConfigService.save(new WebConfig("domain", "https://hoanganhfood.com"));
		webConfigService.save(new WebConfig("title", "Hoàng Anh Food - Chuyên bột chiên rán"));
		webConfigService.save(new WebConfig("description", "Hoàng Anh food, chuyên cung cấp bột chiên, rán chất lượng cao"));
		webConfigService.save(new WebConfig("keywords", "hoàng anh food, bột chiên, bột rán"));
		webConfigService.save(new WebConfig("contact", "contact@hoanganhfood.com"));
		webConfigService.save(new WebConfig("email", "your-email@gmail.com"));
		webConfigService.save(new WebConfig("email_subscribe", "http://hoanganhfood.com/subcribe"));
		webConfigService.save(new WebConfig("password", "p@ssw0rds3cr3t"));
		webConfigService.save(new WebConfig("phone", "0904 523 351"));
		webConfigService.save(new WebConfig("address", "A4, Đền Lừ 2, Hoàng Văn Thụ, Hoàng Mai, Hà Nội"));
		webConfigService.save(new WebConfig("facebook", "https://facebook.com/{{your-page-facebook-id}}"));
		webConfigService.save(new WebConfig("youtube", "https://youtube.com/{{your-channel-id}}"));

		webConfigService.save(new WebConfig("footer1", "CHUYỂN PHÁT NHANH"));
		webConfigService.save(new WebConfig("home_introduction", "Được khởi xướng và vận hành bởi một nhóm người trẻ năng động, " +
				"cửa hàng bánh ngọt và đồ uống DELICIOUS đang dần khẳng định chỗ đứng trong thị trường đồ ăn giải " +
				"khát phong phú và đa dạng ở Hà Nội. Hướng tới đối tượng khách hàng là các bạn trẻ năng động, " +
				"chúng tôi quan tâm tới chất lượng và hình ảnh của sản phẩm."));

		webConfigService.save(new WebConfig("footer1_content", "Chúng tôi miễn phí với đơn hàng trên " +
				"<span> 350.000đ</span> tại Hà Nội và free ship toàn quốc với đơn hàng <span>700.000đ</span>"));

		webConfigService.save(new WebConfig("footer2", "LÀM BÁNH THEO YÊU CẦU"));
		webConfigService.save(new WebConfig("footer2_content", "Một chiếc bánh được thiết kế riêng biệt, " +
				"độc đáo dành cho người thân, bạn bè chắc hẳn sẽ là một món quà bất ngờ và đặc biệt nhất."));
		webConfigService.save(new WebConfig("footer3", "HỖ TRỢ KHÁCH HÀNG"));
		webConfigService.save(new WebConfig("footer3_content", "Trung tâm chăm sóc khách hàng của DELICIOUS, hotline " +
				"<span><a class=\"hotline\" href=\"tel:8497950862\">84979508629</a></span> để được hỗ trợ giải đáp."));
	}
}