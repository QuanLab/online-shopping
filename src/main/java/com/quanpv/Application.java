package com.quanpv;

import com.quanpv.model.*;
import com.quanpv.service.*;
import com.quanpv.storage.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
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
	@Autowired
	private PostService postService;

	@Override
	public void run(String... strings) {
		setupDefaultConfig();
		Category category = new Category("Thời trang nam", "Thoi trang danh cho nam");
		Category category2 = new Category("Thời trang nữ", "Thoi trang danh cho nu");
		Category category3 = new Category("Đồng hồ", "Đồng hồ cao cấp dành cho doanh nhân");
		category3.setSlug("dong-ho");

		String imageList = "/images/products/try2-jpeg.jpg|/images/products/try4-jpeg.jpg";
		Product product = new Product("", "Áo phông nam", "Sản phẩm mới về", "/images/products/try2-jpeg.jpg", imageList, 120000, 12 );
		product.setSalePrice(115000);
		product.setFeature(true);
		product.setPopular(true);
		Product product2 = new Product("", "Váy ngắn", "Sản phẩm mới về", "/images/products/try13.jpg", "", 110000, 25);
		product2.setFeature(true);
		Product product3 = new Product("", "Quần Jean nam", "Sản phẩm mới về", "/images/products/try4-jpeg.jpg",  "", 150000, 25);
		product3.setFeature(true);
		Product product4 = new Product("", "Quần âu nam", "Sản phẩm mới về","/images/products/try12-jpeg.jpg", "", 280000, 35);
		product4.setFeature(true);

		Product product5 = new Product("", "Áo sơ mi", "Sản phẩm nhập khẩu về", "/images/products/try15-jpeg.jpg", "product5.jpg", 210000, 25);

		Product product6 = new Product("", "Áo phông nam", "Sản phẩm mới về", "/images/products/try2-jpeg.jpg", "", 120000, 12 );
		product6.setFeature(true);
		Product product7 = new Product("", "Áo bánh bèo", "Sản phẩm mới về", "/images/products/try12-jpeg.jpg", "", 0, 25);
		product7.setFeature(true);
		product7.setPopular(true);

		Product product8 = new Product("", "Áo phông nam", "Sản phẩm mới về", "/images/products/try2-jpeg.jpg", "", 120000, 12 );
		product8.setSalePrice(115000);
		product8.setFeature(true);
		Product product9 = new Product("", "Váy ngắn", "Sản phẩm mới về", "/images/products/try13.jpg", "", 110000, 25);
		product9.setFeature(true);
		product9.setPopular(true);
		Product product10 = new Product("", "Quần Jean nam", "Sản phẩm mới về", "/images/products/try4-jpeg.jpg",  "", 150000, 25);
		product10.setFeature(true);
		product10.setPopular(true);
		Product product11 = new Product("", "Quần âu nam", "Sản phẩm mới về","/images/products/try12-jpeg.jpg", "", 280000, 35);
		product11.setFeature(true);

		Product product12 = new Product("", "Áo sơ mi", "Sản phẩm nhập khẩu về", "/images/products/try15-jpeg.jpg", "product5.jpg", 210000, 25);

		product.setCategory(category);
		product2.setCategory(category2);
		product3.setCategory(category);
		product4.setCategory(category);
		product5.setCategory(category2);
		product6.setCategory(category2);
		product7.setCategory(category);
		product8.setCategory(category3);
		product9.setCategory(category);
		product10.setCategory(category3);
		product11.setCategory(category);
		product12.setCategory(category3);

		categoryService.save(category);
		categoryService.save(category2);
		categoryService.save(category3);

		productService.save(product);
		productService.save(product2);
		productService.save(product3);
		productService.save(product4);
		productService.save(product5);
		productService.save(product6);
		productService.save(product7);
		productService.save(product8);
		productService.save(product9);
		productService.save(product10);
		productService.save(product11);
		productService.save(product12);

		Post post = new Post();
		post.setTitle("Những lợi ích tuyệt vời của dâu tây mà không phải ai cũng biết");
		post.setContent("<p>Xin chao cac ban, day laf bai viet mau</p><p><Xuong dong cai nao/p>");
		post.setFeaturedImage("/images/products/pexels-photo.jpeg");
		post.setTags("tag mau 2");
		postService.save(post);

		Post post2 = new Post();
		post2.setTitle("Trà hoa cúc chuẩn vị thưởng thức vào mỗi sáng mai");
		post2.setContent("Thơm ngon và ngọt ngào, dâu tây giàu chất dinh dưỡng tự nhiên có lượng đường thấp, ít calo và mang lại nhiều lợi ích cho sức khỏe.\n" +
				"\n" +
				"Dâu tây giúp làm giảm triệu chứng của cảm lạnh và tăng cường hệ miễn dịch: Dâu tây rất giàu vitamin C, một vitamin đóng một vai trò quan trọng trong việc hỗ trợ một hệ miễn dịch khỏe mạnh. Mặc dù cơ thể của chúng ta không thể sản sinh ra vitamin C, chúng ta có thể bổ sung nó bằng cách ăn các thực phẩm như dâu tây. Ăn 8 quả dâu tây mỗi ngày cung cấp nhiều vitamin C hơn cả cam.\n" +
				"\n" +
				"Dâu tây giúp kiểm soát cân nặng: Theo một nghiên cứu gần đây, ăn dâu tây có thể giúp ngăn ngừa tăng cân và là một trong nhiều trái cây nếu ăn hàng ngày có thể giúp giảm cân. Nghiên cứu ghi nhận rằng flavonoid trong dâu tây có thể ngăn ngừa sự tăng cân do tuổi tác bằng cách kích thích sự trao đổi chất và làm giảm sự thèm ăn. Do đó, dâu tây có thể giúp bạn giảm cân.");
		post2.setFeaturedImage("/images/products/pexels-photo.jpeg");
		post2.setTags("tag mau");
		postService.save(post2);
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

		webConfigService.save(new WebConfig("home_introduction", "Được khởi xướng và vận hành bởi một nhóm người trẻ năng động, " +
				"cửa hàng bánh ngọt và đồ uống DELICIOUS đang dần khẳng định chỗ đứng trong thị trường đồ ăn giải " +
				"khát phong phú và đa dạng ở Hà Nội. Hướng tới đối tượng khách hàng là các bạn trẻ năng động, " +
				"chúng tôi quan tâm tới chất lượng và hình ảnh của sản phẩm."));

		webConfigService.save(new WebConfig("footer1", "CHUYỂN PHÁT NHANH"));

		webConfigService.save(new WebConfig("footer1_content", "Chúng tôi miễn phí với đơn hàng trên " +
				"<span> 350.000đ</span> tại Hà Nội và free ship toàn quốc với đơn hàng <span>700.000đ</span>"));

		webConfigService.save(new WebConfig("footer2", "LÀM BÁNH THEO YÊU CẦU"));
		webConfigService.save(new WebConfig("footer2_content", "Một chiếc bánh được thiết kế riêng biệt, " +
				"độc đáo dành cho người thân, bạn bè chắc hẳn sẽ là một món quà bất ngờ và đặc biệt nhất."));
		webConfigService.save(new WebConfig("footer3", "HỖ TRỢ KHÁCH HÀNG"));
		webConfigService.save(new WebConfig("footer3_content", "Trung tâm chăm sóc khách hàng của DELICIOUS, hotline " +
				"<span><a class=\"hotline\" href=\"tel:8497950862\">84979508629</a></span> để được hỗ trợ giải đáp."));

		webConfigService.save(new WebConfig("popular_product", "Sản phẩm ưa chuộng"));
		webConfigService.save(new WebConfig("popular_product_url", "/san-pham-ua-chuong"));

		webConfigService.save(new WebConfig("featured_product", "Sản phẩm nổi bật"));
		webConfigService.save(new WebConfig("featured_product_url", "/san-pham-noi-bat"));

		webConfigService.save(new WebConfig("new_product", "Sản phẩm mới"));
		webConfigService.save(new WebConfig("new_product_url", "/san-pham-moi"));
		String about = "<p><span style=\"font-size:14px;\"><span style=\"font-family:Arial,Helvetica,sans-serif;\"><strong>Delicious CAKE &amp;&nbsp;DRINK</strong>&nbsp; được biết đến là một thương hiệu bánh ngọt và nước uống hấp dẫn cho mọi đối tượng người Việt.&nbsp;</span></span></p>\n" +
				"                        <p><span style=\"font-size:14px;\"><span style=\"font-family:Arial,Helvetica,sans-serif;\">Trước tiên là sự thay đổi về thiết kế đã đem lại sự trẻ trung và sống động cho&nbsp;<strong>Delicious CAKE &amp;&nbsp;DRINK</strong>. Toàn bộ chuỗi&nbsp;cửa hàng <strong>Delicious CAKE &amp;&nbsp;DRINK</strong> trở nên bắt mắt hơn với trần nhà màu trắng và hệ thống đèn chiếu sáng hiện đại. Các cụm đèn màu cam và trắng gắn trên trần cũng đem lại cảm giác năng động nhưng không kém phần gần gũi cho những thực khách đặt chân đến với nơi này. Những sắc màu trẻ trung và họa tiết trên các tủ bánh cũng đem lại cảm giác thích thú khi khách hàng nhìn ngắm và lựa chọn từng chiếc bánh thật ngon mang tên <strong>Delicious</strong>.</span></span></p>\n" +
				"                        <p><span style=\"font-size:14px;\"><span style=\"font-family:Arial,Helvetica,sans-serif;\">Thay đổi thứ hai chính là sự bắt kịp với xu hướng thời đại của thương hiệu <strong>Delicious CAKE &amp;&nbsp;DRINK</strong>. 20 chi nhánh hiện nay đang phát triển theo mô hình kết hợp giữa bánh ngọt và nước uống, tạo không gian mua sắm tiện nghi, nơi thư giãn thoải mái cho tất cả mọi người. Các nhóm bạn hoặc đại gia đình có thể chọn <strong>Delicious CAKE &amp;&nbsp;DRINK</strong> làm điểm hẹn lý thú vào thời gian rảnh rỗi. Trong một không gian sang trọng và ấm cúng, mọi người ngồi quây quần bên nhau nhâm nhi những chiếc bánh ngọt ngon miệng cùng với tách café, trà nóng hoặc ly trà sữa thơm ngon, để cảm nhận hết những giá trị tuyệt vời mà <strong>Delicious CAKE &amp;&nbsp;DRINK</strong> đem lại.</span></span></p>\n" +
				"                        <p><span style=\"font-size:14px;\"><span style=\"font-family:Arial,Helvetica,sans-serif;\">Những sản phẩm bánh mới nhất của thương hiệu <strong>Delicious CAKE &amp;&nbsp;DRINK</strong> như Bánh kem Chocolate nghệ thuật, bánh kem Black Forest, Milky Bun, Blubberry Polo thơm lừng, bánh phô mai, bánh kem Chocolate cao cấp, bánh Tiramisu đẳng cấp… Về nước uống phục vụ quý khách, chuỗi cửa hàng <strong>Delicious CAKE &amp;&nbsp;DRINK</strong> hân hạnh giới thiệu thực đơn là các loại café, trà nóng, các loại trà sữa thích hợp cho giới trẻ,… Thật tuyệt vời khi vị ngọt ngào tinh tế của bánh ngọt hòa quyện vào vị thơm lừng của các loại thức uống ở <strong>Delicious CAKE &amp;&nbsp;DRINK</strong>.</span></span></p>\n" +
				"                        <p><span style=\"font-size:14px;\"><span style=\"font-family:Arial,Helvetica,sans-serif;\">Sự sành điệu thuộc về những người biết lựa chọn đúng gu thưởng thức ẩm thực của chính mình.</span></span></p>\n" +
				"                        <p><span style=\"font-size:14px;\"><span style=\"font-family:Arial,Helvetica,sans-serif;\">Cảm ơn vì sự tin yêu dành cho&nbsp;<strong>Delicious CAKE &amp;&nbsp;DRINK!</strong></span></span></p>";

		webConfigService.save(new WebConfig("about", about));
	}
}