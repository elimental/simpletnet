/*
    Common scripts
 */

function goBack() {
    window.history.back();
}

function submitMessageForm(object) {
    var form = object.parentNode;
    var message = document.getElementById("message").innerHTML;
    message = message.replaceAll("&nbsp;", " ").trim();
    if (message.length !== 0) {
        var hiddenInput = document.createElement("input");
        hiddenInput.type = "hidden";
        hiddenInput.name = "message";
        hiddenInput.value = message;
        form.appendChild(hiddenInput);
        form.submit();
    } else {
        return false;
    }
}

function submitPersonalMessageForm(object, num) {
    var form = object.parentNode;
    var message = document.getElementById("message").innerHTML;
    message = message.replaceAll("&nbsp;", " ").trim();
    if (message.length !== 0) {
        var hiddenInputMessage = document.createElement("input");
        hiddenInputMessage.type = "hidden";
        hiddenInputMessage.name = "message";
        hiddenInputMessage.value = message;
        form.appendChild(hiddenInputMessage);
        var hiddenInputSecondTalkerId = document.createElement("input");
        hiddenInputSecondTalkerId.type = "hidden";
        hiddenInputSecondTalkerId.name = "secondTalkerId";
        hiddenInputSecondTalkerId.value = num;
        form.appendChild(hiddenInputSecondTalkerId);
        form.submit();
    } else {
        return false;
    }
}

String.prototype.replaceAll = function (target, replacement) {
    return this.split(target).join(replacement);
};

function delPhoneLine(a) {
    $(a).parent().parent().remove();
}

/*
    jQuery datepicker
 */
$(function () {
    $("#datepicker").datepicker({
        yearRange: "-100:+0",
        changeMonth: true,
        changeYear: true,
        closeText: 'Закрыть',
        prevText: 'Пред',
        nextText: 'След',
        currentText: 'Сегодня',
        monthNames: ['Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь',
            'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь'],
        monthNamesShort: ['Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн',
            'Июл', 'Авг', 'Сен', 'Окт', 'Ноя', 'Дек'],
        dayNames: ['воскресенье', 'понедельник', 'вторник', 'среда', 'четверг', 'пятница', 'суббота'],
        dayNamesShort: ['вск', 'пнд', 'втр', 'срд', 'чтв', 'птн', 'сбт'],
        dayNamesMin: ['Вс', 'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб'],
        weekHeader: 'Нед',
        dateFormat: "dd.mm.yy",
        firstDay: 1
    });
});

/*
    Date validation
 */
jQuery.validator.addMethod(
    "rusDate",
    function (value, element) {
        var check = false;
        var re = /^\d{1,2}\.\d{1,2}\.\d{4}$/;
        if (re.test(value)) {
            var adata = value.split('.');
            var dd = parseInt(adata[0], 10);
            var mm = parseInt(adata[1], 10);
            var yyyy = parseInt(adata[2], 10);
            var xdata = new Date(yyyy, mm - 1, dd);
            if ((xdata.getFullYear() === yyyy) && (xdata.getMonth() === mm - 1) && (xdata.getDate() === dd)) {
                check = true;
            }
            else {
                check = false;
            }
        } else {
            check = false;
        }
        return this.optional(element) || check;
    },
    "Ведите корректную дату"
);

/*
    Account forms validation
 */
$(function () {
    $("#accountForm").validate({
        rules: {
            email: {
                email: true,
                required: true
            },
            password: {
                required: true,
                normalizer: function (value) {
                    return $.trim(value)
                }
            },
            rpassword: {
                equalTo: "#password"
            },
            firstName: {
                minlength: 3,
                required: true,
                normalizer: function (value) {
                    return $.trim(value)
                }
            },
            birthDay: {
                rusDate: true
            }
        },
        messages: {
            email: {
                email: "Необходимо ввести корретный email",
                required: "Это обязательное поле"
            },
            password: {
                required: "Это обязательное поле"
            },
            rpassword: {
                equalTo: "Пароли не совпадают"
            },

            firstName: {
                minlength: "Имя должно состоять минимум из 3-х символов",
                required: "Это обязательное поле"
            },
            birthDay: {
                date: "Введите корректную дату"
            }
        },
        errorClass: "validation_error"
    });
});

/*
    Create community form validation
 */

$(function () {
    $("#groupForm").validate({
        rules: {
            name: {
                minlength: 3,
                required: true,
                normalizer: function (value) {
                    return $.trim(value)
                }
            }
        },
        messages: {
            name: {
                minlength: "Имя должно состоять минимум из 3-х символов",
                required: "Это обязательное поле"
            }
        },
        errorClass: "validation_error"
    });
});

/*
    Login form validation
 */

$(function () {
    $("#loginForm").validate({
        rules: {
            email: {
                email: true,
                required: true
            },
            password: {
                required: true,
                normalizer: function (value) {
                    return $.trim(value)
                }
            }
        },
        messages: {
            email: {
                email: "Необходимо ввести корретный email",
                required: "Это обязательное поле"
            },
            password: {
                required: "Это обязательное поле"
            }
        },
        errorClass: "validation_error"
    });
});

/*
    Picture size validation
 */
$(function () {
    $("#img5mb").on('change', function () {
        if (this.files[0].size > 5242880) {
            this.value = "";
            $("#imageDialog").dialog({
                resizable: false,
                height: "auto",
                width: 400,
                modal: true,
                buttons: {
                    "OK": function () {
                        $(this).dialog("close");
                    }
                }
            })
        }
    })
});

/*
    Dynamic adding phone field and its validation rules
 */
$(function () {
    var i = 1;
    var template = jQuery.validator.format($.trim($("#addPhoneChild").html()));
    $("#addPhoneBtn").on('click', function (e) {
        $(template(i++)).appendTo("#phones");
        $('.delPhoneLine').on('click', function () {
            $(this).parent().parent().remove();
        });
        $(".phone").each(function () {
            $(this).rules("add", {
                required: true,
                digits: true,
                minlength: 6,
                normalizer: function (value) {
                    return $.trim(value)
                },
                messages: {
                    required: "Поле не может быть пустым",
                    digits: "Только цифры",
                    minlength: "Минимум 6-ть цифр"
                }
            });
        });
        e.preventDefault();
    });
});

/*
    Valitaion for dynamic phone line
 */
$(function () {
    $("input[id*='addPhoneEditRule']").each(function () {
        $(this).rules("add", {
            required: true,
            digits: true,
            minlength: 6,
            normalizer: function (value) {
                return $.trim(value)
            },
            messages: {
                required: "Поле не может быть пустым",
                digits: "Только цифры",
                minlength: "Минимум 6-ть цифр"
            }
        });
    })
});

/*
    Confirmation dialog
 */
$(function () {
    $("#confirmEdit").on('click', function () {
        $("#confirmDialog").dialog({
            resizable: false,
            height: "auto",
            width: 400,
            modal: true,
            buttons: {
                "Да": function () {
                    $(this).dialog("close");
                    $("#groupForm").submit();
                    $("#accountForm").submit();
                },
                "Нет": function () {
                    $(this).dialog("close");
                }
            }
        })
    })
});

$(function () {
    $('.delPhoneLine').on('click', function () {
        $(this).parent().parent().remove();
    })
});