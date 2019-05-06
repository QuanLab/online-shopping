
$('#selectAll').on('click', function () {
    if ($('#selectAll').prop('checked') === true) {
        $('.item-cb').prop('checked', true)
    } else {
        $('.item-cb').prop('checked', false)
    }
});

$("#btnDeleteAll").on("click", function (event) {
    var selected = [];
    $('td input:checked').each(function () {
        selected.push(this.id);
    });
    if (selected.length > 0) {
        $.ajax({
            url: '/api/v1/deleteCategory',
            dataType: 'json',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({'list': selected}),
            success: function (data, textStatus, jQxhr) {
                window.location.reload();
            },
            error: function (jqXhr, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        });
    } else {
        alert("Bạn phải chọn ít nhất một sản phẩm để khi xóa")
    }
});

$('.btnEdit').on('click', function () {
    var catId = $(this).attr('data-id');
    $.ajax({
        url: '/api/v1/category?id=' + catId,
        dataType: 'json',
        type: 'GET',
        contentType: 'application/json; charset=utf-8',
        success: function (data, textStatus, jQxhr) {
            $('#id').val(data.id);
            $('#name').val(data.name);
            $('#slug').val(data.slug);
            $('#description').val(data.description);
        },
        error: function (jqXhr, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
});

$('.btn-create').on('click', function () {
    $('#id').val('');
    $('#name').val('');
    $('#slug').val('');
    $('#description').val('');
});

$('.btn-save').on('click', function () {
    var id = $('#id').val();
    var name = $('#name').val();
    var slug = $('#slug').val();
    var description = $('#description').val();
    var data = {
        'id' : id, 'name' : name, 'slug' : slug, 'description' : description
    };
    $.ajax({
        url: '/api/v1/category',
        dataType: 'json',
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data),
        success: function (data, textStatus, jQxhr) {
            window.location.reload();
        },
        error: function (jqXhr, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
});