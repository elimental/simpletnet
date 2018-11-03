$(function () {
    $('.page').click(function () {
        var type = $(this).attr('type');
        var pageNumber = $(this).find('a').attr('name');
        var pattern = document.getElementById('pattern').innerHTML;
        $.ajax({
            url: window.location.origin + '/searchPage',
            data: 'pattern=' + pattern + '&type=' + type + '&pageNumber=' + pageNumber,
            dataType: 'json',
            success: function (data) {
                $('#' + type + 'Search').empty();
                $.each(data, function (indexInArray, searchEntity) {
                    var imgUrl = window.location.origin + '/getImage?type=' + type + '&id=' + searchEntity.id;
                    var homeUrl;
                    if (type === 'account') {
                        homeUrl = window.location.origin + '/userProfile?id=' + searchEntity.id;
                    } else {
                        homeUrl = window.location.origin + '/group?id=' + searchEntity.id;
                    }
                    $('#' + type + 'Search').append(
                    '<div class="w3-row">'
                    + '<div class="w3-col m3">'
                        + '<a href="' + homeUrl + '"><img src="' + imgUrl + '"'
                        + 'alt="Avatar" class="w3-circle" style="height:30px;width:30px"></a></div>'
                        + '<div class="w3-col m6">'
                        + '<a href="' + homeUrl + '" style="text-decoration: none">'
                        + searchEntity.name + '</a></div></div><br>'
                    )
                })
            }
        })
    });
});