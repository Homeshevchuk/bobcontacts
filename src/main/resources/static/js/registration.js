$(document).ready(function () {
    $("#signUpBtn").click(function () {
        var user = {
            username: $("#username").val(),
            fullName: $("#fullName").val(),
            password: $("#password").val()
        }
        $.ajax
        ({
            type: "post",
            url: "/signup",
            async: false,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: JSON.stringify(user)
            ,
            success: function () {
                window.location.replace("/login.html");
            },
            error: function (data) {
                var error = JSON.parse(data.responseText);
                console.log(error);
                console.log($('#errorBox').children().length);
                if ($('#errorBox').children().length == 0) {
                    error.errors.forEach(function (item, i, arr) {
                        $('#errorBox').append('<div class="alert alert-danger in">' + item.defaultMessage + '</div>')
                        $(".alert").fadeOut(10000);
                        setTimeout(function () {
                            $(".alert").remove();
                        }, 10000)
                    });
                }
            }

        });
    });
});