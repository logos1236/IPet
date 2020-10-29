jQuery(document).ready(function () {    
//=== Send vacancy
    $("body").on("submit", ".service-resume_form", function (e) {        
        var _this_button = $(this).find("input[type=submit]");
        var _this_preloader = _this_button.closest(".for-load");
        var _this_form = $(this);
        var phorm_data = new FormData(this);

        if (_this_button.hasClass("no_click")) {
            return false;
        }

        _this_button.addClass("no_click");
        _this_preloader.addClass("loading");

        $.ajax({
            url: _this_form.attr("action"),
            type: _this_form.attr("method"),
            data: phorm_data,
            processData: false,
            contentType: false,
            success: function (response) {
                if (typeof (response) != 'undefined' && response != null) {
                    if (response.success > 0) {
                        //toastrPrintList(response.success_message, 'success');
                        
                        _this_form.find("input:not(:hidden):not(:submit):not(:checkbox):not(:radio):not(:button), textarea").each(function () {
                            _this_value = "";
                            $(this).val(_this_value);
                        });
                        
                        $('input[type=file]').trigger('refresh');
                        
                        $(".resume-page").hide();
                        $(".sending-page").show();
                    } else {
                        toastrPrintList(response.error, 'error');
                    }
                }
                
                //=== Capcha
                if (typeof grecaptcha != 'undefined') {
                    initGoogleCapcha();
                }
            },
            error: function () {
                toastr.error(window.toastr_error);
            },
            complete: function () {
                _this_button.removeClass('no_click');
                _this_preloader.removeClass('loading');
            }
        });

        return false;
    });
});
