$('.image-checkbox-multi').on("click", function (e) {
    console.log("image-checkbox-multi");
    if ($(this).hasClass('image-checkbox-checked')) {
        $(this).removeClass('image-checkbox-checked');
        $(this).find('input[type="checkbox"]').first().removeAttr("checked");
    } else {
        $(this).addClass('image-checkbox-checked');
        $(this).find('input[type="checkbox"]').first().attr("checked", "checked");
    }
    e.preventDefault();
});

function deleteImages(data) {
    $.ajax({
        url: '/api/v1/deleteImage',
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
}


$('#btnDeleteImage').on('click', function (e) {
    var imagesSelected = [];
    $('.image-checkbox-checked img').each(function (index, item) {
        var imgSelected = $(this).attr('src');
        imagesSelected.push(imgSelected)
    });
    console.log(imagesSelected);
    deleteImages({'list' : imagesSelected })
    e.preventDefault();
});