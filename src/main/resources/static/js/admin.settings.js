$('.btn-save').on('click', function () {
    var data = {
        'domain': $('#domain').val(),
        'title': $('#title').val(),
        'keywords': $('#keywords').val(),
        'description': $('#description').val(),
        'contact': $('#contact').val(),
        'email': $('#email').val(),
        'password': $('#password').val(),
        'phone': $('#phone').val(),
        'address': $('#address').val(),
        'facebook': $('#facebook').val(),
        'youtube': $('#youtube').val(),
        'footer1': $('#footer1').val(),
        'footer1_content': $('#footer1_content').val(),
        'footer2': $('#footer2').val(),
        'footer2_content': $('#footer2_content').val(),
        'footer3': $('#footer3').val(),
        'footer3_content': $('#footer3_content').val()
    };

    console.log(JSON.stringify(data))

    $.ajax({
        url: '/api/v1/setting',
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