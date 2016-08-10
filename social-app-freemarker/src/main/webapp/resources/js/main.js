function deleteUser(userId) {

if (confirm("Вы уверены, что хотите удалить пользователя № " + userId + "?"))
{
    console.log('deleteUser' + userId);
    var url = "user/delete" + "?id=" + userId;
    $.ajax({
        type: 'GET',
        url: url,
        success: function (data, textStatus, jqXHR) {
            alert('User deleted successfully ');
            location.reload();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('deleteUser error: ' + textStatus + userId + ": " + errorThrown);
        }
    })
}

}

function deleteImage(imageId)
{
    if (confirm("Вы уверены, что хотите удалить фото №" + imageId + " из галереи пользователя ?"))
        {
        console.log('deleteImage #' + imageId + ', ');
        var url = "gallery/delete" +  "?imageId=" + imageId;
        $.ajax({
            type: 'POST',
            url: url,
            success: function (data, textStatus, jqXHR) {
                        alert('Image deleted successfully');
                        location.reload();
                    },
            error: function (jqXHR, textStatus, errorThrown) {
                alert('deleteImage error: ' + textStatus + ': ' + url);
            }
        })
        }
}

function deleteFriend(userId)
{
    if (confirm("Вы уверены, что хотите убрать пользователя № " + userId + " из друзей пользователя № " + id + "?"))
    {
    console.log('deleteFriendship, ' + userId);
    var url = "del" +  "?id2=" + userId;
    $.ajax({
        type: 'GET',
        url: url,
        success: function (data, textStatus, jqXHR) {
                    alert('Friendship deleted successfully');
                    location.reload();
                },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('deleteFriend error: ' + textStatus + userId + ': ' + url);
        }
    })
    }
}

function addFriend(userId)
{
    var url = "add" + "?id2=" + userId;
    console.log('addFriendship, ' + userId);
    $.ajax({
        type: 'GET',
        url: url,
        success: function (data, textStatus, jqXHR) {
                    alert('Friendship added successfully');
                    location.reload();
                },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('addFriend error: ' + textStatus + userId + ':' + url);
        }
    })
}

function sortUsers() {
        var dateMin = $('#dateBegin').val();
        var dateMax = $('#dateEnd').val();
        window.location = "usersbydate" + "?datemin=" + dateMin + "&datemax=" + dateMax;
}

function goHome() {
    window.location=".";
}

function gotoadduser() {
    window.location="adduser";
}

function gotoaddfriend(id) {
    window.location="nofriends";
}

function changeLogin() {
    console.log('changeLogin');
    var newLogin = prompt("Введите новый логин", '');
    if (newLogin != 'null' || newLogin != "") {
        var url = "user/change/login?param=" + newLogin;
    $.ajax({
        type: 'GET',
        url: url,
        success: function (data, textStatus, jqXHR) {
                                     alert('Логин успешно изменён.');
                                     location.reload();
                                 },
        error: function(jqXHR, textStatus, errorThrown) {
                           console.log(jqXHR, textStatus, errorThrown);
                           alert('updateUser: ' + textStatus + " логин " + newLogin + ", пользователь " + id + "'" + url + "'");
                       }

    })
    } else {
        alert('Логин не может быть пустым');
    }

}

function changePassword() {
    console.log('changePassword');
    var newPassword = prompt("Введите новый пароль", '');
    if (newPassword != null) {
        var url = "change/password?param=" + newPassword;
    $.ajax({
        type: 'GET',
        url: url,
        success: function (data, textStatus, jqXHR) {
                                     alert('Пароль успешно изменён.');
                                     location.reload();
                                 },
        error: function(jqXHR, textStatus, errorThrown) {
                           console.log(jqXHR, textStatus, errorThrown);
                           alert('updateUser: ' + textStatus + " пароль " + newPassword + ", пользователь " + id + "'" + url + "'");
                       }

    })} else {
            alert('Пароль не может быть пустым');
        }

}

function changeFirstName() {
    console.log('changeFirstName');
    var newName = prompt("Введите новое имя", '');
    var url = "user/change/firstname?param=" + newName;
    if (newName != '') {
    $.ajax({
        type: 'GET',
        url: url,
        success: function (data, textStatus, jqXHR) {
                                     alert('Имя успешно изменено.');
                                     location.reload();
                                 },
        error: function(jqXHR, textStatus, errorThrown) {
                           console.log(jqXHR, textStatus, errorThrown);
                           alert('updateUser: ' + textStatus + " имя " + newName + ", пользователь " + id + "'" + url + "'");
                       }

    })
    } else {
        alert('Имя не может быть пустым');
    }

}

function changeLastName() {
    console.log('changeLastName');
    var newName = prompt("Введите новую фамилию", '');
    var url = "change/lastname?param=" + newName;
    if (newName != '') {
    $.ajax({
        type: 'GET',
        url: url,
        success: function (data, textStatus, jqXHR) {
                                     alert('Имя успешно изменено.');
                                     location.reload();
                                 },
        error: function(jqXHR, textStatus, errorThrown) {
                           console.log(jqXHR, textStatus, errorThrown);
                           alert('updateUser: ' + textStatus + " фамилия " + newName + ", пользователь " + id + "'" + url + "'");
                       }

    })
    } else {
        alert('Фамилия не может быть пустой');
    }

}

var id = getQueryVariable("id");

function getQueryVariable(variable)
{
       var query = window.location.search.substring(1);
       var vars = query.split("&");
       for (var i=0;i<vars.length;i++) {
               var pair = vars[i].split("=");
               if(pair[0] == variable){return pair[1];}
       }
       return(false);
}


