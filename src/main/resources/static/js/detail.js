$(document).ready(function () {
	$("#gallery_02").owlCarousel({
		navigation : true,
		nav: true,
		navigationPage: false,
		navigationText : false,
		slideSpeed : 1000,
		pagination : true,
		dots: false,
		margin: 5,
		autoHeight:true,
		autoplay:false,
		autoplayTimeout:false,
		autoplayHoverPause:true,
		loop: false,
		responsive: {
			0: {
				items: 3
			},
			543: {
				items: 3
			},
			768: {
				items: 3
			},
			991: {
				items: 3
			},
			992: {
				items: 3
			},
			1200: {
				items: 4
			},
			1500: {
				items: 4
			}
		}
	});

	/*** xử lý active thumb -- ko variant ***/
	var thumbLargeimg = $('.details-product .large-image a').attr('href').split('?')[0];
	var thumMedium = $('#gallery_02 .owl-item .item a').find('img').attr('src');
	var url = [];

	$('#gallery_02 .owl-item .item').each(function(){
		var srcImg = '';
		$(this).find('a img').each(function(){
			var current = $(this);
			if(current.children().size() > 0) {return true;}
			srcImg += $(this).attr('src');
		});
		url.push(srcImg);
		var srcimage = $(this).find('a img').attr('src').split('?')[0];
		if (srcimage == thumbLargeimg) {
			$(this).find('a').addClass('active');
		} else {
			$(this).find('a').removeClass('active');
		}

	});
	$('#gallery_02 img, .swatch-element label').click(function(e){
		e.preventDefault();
		$('.large-image .checkurl img').attr('src',$(this).attr('data-img'));
	})
});


var ww = $(window).width();

