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


function updateCartFromPage(data) {
    var numberOfItem = 0;
    if (data.items.length === 0) {
        $(".count_item").text(numberOfItem);
    } else {
        $.each(data.items, function (index, item) {
            numberOfItem = numberOfItem + item.quantity;
        });
        $(".count_item").text(numberOfItem);
    }

    var table = $('.cart-tbody');
    table.empty();
    var cart_page_mobile = $('.cart_page_mobile');
    cart_page_mobile.empty();
    var sub_total = 0;

    if (numberOfItem > 0) {
        //append list item to cart on hover
        $.each(data.items, function (index, item) {
            var item_sub_total = item.product.price * item.quantity;
            sub_total = sub_total + item_sub_total;
            var slug = '/' + item.product.category.slug + '/' + item.product.slug + '/';
            $('<div class="item-cart productid-' +  item.product.id +'">' +
                '   <div style="width: 18%" class="image">' +
                '       <a class="product-image" title="'+ item.product.name + '" href="' + slug +'">' +
                '           <img width="75" height="auto" alt="' + item.product.name +'" src="' + item.product.featureImage +'">' +
                '       </a>' +
                '   </div>' +
                '   <div style="width: 32%" class="a-center">' +
                '       <h3 class="product-name"> ' +
                '           <a href="' + slug + '" title="' + item.product.name + '">' + item.product.name + '</a>' +
                '       </h3>' +
                '   </div>' +
                '   <div style="width: 17%" class="a-center">' +
                '       <span class="item-price"> ' +
                '           <span class="price pricechange">'  + item.product.price + '₫</span>' +
                '       </span>' +
                '   </div>' +
                '   <div style="width: 14%" class="a-center">' +
                '       <div class="input_qty_pr">' +
                '           <input class="productId" type="hidden" name="productId" value="' + item.product.id + '">' +
                '           <input type="text" maxlength="12" readonly="" min="0" ' +
                '               class="check_number_here input-text number-sidebar input_pop input_pop qtyItem' + item.product.id  + '" ' +
                '              id="qtyItem' + item.product.id +'" name="qty" size="4" value="' + item.quantity +'">' +
                '           <button onclick="var result = document.getElementById(\'qtyItem' + item.product.id +'\'); var qtyItem' + item.product.id + ' = result.value; if( !isNaN( qtyItem' +  item.product.id  + ' )) result.value++;return false;" class="increase_pop items-count btn-plus" type="button">+</button>' +
                '           <button onclick="var result = document.getElementById(\'qtyItem' + item.product.id + '\'); var qtyItem' +  item.product.id  + ' = result.value; if( !isNaN( qtyItem'+  item.product.id  +' ) &amp;&amp; qtyItem'+  item.product.id  +' > 1 ) result.value--;return false;" class="reduced_pop items-count btn-minus" type="button">-</button>' +
                '       </div>' +
                '       </div>' +
                '       <div style="width: 14%" class="a-center">' +
                '           <span class="cart-price"> ' +
                '               <span class="price">' +  item_sub_total + '₫</span> ' +
                '           </span>' +
                '       </div>' +
                '    <div style="width: 5%" class="a-center">' +
                '       <a class="remove-itemx remove-item-cart" title="Xóa" href="javascript:;" data-id="'+ item.product.id +'">' +
                '           <span><i class="fa fa-trash-o"></i></span>' +
                '       </a>' +
                '   </div>' +
                '</div>')
                .appendTo(table);

            $('<div class="item-product item-mobile-cart item">' +
                '    <div class="item-product-cart-mobile"><a href="' + slug+'"> </a>' +
                '       <a class="product-images1" href="' + slug + '" title="' + item.product.name +'">' +
                '           <img width="80" height="150" alt="' + item.product.name + '" src="' + item.product.featureImage + '"></a></div>\n' +
                '    <div class="title-product-cart-mobile">' +
                '        <h3><a href="' + slug + '" title="'  + item.product.name + '">' + item.product.name +'</a></h3>\n' +
                '        <p>Giá: <span class="pricechange">' + item.product.price + '₫</span></p>' +
                '    </div>' +
                '    <div class="select-item-qty-mobile">' +
                '        <div class="txt_center in_put check_">' +
                '       <input class="productId" type="hidden" name="productId" value="' + item.product.id + '">' +
                '       <button onclick="var result = document.getElementById(\'qtyMobile' +  item.product.id +'\'); var qtyMobile' +  item.product.id +' = result.value; if( !isNaN( qtyMobile'+ item.product.id +' ) &amp;&amp; qtyMobile' + item.product.id + ' > 0 ) result.value--;return false;" class="reduced items-count btn-minus" type="button">–</button>' +
                '       <input type="number" maxlength="12" min="1" readonly="" class="check_number_here input-text mobile_input number-sidebar qtyMobile'+ item.product.id + '" id="qtyMobile' + item.product.id + '" name="qty" size="4" value="' + item.quantity + '">' +
                '       <button onclick="var result = document.getElementById(\'qtyMobile' + item.product.id + '\'); var qtyMobile'+ item.product.id + ' = result.value; if( !isNaN( qtyMobile' + item.product.id + ')) result.value++;return false;" class="increase items-count btn-plus" type="button">+</button>' +
                '   </div>' +
                '   <a class="button remove-item remove-item-cart" href="javascript:;" data-id="' + item.product.id +'">Xoá</a>' +
                '    </div>' +
                '</div>')
                .appendTo(cart_page_mobile);
        });
    } else {
        $('<div class="no-item"><p>Không có sản phẩm nào.</p></div>').appendTo(table);
        $('<div class="no-item"><p>Không có sản phẩm nào.</p></div>').appendTo(cart_page_mobile);
    }

    $('.totals_price').text(sub_total + 'đ');
    $('.totals_price_mobile').text(sub_total + 'đ');
}


$(".btn-cart").on('click', function (e) {
    var form = $(this).parents('form:first');
    var productId = $("input[name='productId']", form).val();
    var quantity = $("input[name='quantity']", form).val();
    var token =  getToken();
    if (quantity === undefined) {
        quantity = 1;
    }
    var data = {'token': token, 'productId': productId, 'quantity': quantity}
    addCart(data);
    updateCart();
    e.preventDefault();
});

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
            updateCartFromPage(data);
        },
        error: function (jqXhr, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
}

$(document).ready(function () {
    updateCart();
});