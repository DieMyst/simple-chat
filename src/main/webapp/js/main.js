var ws;
var address = "ws://localhost:8080/SimpleChat/chat";
var $nickName;
var $color;
var $msgInput;
var $chatWindow;

Array.prototype.remove = function() {
    var what, a = arguments, L = a.length, ax;
    while (L && this.length) {
        what = a[--L];
        while ((ax = this.indexOf(what)) !== -1) {
            this.splice(ax, 1);
        }
    }
    return this;
};

function connectToChatserver() {
    wsocket = new WebSocket(address);
    wsocket.onmessage = onMessageReceived;
}

function updateNameList(names) {
    var usersContainer = $('#users-container');
    usersContainer.find('div').remove();
    var arrayLength = names.length;
    for (var i = 0; i < arrayLength; i++) {
        usersContainer.append('<div style="color:' + names[i].color + ';">' + names[i].name + "</div>")
    }

}

function onMessageReceived(event) {
    var msg = JSON.parse(event.data);
    if (msg.message != null) {
        var $messageLine = $('<div class="message"><div class="time">' + msg.received
        + '</div><div class="author">' +msg.nickname
        + ':</div><div style="color: ' + msg.color + ';" class="text">' + msg.message
        + '</div><div class="line"></div></div>');
        $chatWindow.append($messageLine);
        $chatWindow.scrollTop($chatWindow.height());
    } else if (msg.names != null) {
        updateNameList(msg.names);
    }
}

function sendMessage() {
    var msg = '{"message":"' + $msgInput.val() + '", "nickname":"'
        + $nickName.val() + '", "received":"", "color":"' + $color.val() + '"}';
    wsocket.send(msg);
    $msgInput.val('').focus();
}

$(document).ready(function() {
    $nickName = $('#nickname');
    $chatWindow = $('#chat-container');
    $msgInput = $('#msgInput');
    $color = $('#color');
    $('#main').hide();
    $nickName.focus();

    $('#login').click(function(event) {
        event.preventDefault();
        connectToChatserver();
        $('#greeting').text('Hello, ' + $nickName.val());
        $('#sign-in').hide();
        $('#main').show();
        $msgInput.focus();
    });
    $('#send').click(function(event) {
        event.preventDefault();
        sendMessage();
    });

    $('#leave').click(function(){
        leave();
    });
});

function leave() {
    wsocket.close();
    $chatWindow.empty();
    $('#main').hide();
    $('#sign-in').show();
    $nickName.focus();
}