var selectCallback = function(variant, selector) {


	$('.iwishAddWrapper').attr('data-variant',variant.id);

	if (variant) {

		var form = jQuery('#' + selector.domIdPrefix).closest('form');

		for (var i=0,length=variant.options.length; i<length; i++) {

			var radioButton = form.find('.swatch[data-option-index="' + i + '"] :radio[value="' + variant.options[i] +'"]');
			if (radioButton.size()) {
				radioButton.get(0).checked = true;
			}
			jQuery('.swatch[data-option-index="' + i + '"] :radio[value="' + variant.options[i] +'"]').removeClass('soldout').addClass('available').find(':radio').removeAttr('disabled');			
		}
	}

	/*** SKU ***/
	if (variant) {
		if (variant.sku){
			$('.variant-sku').html('<span class="first_status"><span class="a_name">' + 'Mã sản phẩm: ' + '</span>' + '<span class="status_name">' + variant.sku + '</span>&nbsp;&nbsp;'
												+ '<span class="hidden-xs">|</span>&nbsp;&nbsp;' + '</span>');
		} else {
			$('.variant-sku').html('<span class="first_status"><span class="a_name">' + 'Mã sản phẩm: ' + '</span>' + '<span class="status_name">' + 'Chưa có' + '</span>&nbsp;&nbsp;'
												+ '<span class="hidden-xs">|</span>&nbsp;&nbsp;' + '</span>');
		}
	}
	else {
		$('.variant-sku').empty();
	}


	var addToCart = jQuery('.form-product .btn-cart'),/*khai bao kiem tra addtocart: nut mua hang*/
		form = jQuery('.form-product .form_button_details'),	
		boxinput = jQuery('.form-product .form_button_details .quantity-option'),
		productPrice = jQuery('.details-pro .special-price .product-price'),
		qty = jQuery('.availabel'),/*lay so luog, trang thai hien co*/
		sale = jQuery('.details-pro .old-price .product-price-old'),
		comparePrice = jQuery('.details-pro .old-price .product-price-old'),/*gia khuyen mai*/
		priceTopbar  = jQuery('.product_info_price_value-final'),
		title = jQuery('.product_info_price_title'),/*ten thong tin neu dung modunle*/
		button = jQuery('.btn_buyNow'),/*cac var chi den dung cac class trong product*/
		metaInStock = '<link itemprop="availability" href="http://schema.org/InStock" />',
		metaOutStock = '<link itemprop="availability" href="http://schema.org/OutOfStock" />';

	/*** VAT ***/
	if (variant){
		if (variant.taxable){
			$('.taxable').removeClass('hidden').find('.vat').text('Đã bao gồm VAT');
		} else {
			$('.taxable').removeClass('hidden').find('.vat').text('Chưa bao gồm VAT');
		}
	}
	

	if (variant && variant.available) {		
		if(variant.inventory_management == "bizweb" || variant.inventory_management == "sapo"){
			if (variant.inventory_quantity != 0) {
				qty.html(metaInStock + 'Còn hàng');
			} else if (variant.inventory_quantity == ''){
				qty.html(metaInStock + 'Hết hàng');
			}
		} else {
			qty.html(metaInStock + 'Còn hàng');
		}
		button.addClass('add_to_cart').removeAttr('disabled').text('Thêm vào giỏ hàng');/*addtocart chay qua cs.js*/
		addToCart.removeAttr('disabled').find('span').text('Thêm vào giỏ hàng');	
		addToCart.removeClass('hidden');
		button.removeClass('hidden');
		boxinput.removeClass('hidden');
		if(variant.price == 0){			
			title.html('');
			button.addClass('hidden');
			priceTopbar.html('Liên hệ');
			productPrice.html('Liên hệ');	
			comparePrice.hide();
			form.addClass('hidden');
			sale.removeClass('sale');
			$('.taxable').addClass('hidden')
			if(variant.inventory_management == "bizweb" || variant.inventory_management == "sapo"){
				if (variant.inventory_quantity != 0) {
					qty.html(metaInStock + 'Còn hàng');					
				} else if (variant.inventory_quantity == ''){
					qty.html(metaOutStock + 'Hết hàng');
				}
			} else {
				qty.html(metaOutStock + 'Còn hàng');				
			}
		}else{			
			title.html('Giá bán:');
			priceTopbar.html(Bizweb.formatMoney(variant.price, "{{amount_no_decimals_with_comma_separator}}₫"));/*convert gia tu them dong sau gia*/
			form.removeClass('hidden');
			productPrice.html(Bizweb.formatMoney(variant.price, "{{amount_no_decimals_with_comma_separator}}₫"));
			// Also update and show the product's compare price if necessary
			if ( variant.compare_at_price > variant.price ) {
				comparePrice.html(Bizweb.formatMoney(variant.compare_at_price, "{{amount_no_decimals_with_comma_separator}}₫")).show();
				sale.addClass('sale');		
				if(variant.inventory_management == "bizweb" || variant.inventory_management == "sapo"){
					if (variant.inventory_quantity != 0) {
						qty.html(metaOutStock + 'Còn hàng');
					} else if (variant.inventory_quantity == ''){
						qty.html(metaOutStock + 'Còn hàng');
					}
				} else {
					qty.html(metaOutStock + 'Còn hàng');
				}
			} else {				
				comparePrice.hide();  
				sale.removeClass('sale');
				if(variant.inventory_management == "bizweb" || variant.inventory_management == "sapo"){
					if (variant.inventory_quantity != 0) {
						qty.html(metaOutStock + 'Còn hàng');
					} else if (variant.inventory_quantity == ''){
						qty.html(metaOutStock + 'Hết hàng');
					}
				} else {
					qty.html(metaOutStock + 'Còn hàng');
				}
			}       										
		}

	} else {	
		button.removeClass('add_to_cart').text('Hết hàng').attr('disabled', 'disabled');
		qty.html(metaOutStock + 'Hết hàng');
		addToCart.attr('disabled', 'disabled').find('span').text('Hết hàng');
		form.removeClass('hidden');
		boxinput.addClass('hidden');
		if(variant){
			if(variant.price != 0){
				title.html('Giá bán:');
				priceTopbar.html(Bizweb.formatMoney(variant.price, "{{amount_no_decimals_with_comma_separator}}₫"));
				form.removeClass('hidden');
				addToCart.addClass('hidden');
				productPrice.html(Bizweb.formatMoney(variant.price, "{{amount_no_decimals_with_comma_separator}}₫"));
				// Also update and show the product's compare price if necessary
				if ( variant.compare_at_price > variant.price ) {
					form.addClass('hidden');
					comparePrice.html(Bizweb.formatMoney(variant.compare_at_price, "{{amount_no_decimals_with_comma_separator}}₫")).show();
					sale.addClass('sale');
					if(variant.inventory_management == "bizweb"){
						if (variant.inventory_quantity != 0) {
							qty.html(metaOutStock + 'Còn hàng');
						} else if (variant.inventory_quantity == ''){
							qty.html(metaOutStock + 'Hết hàng');
							form.removeClass('hidden');
							addToCart.removeClass('hidden');
							button.removeClass('hidden');
							boxinput.addClass('hidden');
						}
					} else {
						qty.html(metaInStock + 'Còn hàng');
					}
				} else {
					comparePrice.hide();   
					sale.removeClass('sale');
					if(variant.inventory_management == "bizweb"){
						if (variant.inventory_quantity != 0) {
							qty.html(metaInStock + 'Còn hàng');
						} else if (variant.inventory_quantity == ''){
							qty.html(metaInStock + 'Hết hàng');
							form.removeClass('hidden');
							addToCart.removeClass('hidden');
							button.removeClass('hidden');
							boxinput.addClass('hidden');
							priceTopbar.html(Bizweb.formatMoney(variant.price, "{{amount_no_decimals_with_comma_separator}}₫")).show();
						}
					} else {
						qty.html(metaInStock + 'Còn hàng');
					}
				}     
			}else{
				title.html('Liên hệ');
				priceTopbar.hide();
				productPrice.html('Liên hệ');	
				comparePrice.hide();
				form.addClass('hidden');
				boxinput.addClass('hidden');
				sale.removeClass('sale');
				addToCart.addClass('hidden');
				button.addClass('hidden');
				$('.taxable').addClass('hidden')
			}
		}else{
			title.html('Liên hệ');
			priceTopbar.hide();
			button.addClass('hidden');
			productPrice.html('Liên hệ');	
			comparePrice.hide();
			form.addClass('hidden');
			boxinput.addClass('hidden');
			sale.removeClass('sale');
			addToCart.addClass('hidden');
			$('.taxable').addClass('hidden')
		}

	}
	 
	 /*begin variant image*/
	 if (variant && variant.image) {  
		 var originalImage = jQuery(".large-image img"); 
		 var newImage = variant.image;
		 var element = originalImage[0];
		 Bizweb.Image.switchImage(newImage, element, function (newImageSizedSrc, newImage, element) {
			 jQuery(element).parents('a').attr('href', newImageSizedSrc);
			 jQuery(element).attr('src', newImageSizedSrc);
			 jQuery('.pict').attr('src', newImageSizedSrc);
			 if (ww >= 1200){
				 $("#img_01").data('zoom-image', newImageSizedSrc).elevateZoom({
					 responsive: true,
					 gallery:'gallery_02',
					 cursor: 'pointer',
					 galleryActiveClass: "active"
				 });
				 $("#img_01").bind("click", function(e) {
					 var ez = $('.img_01').data('elevateZoom');
					 /* $.fancybox(ez.getGalleryList());
					 return false;*/
				 });
			 }

		 });

		 setTimeout(function(){
			 $('.checkurl').attr('href',$(this).attr('src'));
			 if (ww >= 1200){
				 $('.zoomContainer').remove();
				 $("#img_01").elevateZoom({
					 responsive: true,
					 gallery:'gallery_02',
					 cursor: 'pointer',
					 scrollZoom : false,
					 galleryActiveClass: "active"
				 });
			 }
		 },200);

	 } 
	  
	   };
	   jQuery(function($) {
		   if(variantsize == true ){

			   new Bizweb.OptionSelectors('product-selectors', {
				   product: productJson,
				   onVariantSelected: selectCallback,
				   enableHistoryState: true
			   });
		   }

		   // Add label if only one product option and it isn't 'Title'. Could be 'Size'.
		   if(productOptionsSize == 1){
			   $('.selector-wrapper:eq(0)').prepend('<label>'+ optionsFirst +'</label>');
		   }

		   // Hide selectors if we only have 1 variant and its title contains 'Default'.
		   if(cdefault == 1){
			   $('.selector-wrapper').hide();
		   } 
		   $('.selector-wrapper').css({
			   'text-align':'left',
			   'margin-bottom':'15px'
		   });
	   });

	   jQuery('.swatch :radio').change(function() {
		   var optionIndex = jQuery(this).closest('.swatch').attr('data-option-index');
		   var optionValue = jQuery(this).val();
		   jQuery(this)
			   .closest('form')
			   .find('.single-option-selector')
			   .eq(optionIndex)
			   .val(optionValue)
			   .trigger('change');
	   });
	   if (ww >= 1200){
		   $(document).ready(function() {
			   if($(window).width()>1200){
				   $('#img_01').elevateZoom({
					   gallery:'gallery_02', 
					   zoomWindowWidth:420,
					   zoomWindowHeight:500,
					   zoomWindowOffetx: 10,
					   easing : true,
					   scrollZoom : false,
					   cursor: 'pointer', 
					   galleryActiveClass: 'active', 
					   imageCrossfade: true
				   });
			   }
		   });
	   }
	   $(".dp-flex img").click(function(e){
		   e.preventDefault();
		   var hr = $(this).attr('data-src');
		   $('#img_01').attr('src',hr);
		   $('.pict').attr('src',hr);
		   $('.large_image_url').attr('href',hr);
		   $('#img_01').attr('data-zoom-image',hr);
	   });



	   $(".not-dqtab").each( function(e){
		   $(this).find('.tabs-title li:first-child').addClass('current');
		   $(this).find('.tab-content').first().addClass('current');

		   $(this).find('.tabs-title li').click(function(){
			   if($(window).width()>315){	
				   if($(this).hasClass('current')){
					   $(this).removeClass('current');
				   }else{
					   var tab_id = $(this).attr('data-tab');
					   var url = $(this).attr('data-url');
					   $(this).closest('.e-tabs').find('.tab-viewall').attr('href',url);

					   $(this).closest('.e-tabs').find('.tabs-title li').removeClass('current');
					   $(this).closest('.e-tabs').find('.tab-content').removeClass('current');

					   $(this).addClass('current');
					   $(this).closest('.e-tabs').find("#"+tab_id).addClass('current');
				   }
			   }else{
				   var tab_id = $(this).attr('data-tab');
				   var url = $(this).attr('data-url');
				   $(this).closest('.e-tabs').find('.tab-viewall').attr('href',url);

				   $(this).closest('.e-tabs').find('.tabs-title li').removeClass('current');
				   $(this).closest('.e-tabs').find('.tab-content').removeClass('current');

				   $(this).addClass('current');
				   $(this).closest('.e-tabs').find("#"+tab_id).addClass('current');

			   }

		   });    
	   });
	   function scrollToxx() {
		   $('html, body').animate({ scrollTop: $('.product-tab.e-tabs').offset().top }, 'slow');
		   $('.product-tab .tab-link').removeClass('current');
		   $('.product-tab .tab-link[data-tab=tab-3]').addClass('current');
		   $('.product-tab .tab-content').removeClass('current');
		   $('.product-tab .tab-content#tab-3').addClass('current');

		   return false;
	   }
	   /*For recent product*/
	   var alias = '';
	   /*end*/
	   if (ww >= 1200){
		   $(document).ready(function() {
			   $('#img_01').elevateZoom({
				   gallery:'gallery_02', 
				   zoomWindowWidth:420,
				   zoomWindowHeight:500,
				   zoomWindowOffetx: 10,
				   easing : true,
				   scrollZoom : false,
				   cursor: 'pointer', 
				   galleryActiveClass: 'active', 
				   imageCrossfade: true

			   });
		   });
	   }
	   $('#gallery_00 img, .swatch-element label').click(function(e){

		   $('.checkurl').attr('href',$(this).attr('src'));
		   if (ww >= 1200){
			   setTimeout(function(){
				   $('.zoomContainer').remove();				
				   $('#zoom_01').elevateZoom({
					   gallery:'gallery_02', 
					   zoomWindowWidth:420,
					   zoomWindowHeight:500,
					   zoomWindowOffetx: 10,
					   easing : true,
					   scrollZoom : false,
					   cursor: 'pointer', 
					   galleryActiveClass: 'active', 
					   imageCrossfade: true
				   });
			   },300);
		   }
	   });