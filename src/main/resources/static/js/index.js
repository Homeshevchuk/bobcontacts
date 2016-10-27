
$(document).ready(function () {
    var contacts;
    var row;
    var filter = {
        filter:'',
        filtered:false,
        filteredArr:[]
    };
    var filter;
    function fillErrors(data) {
        var error = JSON.parse(data.responseText);
        console.log(error);
        console.log($('#errorBox').children().length);
        if ($('#errorBox').children().length == 0) {
            error.errors.forEach(function (item, i, arr) {
                $('#' + item.field).val('');
                $('#' + item.field).attr("placeholder", item.defaultMessage);
            })
            setTimeout(function () {
                $("#surname").attr("placeholder", 'Surname');
                $("#name").attr("placeholder", 'Name');
                $("#lastName").attr("placeholder", 'Last Name');
                $("#mobilePhone").attr("placeholder", 'Mobile Phone');
                $("#homePhone").attr("placeholder", 'Home Phone');
                $("#address").attr("placeholder", 'Address');
                $("#email").attr("placeholder", 'Email');

            },10000);


        }
    }
    function paint(data) {
        function nullToEmpty(s) {
            return (s == null ? '' : s)
        }

        data.forEach(function (item, i, arr) {

            $('#content').append('<tr class="rows"><th scope="row">' + (i + 1) + '</th><td>' + nullToEmpty(item.surname) + '</td><td>' + nullToEmpty(item.name) + '</td><td>' + nullToEmpty(item.middleName) + '</td><td>' + nullToEmpty(item.mobilePhone) + '</td><td>' + nullToEmpty(item.homePhone) + '</td><td>' + nullToEmpty(item.address) + '</td><td>' + nullToEmpty(item.email) + '</td></tr>')

        });
    }

    function clear() {
        $('#content').empty();
    }

    $.ajax
    ({
        type: "GET",
        url: "/Contacts/getContacts",
        async: false,

        success: function (data) {
            console.log("Asdasd");
            console.log(data);
            contacts = data;
            paint(contacts);


        },
        error: function () {
            /*$("#buttons").before('<div class="alert alert-danger in">Ошибка входа!</div>');
             $(".alert").fadeOut(1000);*/

            $("#form").addClass("has-error").delay(3000).queue(function (next) {
                $(this).removeClass("has-error");
                next();

            });

        }
    });
    $("#content").on("click", function (eventData) {
        console.log(eventData);
        row = eventData.target.parentNode.rowIndex - 1;
        if(filter.filtered == true){
            row = contacts.indexOf(filter.filteredArr[row]);
        }
        console.log('row = ' + row + ';column = ' + eventData.target.cellIndex);
        $("#surname").val(contacts[row].surname);
        $("#name").val(contacts[row].name);
        $("#middleName").val(contacts[row].middleName);
        $("#mobilePhone").val(contacts[row].mobilePhone);
        $("#homePhone").val(contacts[row].homePhone);
        $("#address").val(contacts[row].address);
        $("#email").val(contacts[row].email);
        $('#deleteBtn').removeClass('hide')
        $('.modal-title').text('Change contact');
        $('.target-dialog').modal('show');
    });
    $('#saveDialog').click(function () {

        if ($('.modal-title').text() == 'Change contact') {
            contacts[row].surname = $("#surname").val();
            contacts[row].name = $("#name").val();
            contacts[row].middleName = $("#middleName").val();
            contacts[row].mobilePhone = $("#mobilePhone").val();
            contacts[row].homePhone = $("#homePhone").val();
            contacts[row].address = $("#address").val();
            contacts[row].email = $("#email").val();
            $.ajax
            ({
                type: "post",
                url: "/Contacts/updateContact",
                async: false,
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                data: JSON.stringify(contacts[row])
                ,
                success: function (data) {

                    clear();
                    paint(filter.filtered?filter.filteredArr:contacts);
                    $('.target-dialog').modal('hide');
                },
                error: function (data) {
                    fillErrors(data);
                }
            });
        } else {

            var object = {
                surname: $("#surname").val(),
                name: $("#name").val(),
                middleName: $("#middleName").val(),
                mobilePhone: $("#mobilePhone").val(),
                homePhone: $("#homePhone").val(),
                address: $("#address").val(),
                email: $("#email").val(),
            }

            $.ajax
            ({
                type: "post",
                url: "/Contacts/addContact",
                async: false,
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                data: JSON.stringify(object)
                ,
                success: function (data) {
                    contacts.push(object);
                    clear();
                    paint(contacts);
                    $('.target-dialog').modal('hide');
                },
                error: function (data) {
                    fillErrors(data);
                }
            });
        }


    });
    $('#deleteBtn').click(function () {


        $.ajax
        ({
            type: "post",
            url: "/Contacts/deleteContact",
            async: false,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: JSON.stringify(contacts[row])
            ,
            success: function (data) {

                if(filter.filtered) {
                    indexInFiltered = filter.filteredArr.indexOf(contacts[row]);
                    contacts.splice(row, 1);
                    filter.filteredArr.splice(indexInFiltered,1);
                    clear();
                    paint(filter.filteredArr);
                }else {
                    contacts.splice(row, 1);
                    clear();
                    paint(contacts);
                }


                $('.target-dialog').modal('hide');
            },
            error: function (data) {
            }

        });


    });
    $('#addBtn').click(function () {
        $("#surname").val('');
        $("#name").val('');
        $("#middleName").val('');
        $("#mobilePhone").val('');
        $("#homePhone").val('');
        $("#address").val('');
        $("#email").val('');
        $('#deleteBtn').removeClass('hide')
        $('.target-dialog').modal('show');
        $('#s')
        $('#deleteBtn').addClass('hide')
        console.log($('.modal-title'));
        $('.modal-title').text('Create contact');
        $('.target-dialog').modal('show');


    });
    $('#filterInput').on('input',function () {
        if(filter){
            var arr = jQuery.grep(contacts, function( elem, i ) {
               return elem[filter.filter].toLowerCase().includes($('#filterInput').val().toLowerCase());
            });
            clear();
            paint(arr);
            filter.filtered=true;
            filter.filteredArr = arr;
        }else {
            filter.filtered = false;
            filter.filteredArr=[];
        }
    })
    $('#surnameFilter').click(function () {
        $('.dropdown-toggle').text('Surname')
        filter.filter = 'surname';


    });
    $('#nameFilter').click(function () {
        $('.dropdown-toggle').text('Name')
        filter.filter = 'name';


    });
    $('#phoneFilter').click(function () {
        $('.dropdown-toggle').text('Phone')
        filter.filter = 'mobilePhone';

    });
    $('#disableFilter').click(function () {
        $('.dropdown-toggle').text('Filter')
        filter.filter = '';
        filter.filtered = false;
        filter.filteredArr = [];
    });
});