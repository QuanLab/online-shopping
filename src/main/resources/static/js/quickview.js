$('.quick-view').on('click', function (e) {
    var productId = $(this).siblings('input[name="productId"]').val();
    console.log(productId)
    $.ajax({
        url: '/api/v1/product?id=' + productId,
        dataType: 'json',
        type: 'GET',
        contentType: 'application/json; charset=utf-8',
        success: function (product) {

            $('input[name="productId"]').val(product.id);
            $('.product-featured-image-quickview').attr('src', product.featureImage)

            var prices = $('#quick-view-product .prices');
            prices.empty();
            if(product.salePrice > 0) {
                $('<span class="price sale-price on-sale">' + product.salePrice + '₫</span>').appendTo(prices);
                $('<del class="old-price">' + product.price +' ₫</del>').appendTo(prices);
            } else {
                if(product.price > 0) {
                    $('<span class="price">' + product.price + '₫</span>').appendTo(prices);
                } else {
                    $('<span class="price h2">Liên hệ</span>').appendTo(prices);
                }
            }

            var qwpName = $('.qwp-name')
            qwpName.empty();
            $('<a href="#" title="' + product.name  + '">' + product.name + '</a>').appendTo(qwpName)

            $('#quick-view-product').modal('show');
            e.preventDefault()
        },
        error: function (jqXhr, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
    e.preventDefault()
});

$('.quickview-close').on('click', function (e) {
    $('#quick-view-product').modal('hide');
    e.preventDefault()
});