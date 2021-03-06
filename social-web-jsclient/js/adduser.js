// The root URL for the RESTful services
var PREFIX_URL = "http://localhost:8081/rest"

var USER_URL = "/user";

// Register listeners
$('#addUser').click(function () {
        addUser();
//        goHome();
});


function goHome() {
    window.location="index.html";
}

function addUser() {
    console.log('addUser');
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: PREFIX_URL + USER_URL,
        dataType: "json",
        data: formToJSON(),
        success: function (data, textStatus, jqXHR) {
            alert('User created successfully');
            if (confirm("Хотите добавить ещё одного пользователя?")) {

            } else {
            goHome();
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('addUser error: ' + textStatus);
        }
    });
}

function formToJSON() {
    return JSON.stringify({
        "login": $('#login').val(),
        "password": $('#password').val(),
        "firstName": $('#firstName').val(),
        "lastName": $('#lastName').val(),
        "age": $('#age').val()
    });
}