$("#btnSaveProduct").on( "click", function () {
    var data = {
        "id" : $("#id").val(),
        "name" : $("#name").val(),
        "slug": $("#slug").val(),
        "quantity": $("#quantity").val(),
        "price": $("#price").val(),
        "category": {"id" : $("#category option:selected").val()},
        "imageList" : $('#imageList').val(),
        "featureImage" : $('#featuredImage').val(),
        "description": $("#document-full").children().first().html()
    };
    // save product to database
    $.ajax({
        url: '/api/v1/product',
        dataType: 'json',
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data),
        success: function( data, textStatus, jQxhr ){
            var updateStatus = $("#updateStatus");
            updateStatus.empty()
            updateStatus.append("<span>Lưu thành công</span>")
        },
        error: function( jqXhr, textStatus, errorThrown ){
            console.log( errorThrown );
        }
    });
})

$(document).on("click", '.image-checkbox', function (e) {
    var hasChecked = $(this).hasClass('image-checkbox-checked');
    //reset all checkbox to non-checked
    $(".image-checkbox").each(function () {
        $(this).removeClass('image-checkbox-checked');
        $(this).find('input[type="checkbox"]').first().removeAttr("checked");
    });

    if (!hasChecked) {
        $(this).addClass('image-checkbox-checked');
        $(this).find('input[type="checkbox"]').first().attr("checked", "checked");
    }
    e.preventDefault();
});

$(document).on("click", '.image-checkbox-multi', function (e) {
    if ($(this).hasClass('image-checkbox-checked')) {
        $(this).removeClass('image-checkbox-checked');
        $(this).find('input[type="checkbox"]').first().removeAttr("checked");
    } else {
        $(this).addClass('image-checkbox-checked');
        $(this).find('input[type="checkbox"]').first().attr("checked", "checked");
    }
    e.preventDefault();
});


$('.btnSelectImage').on('click', function () {
    loadImageToModal('.modal-body', 'image-checkbox');
});

$('.btnSelectImageList').on('click', function () {
    loadImageToModal('.modal-body', 'image-checkbox-multi');
});

function loadImageToModal(modalBodyClass, imageCheckBoxClass) {
    $.ajax({
        url: '/api/v1/images?offset=0&limit=5',
        dataType: 'json',
        type: 'GET',
        contentType: 'application/json; charset=utf-8',

        success: function (data, textStatus, jQxhr) {
            var modalBody = $(modalBodyClass);
            modalBody.empty();
            var row = $('<div class="row"></div>')
            var numRowElement = 0;
            $.each(data, function (index, imageUrl) {
                $('<div class="col-md-1">' +
                    '<label class="' + imageCheckBoxClass + '">' +
                    '<img style="max-width: 100%;" src="' + imageUrl + '" />' +
                    '<input type="checkbox"/>' +
                    '</label>' +
                    '</div>'
                ).appendTo(row);
                numRowElement = numRowElement + 1;
                if (numRowElement === 12) {
                    row.appendTo(modalBody);
                    row.empty();
                    numRowElement = 0;
                }
            });
            if (numRowElement > 0) {
                row.appendTo(modalBody);
                row.empty();
            }
            $('#modalMedia').modal({show: true});
        },
        error: function (jqXhr, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
}

$('.btnSelectImageAccepted').on('click', function (e) {
    var imagesSelected = [];
    $('.image-checkbox-checked img').each(function (index, item) {
        var imgSelected = $(this).attr('src');
        imagesSelected.push(imgSelected)
    });
    $('#modalMedia').modal('hide');

    if($('.image-checkbox-multi').length === 0) {
        $('#featuredImage').val(imagesSelected.join('|'));
    } else {
        $('#imageList').val(imagesSelected.join('|'));
    }
    e.preventDefault();
})