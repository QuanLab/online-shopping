$('.btn-save').on('click', function () {
    var data = {
        'about' : $("#document-full").children().first().html()
    };
    console.log(JSON.stringify(data))

    $.ajax({
        url: '/api/v1/about',
        dataType: 'json',
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data),
        success: function (data, textStatus, jQxhr) {
            var updateStatus = $("#updateStatus");
            updateStatus.empty();
            updateStatus.append("<span>Lưu thành công</span>")
        },
        error: function (jqXhr, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });

});