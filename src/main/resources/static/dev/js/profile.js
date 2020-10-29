$(document).ready(function () {  
    //=== Change user info
    /*$("body").on("click", ".service-change-user-info", function () {
        $(".service-form-change-user-info").submit();
        return false;
    });
    $("body").on("submit", ".service-form-change-user-info", function () {
        var _this_button = $(this).find(".service-change-user-info");
        var _this_preloader = $(this).find(".service-change-user-info");
        var _this_form = $(this);
        var phorm_data = _this_form.serialize();

        if (_this_button.hasClass("no_click")) {
            return false;
        }

        $.ajax({
            url: _this_form.attr("action"),
            type: _this_form.attr("method"),
            data: phorm_data,
            beforeSend: function () {
                _this_button.addClass("no_click");
                _this_preloader.addClass("loading");
            },
            success: function (response) {
                if (typeof (response) != 'undefined' && response != null) {
                    if (response.success > 0) {
                        toastrPrintList(response.success_message, 'success');
                    } else {
                        toastrPrintList(response.error, 'error');
                    }
                }
            },
            error: function () {
                toastr.error(window.toastr_error);
            },
            complete: function () {
                _this_button.removeClass("no_click");
                _this_preloader.removeClass("loading");
            }
        });

        return false;
    });*/
    
    //=== Logout
    $("body").on("click", ".service-logout", function () {
        var _this_button = $(this);
        var _this_preloader = $(this);
        
        _this_button.addClass("no_click");
        _this_preloader.addClass("loading");
        
        var phorm_data = new Object();
        phorm_data.action = "logout";
        
        $.ajax({
            url: "/ajax/user.php",
            type: "POST",
            data: phorm_data,
            beforeSend: function () {
                _this_button.addClass("no_click");
                _this_preloader.addClass("loading");
            },
            success: function (response) {
                if (typeof (response) != 'undefined' && response != null) {
                    if (response.success > 0) {
                        toastrPrintList(response.success_message, 'success');
                        
                        window.location.reload();
                    } else {
                        toastrPrintList(response.error, 'error');
                    }
                }
            },
            error: function () {
                toastr.error(window.toastr_error);
            },
            complete: function () {
                _this_button.removeClass("no_click");
                _this_preloader.removeClass("loading");
            }
        });
    });
});