var stompClient = null;
var from = null;
var to = null;

$(function () {
    from = $('#from').text();
    to = $('#to').text();
});

$(function () {
    connect();
    scrollBottom();
});

$(function () {
    $('#sendMessage').on('click', function () {
        var text = $('#messageText').val();
        stompClient.send('/app/accountChat' + getTopic(from, to), {},
            JSON.stringify({'from': from, 'to': to, 'text': text}));
        $('#messageText').val('');
    });
});


function connect() {
    disconnect();
    var socket = new SockJS('/accountChat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic' + getTopic(from, to), function (message) {
            showMessage(JSON.parse(message.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
}

function getTopic() {
    return from < to ? '/' + from + '/' + to : '/' + to + '/' + from;
}

function showMessage(message) {
    $('#chatContainer').append(
        '<div class="w3-container">' +
        '<img src="/getImage?type=account&id=' + message.from + '" alt="Avatar" class="w3-circle" style="height:20px;width:20px">' +
        '<label style="font-size: small">' + message.fromName + '</c:out></label>' +
        '<span class="w3-opacity w3-right" style="font-size: x-small">' + message.date + '</span>' +
        '<p style="font-size: small">' + message.text + '</p>' +
        '</div>'
    );
    scrollBottom();
}

function scrollBottom() {
    $('#chatContainer').animate({
        scrollTop: $('#chatContainer')[0].scrollHeight
    }, "slow");
}