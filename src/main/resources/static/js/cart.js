function addCart(data) {
    $.ajax({
        url: '/api/v1/cart/add',
        dataType: 'json',
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data),
        success: function (responseData, textStatus, jQxhr) {
            updateCart();
            console.log(JSON.stringify(responseData));
            var product = responseData['object'];
            showPopupAddToCart(product)
        },
        error: function (jqXhr, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
}

function updateCartItem(data) {
    $.ajax({
        url: '/api/v1/cart/update',
        dataType: 'json',
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data),
        success: function (responseData, textStatus, jQxhr) {
            updateCart();
        },
        error: function (jqXhr, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
}

function showPopupAddToCart(product) {
    var popupCart = $('#popup-cart');
    popupCart.empty();

    $('<div class="modal-dialog">' +
        '       <div class="modal-content">' +
        '            <div class="modal-header">' +
        '                <button type="button" class="close" data-dismiss="modal" aria-label="Close" title="Close" style="position: relative; z-index: 9;">' +
        '                    <span aria-hidden="true">×</span>' +
        '                </button>' +
        '                <h4 class="modal-title">Sản phẩm đã được thêm vào giỏ hàng</h4>' +
        '            </div>' +
        '            <div class="modal-body">' +
        '                <div class="media">' +
        '                    <div class="media-left">' +
        '                        <div class="thumb-1x1"><img width="140px" src="' + product.featureImage + '" alt="' + product.name +'"></div>' +
        '                    </div>' +
        '                    <div class="media-body">' +
        '                        <div class="product-title">' + product.name + '</div>' +
        '                        <div class="product-new-price"><span>' + product.price+ 'đ</span></div>' +
        '                        <button class="btn btn-block btn-outline-red" data-dismiss="modal">Tiếp tục mua hàng</button>' +
        '                        <a href="/checkout" class="btn btn-block btn-red">Tiến hành thanh toán</a>' +
        '                    </div>' +
        '                </div>' +
        '            </div>' +
        '        </div>' +
        '    </div>').appendTo(popupCart);

    popupCart.modal('show')
}

function updateCartFromForm(data) {
    var numberOfItem = 0;
    if (data.items.length === 0) {
        $(".count_item").text(numberOfItem);
    } else {
        $.each(data.items, function (index, item) {
            numberOfItem = numberOfItem + item.quantity;
        });
        $(".count_item").text(numberOfItem);
    }

    var table = $('.mini-products-list');
    table.empty();

    if (numberOfItem > 0) {
        var sub_total = 0;
        $('<ul class="list-item-cart"></ul>').appendTo(table);
        //append list item to cart on hover
        $.each(data.items, function (index, item) {
            sub_total = sub_total + item.product.price * item.quantity;
            var slug = '/' + item.product.category.slug + '/' + item.product.slug + '/';
            $('<li class="item productid-' + item.id + '">' +
                '<div class="wrap_item">' +
                '<a class="product-image" href="' + slug + '" title="' + item.product.name + '">' +
                '<img alt="' + item.product.name + '" src="' + item.product.featureImage + '" width="100">' +
                '</a>' +
                '<div class="detail-item">' +
                '<div class="product-details"> ' +
                '<a data-id="' + item.product.id + '" title="Xóa" class="remove-item-cart fa fa-close" href="javascript:;">&nbsp;</a>' +
                '<h3 class="product-name"> ' +
                '<a href="' + slug + '" title="' + item.product.name + '">' + item.product.name + '</a>' +
                '</h3>' +
                '</div>' +
                '<div class="product-details-bottom">' +
                '<span class="price pricechange">' + item.product.price + '₫</span>' +
                '<span class="hidden quaty item_quanty_count"> x ' + item.quantity + '</span>' +
                '<div class="quantity-select qty_drop_cart">' +
                '<input class="productId" type="hidden" name="productId" value="' + item.product.id + '">' +
                '<button onclick="var result = document.getElementById(\'qty' + item.product.id + '\'); var qty' + item.product.id + ' = result.value; if( !isNaN(qty' + item.product.id + ') && qty' + item.product.id + ' > 1) result.value--;return false;" class="btn_reduced reduced items-count btn-minus" type="button">–</button>' +
                '<input type="text" maxlength="12" readonly="" class="input-text number-sidebar qty' + item.product.id + '" id="qty' + item.product.id + '" name="qty" size="4" value="' + item.quantity + '">' +
                '<button onclick="var result = document.getElementById(\'qty' + item.product.id + '\'); var qty' + item.product.id + ' = result.value; if( !isNaN( qty' + item.product.id + ')) result.value++; return false;" class="btn_increase increase items-count btn-plus" type="button">+</button>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '<div class="border">' +
                '</div>' +
                '</div>' +
                '</li>')
                .appendTo(table.children('.list-item-cart'));
        });
        $('<div class="wrap_total"><div class="top-subtotal hidden">Phí vận chuyển: <span class="pricex">Tính khi thanh toán</span></div><div class="top-subtotal">Thành tiền: <span class="price">' + sub_total + '₫</span></div></div>').appendTo(table);
        $('<div class="wrap_button"><div class="actions"><a href="/cart" class="btn btn-gray btn-cart-page pink"><span>Giỏ hàng</span></a> <a href="/checkout" class="btn btn-gray btn-checkout pink"><span>Thanh toán</span></a> </div></div>').appendTo(table);
    } else {
        $('<div class="no-item"><p>Không có sản phẩm nào.</p></div>').appendTo(table);
    }

    // updateCartDesc(cart);
    var numInput = document.querySelector('#cart-sidebar .qty_drop_cart input.input-text');
    if (numInput != null) {
        // Listen for input event on numInput.
        numInput.addEventListener('input', function () {
            // Let's match only digits.
            var num = this.value.match(/^\d+$/);
            if (num === 0) {
                // If we have no match, value will be empty.
                this.value = 1;
            }
            if (num === null) {
                // If we have no match, value will be empty.
                this.value = "1";
            }
        }, false)
    }
}

$("form").submit(function (e) {
    e.preventDefault();
    var productId = $("input[name='productId']", this).val();
    var quantity = $("input[name='quantity']", this).val();
    var token =  getToken();
    if (quantity === undefined) {
        quantity = 1;
    }
    var data = {'token': token, 'productId': productId, 'quantity': quantity}
    addCart(data);
    updateCart();
});

function onCartRemoveClick(cart, productId) {
    $.each(cart, function (key, value) {
        if (key === 'items') {
            $.each(value, function (i, item) {
                if (item.product.id === productId) {
                    $('.productid-' + productId).remove();
                }
            });
        }
    });
    // updateCartDesc(cart);
}

$(document).on("click", ".remove-item-cart", function(){
    var productId = $.attr(this, 'data-id');
    var data = {'token': getToken(), 'productId': productId, 'quantity': 0};
    updateCartItem(data)
});

//remove on page
$('.remove-item-cart').on("click", function(){
    var productId = $.attr(this, 'data-id');
    var data = {'token': getToken(), 'productId': productId, 'quantity': 0};
    updateCartItem(data)
    $(this).parents('.item-cart').remove()
});

$(document).on("click", ".items-count", function(){
    $(this).siblings().each(function (index, item) {
        console.log(item)
    });
    var productId = $(this).siblings('.productId').val();
    var quantity = $(this).siblings('input[name="qty"]').val();
    var data = {'token': getToken(), 'productId': productId, 'quantity': quantity};
    updateCartItem(data);
});

function getToken() {
    var token = Cookies.get('token');
    if (token === undefined) {
        token = ''
    }
    return token;
}

function updateCart() {
    $.ajax({
        url: '/api/v1/cart?token=' + getToken(),
        dataType: 'json',
        type: 'GET',
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            updateCartFromForm(data);
        },
        error: function (jqXhr, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
}

$(document).ready(function () {
    updateCart();
});


// Bizweb.updateCartPopupForm = function (cart, cart_summary_id, cart_count_id) {
//     if ((typeof cart_summary_id) === 'string') {
//         var cart_summary = jQuery(cart_summary_id);
//         if (cart_summary.length) {
//             // Start from scratch.
//             cart_summary.empty();
//             // Pull it all out.
//             jQuery.each(cart, function (key, value) {
//                 if (key === 'items') {
//                     var table = jQuery(cart_summary_id);
//                     if (value.length) {
//                         jQuery.each(value, function (i, item) {
//                             var src = item.image;
//                             if (src == null) {
//                                 src = "https://bizweb.dktcdn.net/thumb/large/assets/themes_support/noimage.gif";
//                             }
//                             var buttonQty = "";
//                             if (item.quantity == '1') {
//                                 buttonQty = 'disabled';
//                             } else {
//                                 buttonQty = '';
//                             }
//                             var title_vas = item.variant_title;
//                             if (title_vas == 'Default Title') {
//                                 title_vas = "";
//                             }
//                             else {
//                                 title_vas = item.variant_title;
//                             }
//                             var pageCartItem = '<div class="item-popup productid-' + item.variant_id + '">'
//                                 + '<div style="width: 15%;" class="border height image_ text-left"><div class="item-image">'
//                                 + '<a class="product-image" href="' + item.url + '" title="' + item.name + '"><img alt="' + item.name + '" src="' + src + '"width="' + '90' + '"\></a>'
//                                 + '</div></div>'
//                                 + '<div style="width:38.8%;" class="height text-left"><div class="item-info"><p class="item-name"><a class="text2line" href="' + item.url + '" title="' + item.name + '">' + item.title + '</a></p>'
//                                 + '<span class="variant-title-popup">' + title_vas + '</span>'
//                                 + '<a href="javascript:;" class="remove-item-cart" title="Xóa sản phẩm" data-id="' + item.variant_id + '"><i class="fa fa-close"></i>&nbsp;&nbsp;Xoá sản phẩm</a>'
//                                 + '<p class="addpass" style="color:#fff;margin:0px;">' + item.variant_id + '</p>'
//                                 + '</div></div>'
//                                 + '<div style="width: 15.2%;" class="border height text-center"><div class="item-price"><span class="price pricechange">' + Bizweb.formatMoney(item.price, "{{amount_no_decimals_with_comma_separator}}₫") + '</span>'
//                                 + '</div></div><div style="width: 15.4%;" class="border height text-center"><div class="qty_thuongdq check_"><input class="variantID" type="hidden" name="variantId" value="' + item.variant_id + '">'
//                                 + '<button onClick="var result = document.getElementById(\'qtyItemP' + item.variant_id + '\'); var qtyItemP' + item.variant_id + ' = result.value; if( !isNaN( qtyItemP' + item.variant_id + ' ) &amp;&amp; qtyItemP' + item.variant_id + ' &gt; 1 ) result.value--;return false;" ' + buttonQty + ' class="num1 reduced items-count btn-minus" type="button">-</button>'
//                                 + '<input type="text" maxlength="12" min="0" readonly class="input-text number-sidebar qtyItemP' + item.variant_id + '" id="qtyItemP' + item.variant_id + '" name="Lines" id="updates_' + item.variant_id + '" size="4" value="' + item.quantity + '">'
//                                 + '<button onClick="var result = document.getElementById(\'qtyItemP' + item.variant_id + '\'); var qtyItemP' + item.variant_id + ' = result.value; if( !isNaN( qtyItemP' + item.variant_id + ' )) result.value++;return false;" class="num2 increase items-count btn-plus" type="button">+</button></div></div>'
//                                 + '<div style="width: 15%;" class="border height text-center"><span class="cart-price"> <span class="price">' + Bizweb.formatMoney(item.price * item.quantity, "{{amount_no_decimals_with_comma_separator}}₫") + '</span> </span></div>'
//                                 + '</div>';
//                             jQuery(pageCartItem).appendTo(table);
//                             $('.link_product').text();
//                         });
//                     }
//                 }
//             });
//         }
//     }
//     jQuery('.total-price').html(Bizweb.formatMoney(cart.total_price, "{{amount_no_decimals_with_comma_separator}}₫"));
//
//     updateCartDesc(cart);
//
// }

// Bizweb.updateCartPageFormMobile = function (cart, cart_summary_id, cart_count_id) {
//     if ((typeof cart_summary_id) === 'string') {
//         var cart_summary = jQuery(cart_summary_id);
//         if (cart_summary.length) {
//             // Start from scratch.
//             cart_summary.empty();
//             // Pull it all out.
//             jQuery.each(cart, function (key, value) {
//                 if (key === 'items') {
//
//                     var table = jQuery(cart_summary_id);
//                     if (value.length) {
//                         jQuery('<div class="cart_page_mobile content-product-list"></div>').appendTo(table);
//                         jQuery.each(value, function (i, item) {
//                             if (item.image != null) {
//                                 var src = Bizweb.resizeImage(item.image, 'small');
//                             } else {
//                                 var src = "https://bizweb.dktcdn.net/thumb/large/assets/themes_support/noimage.gif";
//                             }
//                             jQuery('<div class="item-product item-mobile-cart item productid-' + item.variant_id + ' "><div class="item-product-cart-mobile"><a href="' + item.url + '">	<a class="product-images1" href="' + item.url + '"  title="' + item.name + '"><img width="80" height="150" alt="' + item.name + '" src="' + src + '" alt="' + item.name + '"></a></a></div>'
//                                 + '<div class="title-product-cart-mobile"><h3><a href="' + item.url + '" title="' + item.name + '">' + item.name + '</a></h3><p>Giá: <span class="pricechange">' + Bizweb.formatMoney(item.price, "{{amount_no_decimals_with_comma_separator}}₫") + '</span></p></div>'
//                                 + '<div class="select-item-qty-mobile"><div class="txt_center in_put check_">'
//                                 + '<input class="variantID" type="hidden" name="variantId" value="' + item.variant_id + '"><button onClick="var result = document.getElementById(\'qtyMobile' + item.variant_id + '\'); var qtyMobile' + item.variant_id + ' = result.value; if( !isNaN( qtyMobile' + item.variant_id + ' ) &amp;&amp; qtyMobile' + item.variant_id + ' &gt; 0 ) result.value--;return false;" class="reduced items-count btn-minus" type="button">–</button><input type="number" maxlength="12" min="1" readonly class="check_number_here input-text mobile_input number-sidebar qtyMobile' + item.variant_id + '" id="qtyMobile' + item.variant_id + '" name="Lines" id="updates_' + item.variant_id + '" size="4" value="' + item.quantity + '"><button onClick="var result = document.getElementById(\'qtyMobile' + item.variant_id + '\'); var qtyMobile' + item.variant_id + ' = result.value; if( !isNaN( qtyMobile' + item.variant_id + ' )) result.value++;return false;" class="increase items-count btn-plus" type="button">+</button></div>'
//                                 + '<a class="button remove-item remove-item-cart" href="javascript:;" data-id="' + item.variant_id + '">Xoá</a></div>').appendTo(table.children('.content-product-list'));
//                         });
//
//                         jQuery('<div class="header-cart-price" style=""><div class="title-cart a-center"><span class="total_mobile a-center">Tổng tiền: <span class=" totals_price_mobile">' + Bizweb.formatMoney(cart.total_price, "{{amount_no_decimals_with_comma_separator}}₫") + '</span><span></div>'
//                             + '<div class="checkout"><button class="btn-proceed-checkout-mobile" title="Tiến hành thanh toán" type="button" onclick="window.location.href=\'/checkout\'">'
//                             + '<span>Tiến hành thanh toán</span></button>'
//                             + '<button class="btn btn-white contin" title="Tiếp tục mua hàng" type="button" onclick="window.location.href=\'/san-pham\'"><span>Tiếp tục mua hàng</span></button>'
//                             + '</div></div>').appendTo(table);
//                     } else {
//                         jQuery('<p class="hidden-xs-down col-xs-12">Không có sản phẩm nào. Quay lại <a href="/san-pham" style="color:;">cửa hàng</a> để tiếp tục mua sắm.</p>').appendTo(table);
//                         jQuery('.cart_desktop_page').css('min-height', 'auto');
//                     }
//
//                 }
//             });
//         }
//     }
//
//     updateCartDesc(cart);
//
//
// }


// function updateCartDesc(data) {
//     var $cartPrice = Bizweb.formatMoney(data.total_price, "{{amount_no_decimals_with_comma_separator}}₫"),
//         $cartMobile = $('#header .cart-mobile .quantity-product'),
//         $cartDesktop = $('.count_item_pr'),
//         $cartDesktopList = $('.cart-counter-list'),
//         $cartPopup = $('.cart-popup-count');
//
//     switch (data.item_count) {
//         case 0:
//             $cartMobile.text('0');
//             $cartDesktop.text('0');
//             $cartDesktopList.text('0');
//             $cartPopup.text('0');
//
//             break;
//         case 1:
//             $cartMobile.text('1');
//             $cartDesktop.text('1');
//             $cartDesktopList.text('1');
//             $cartPopup.text('1');
//
//             break;
//         default:
//             $cartMobile.text(data.item_count);
//             $cartDesktop.text(data.item_count);
//             $cartDesktopList.text(data.item_count);
//             $cartPopup.text(data.item_count);
//
//             break;
//     }
//     $('.top-cart-content .top-subtotal .price, aside.sidebar .block-cart .subtotal .price, .popup-total .total-price').html($cartPrice);
//     $('.popup-total .total-price').html($cartPrice);
//     $('.shopping-cart-table-total .totals_price').html($cartPrice);
//     $('.header-cart-price .totals_price_mobile').html($cartPrice);
//     $('.cartCount').html(data.item_count);
// }
//
// Bizweb.onCartUpdate = function (cart) {
//     Bizweb.updateCartFromForm(cart, '.mini-products-list');
//     Bizweb.updateCartPopupForm(cart, '#popup-cart-desktop .tbody-popup');
//
// };
// Bizweb.onCartUpdateClick = function (cart, variantId) {
//     jQuery.each(cart, function (key, value) {
//         if (key === 'items') {
//             jQuery.each(value, function (i, item) {
//                 if (item.variant_id == variantId) {
//                     $('.productid-' + variantId).find('.pricechange').html(Bizweb.formatMoney(item.price, "{{amount_no_decimals_with_comma_separator}}₫"));
//                     $('.productid-' + variantId).find('.cart-price span.price').html(Bizweb.formatMoney(item.price * item.quantity, "{{amount_no_decimals_with_comma_separator}}₫"));
//                     $('.productid-' + variantId).find('.items-count').prop("disabled", false);
//                     $('.productid-' + variantId).find('.number-sidebar').prop("disabled", false);
//                     $('.productid-' + variantId + ' .number-sidebar').val(item.quantity);
//                     if (item.quantity == '1') {
//                         $('.productid-' + variantId).find('.items-count.btn-minus').prop("disabled", true);
//                     }
//                 }
//             });
//         }
//     });
//     updateCartDesc(cart);
// }