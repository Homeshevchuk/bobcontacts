$(document).ready(function(){
    $("#loginBtn").click(function(){
        var data = btoa($("#username").val()+":"+$("#password").val());
        $.ajax
        ({
            type: "GET",
            url: "/signin",
            async: false,
            headers: {
                "Authorization": "Basic " + data
            },
            success: function (data){
                console.log("Asdasd");
                window.location.replace("/index.html");
            },
            error: function () {
               /*$("#buttons").before('<div class="alert alert-danger in">Ошибка входа!</div>');
                $(".alert").fadeOut(1000);*/

                $("#form").addClass("has-error").delay(3000).queue(function(next){
                    $(this).removeClass("has-error");
                    next();

                });

            }
        });
    });
    $("#signUpBtn").click(function () {
        window.location.replace("/registration.html");
    });
});