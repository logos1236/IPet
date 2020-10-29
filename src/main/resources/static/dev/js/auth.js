function backTimer(elem, func) {
    var seconds = elem.text();
    
    if (typeof (int) != 'undefined' && int != null) {
        clearInterval(int);
    }
    
    int = setInterval(function () {
        if (seconds > 0) {
            seconds--;
            elem.text(seconds);
        } else {
            clearInterval(int);
            int = null;
            func();
        }
    }, 1000);
}

function repeatSmsBlock(repeat_btn_container, timer_container, next_try) { 
    if (next_try > 0) {
        timer_container.show();
        repeat_btn_container.hide();

        timer_container.find(".timer").html(next_try);
        backTimer(timer_container.find(".timer"), function () {
            timer_container.hide();
            repeat_btn_container.show();
        });
    } else {
        timer_container.hide();
        repeat_btn_container.show();
    }
}

$(document).ready(function () {
//=== Auth
    $("body").on("click", ".service-form_login .service-form_submit", function () {        
        $(".service-form_login form").submit();
        return false;
    });
    $("body").on("submit", ".service-form_login form", function () {
        var _this_form = $(this);
        var _this_button = _this_form.find(".service-form_submit");
        var _this_preloader = _this_form.find(".service-form_submit");

        var phorm_data = _this_form.serialize();

        if (_this_button.hasClass("no_click")) {
            return false;
        }

        _this_button.addClass("no_click");
        _this_preloader.addClass("loading");

        $.ajax({
            url: _this_form.attr("action"),
            type: _this_form.attr("method"),
            data: phorm_data,
            success: function (response) {
                if (typeof (response) != 'undefined' && response != null) {
                    if (response.success > 0) {
                        $.fancybox.close();

                        toastrPrintList(response.success_message, 'success');

                        window.location.reload();
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
    
//=== Check phone
    $("body").on("click", ".service-form_check_phone .service-form_submit", function () {        
        $(".service-form_check_phone form").submit();
        return false;
    });
    $("body").on("submit", ".service-form_check_phone form", function () {
        var _this_form = $(this);
        var _this_button = _this_form.find(".service-form_submit");
        var _this_preloader = _this_form.find(".service-form_submit");

        var phorm_data = _this_form.serialize();

        if (_this_button.hasClass("no_click")) {
            return false;
        }

        _this_button.addClass("no_click");
        _this_preloader.addClass("loading");

        $.ajax({
            url: _this_form.attr("action"),
            type: _this_form.attr("method"),
            data: phorm_data,
            success: function (response) {
                if (typeof (response) != 'undefined' && response != null) {
                    if (response.success > 0) {
                        //=== Confirm SMS registration 
                        $.fancybox.close();
                        $.fancybox.open({
                            src  : '#modal__login_2-2',
                            btnTpl: {
                                close: '',
                                smallBtn: ''
                            }
                        });
                        $(".service-form_registration_block .service-form_registration_change_phone").show();
                        $(".service-form_registration_block .service-form_registration_confirm_sms").show();
                        $(".service-form_registration_block .service-form_registration_set_password").stop().animate({height: 0}, 150);
                        
                        //=== Set phone to into input
                        $(".service-form_registration_block .service-form_registration_change_phone input[name=phone]").val(response.phone);
                        $(".service-form_registration_block .service-form_registration_set_password input[name=phone]").val(response.phone);
                            
                        //=== Timer for resend SMS
                        var repeat_btn_container = $(".service-form_registration_confirm_sms .service-resend_sms");
                        var timer_container = $(".service-form_registration_confirm_sms .service-resend_sms_timer");
                        repeatSmsBlock(repeat_btn_container, timer_container, response.next_try);

                        toastrPrintList(response.success_message, 'success');
                    } else {
                        if (response.is_client == true) {
                            //=== Already client
                            $.fancybox.close();
                            $.fancybox.open({
                                src  : '#modal__login',
                                btnTpl: {
                                    close: '',
                                    smallBtn: ''
                                }
                            });
                            
                            $(".service-form_login").find("input[name=phone]").val(response.phone);
                        }
                        
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
    
//=== Resend SMS
    $("body").on("click", ".service-form_registration_confirm_sms .service-resend_sms", function () {
        var _this_button = $(this);
        var _this_preloader = $(this);

        if (_this_button.hasClass("no_click")) {
            return false;
        }
        
        var phorm_data = new Object();
        phorm_data.action_auth = "registration_resend_sms";

        $.ajax({
            url: "/ajax/auth.php",
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
                        
                        //=== Timer for resend SMS
                        var repeat_btn_container = $(".service-form_registration_confirm_sms .service-resend_sms");
                        var timer_container = $(".service-form_registration_confirm_sms .service-resend_sms_timer");
                        repeatSmsBlock(repeat_btn_container, timer_container, response.next_try);

                    } else {
                        toastrPrintList(response.error, 'error');
                    }
                }

                return false;
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
    
//=== Registration (change phone)
    $("body").on("click", ".service-form_registration_change_phone .service-form_submit", function () {        
        $(".service-form_registration_change_phone form").submit();
        return false;
    });
    $("body").on("submit", ".service-form_registration_change_phone form", function () {
        var _this_form = $(this);
        var _this_button = _this_form.find(".service-form_submit");
        var _this_preloader = _this_form.find(".service-form_submit");

        var phorm_data = _this_form.serialize();

        if (_this_button.hasClass("no_click")) {
            return false;
        }

        _this_button.addClass("no_click");
        _this_preloader.addClass("loading");

        $.ajax({
            url: _this_form.attr("action"),
            type: _this_form.attr("method"),
            data: phorm_data,
            success: function (response) {
                if (typeof (response) != 'undefined' && response != null) {
                    if (response.success > 0) {                
                        //=== Timer for resend SMS
                        var repeat_btn_container = $(".service-form_registration_confirm_sms .service-resend_sms");
                        var timer_container = $(".service-form_registration_confirm_sms .service-resend_sms_timer");
                        repeatSmsBlock(repeat_btn_container, timer_container, response.next_try);
                        
                        //=== Set phone to into input
                        $(".service-form_registration_block .service-form_registration_change_phone input[name=phone]").val(response.phone);
                        $(".service-form_registration_block .service-form_registration_set_password input[name=phone]").val(response.phone);

                        toastrPrintList(response.success_message, 'success');
                    } else {
                        if (response.is_client == true) {                            
                            //=== Already client
                            $.fancybox.close();
                            $.fancybox.open({
                                src  : '#modal__login',
                                btnTpl: {
                                    close: '',
                                    smallBtn: ''
                                }
                            });
                            
                            $(".service-form_login").find("input[name=phone]").val(response.phone);
                        }
                        
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
    
//=== Registration (confirm sms)
    $("body").on("click", ".service-form_registration_confirm_sms .service-form_submit", function () {        
        $(".service-form_registration_confirm_sms form").submit();
        return false;
    });
    $("body").on("submit", ".service-form_registration_confirm_sms form", function () {
        var _this_form = $(this);
        var _this_button = _this_form.find(".service-form_submit");
        var _this_preloader = _this_form.find(".service-form_submit");

        var phorm_data = _this_form.serialize();

        if (_this_button.hasClass("no_click")) {
            return false;
        }

        _this_button.addClass("no_click");
        _this_preloader.addClass("loading");

        $.ajax({
            url: _this_form.attr("action"),
            type: _this_form.attr("method"),
            data: phorm_data,
            success: function (response) {
                if (typeof (response) != 'undefined' && response != null) {
                    if (response.success > 0) {
                        //=== Set password
                        $(".service-form_registration_block .service-form_registration_change_phone").hide();
                        $(".service-form_registration_block .service-form_registration_confirm_sms").hide();
                        autoHeightAnimate($(".service-form_registration_block .service-form_registration_set_password"), 150);

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
                _this_button.removeClass('no_click');
                _this_preloader.removeClass('loading');
            }
        });

        return false;
    });
    
//=== Set password
    $("body").on("click", ".service-form_registration_set_password .service-form_submit", function () {        
        $(".service-form_registration_set_password form").submit();
        return false;
    });
    $("body").on("submit", ".service-form_registration_set_password form", function () {
        var _this_form = $(this);
        var _this_button = _this_form.find(".service-form_submit");
        var _this_preloader = _this_form.find(".service-form_submit");

        var phorm_data = _this_form.serialize();

        if (_this_button.hasClass("no_click")) {
            return false;
        }

        _this_button.addClass("no_click");
        _this_preloader.addClass("loading");

        $.ajax({
            url: _this_form.attr("action"),
            type: _this_form.attr("method"),
            data: phorm_data,
            success: function (response) {
                if (typeof (response) != 'undefined' && response != null) {
                    if (response.success > 0) {
                        $.fancybox.close();

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
                _this_button.removeClass('no_click');
                _this_preloader.removeClass('loading');
            }
        });

        return false;
    });
    
    
//=== Forgot password (check phone)
    $("body").on("click", ".service-form_forgot_check_phone .service-form_submit", function () {        
        $(".service-form_forgot_check_phone form").submit();
        return false;
    });
    $("body").on("submit", ".service-form_forgot_check_phone form", function () {
        var _this_form = $(this);
        var _this_button = _this_form.find(".service-form_submit");
        var _this_preloader = _this_form.find(".service-form_submit");

        var phorm_data = _this_form.serialize();

        if (_this_button.hasClass("no_click")) {
            return false;
        }

        _this_button.addClass("no_click");
        _this_preloader.addClass("loading");

        $.ajax({
            url: _this_form.attr("action"),
            type: _this_form.attr("method"),
            data: phorm_data,
            success: function (response) {
                if (typeof (response) != 'undefined' && response != null) {
                    if (response.success > 0) {
                        //=== Forgot (confirm SMS) 
                        $(".service-form_forgot_block .service-form_forgot_check_phone").hide(); 
                        $(".service-form_forgot_block .service-form_forgot_change_phone").show();
                        autoHeightAnimate($(".service-form_forgot_block .service-form_forgot_confirm_sms"), 150);
                        $(".service-form_forgot_block .service-form_registration_set_password").stop().animate({height: 0}, 150);
                        
                        //=== Set phone to into input
                        $(".service-form_forgot_block .service-form_forgot_change_phone input[name=phone]").val(response.phone);
                        $(".service-form_forgot_block .service-form_forgot_set_password input[name=phone]").val(response.phone);
                            
                        //=== Timer for resend SMS
                        var repeat_btn_container = $(".service-form_forgot_confirm_sms .service-resend_sms");
                        var timer_container = $(".service-form_forgot_confirm_sms .service-resend_sms_timer");
                        repeatSmsBlock(repeat_btn_container, timer_container, response.next_try);

                        toastrPrintList(response.success_message, 'success');
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
    
    
//=== Forgot password (resend SMS)
    $("body").on("click", ".service-form_forgot_confirm_sms .service-resend_sms", function () {
        var _this_button = $(this);
        var _this_preloader = $(this);

        if (_this_button.hasClass("no_click")) {
            return false;
        }
        
        var phorm_data = new Object();
        phorm_data.action_auth = "forgot_resend_sms";

        $.ajax({
            url: "/ajax/auth.php",
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
                        
                        //=== Set phone to into input
                        $(".service-form_forgot_block .service-form_forgot_change_phone input[name=phone]").val(response.phone);
                        $(".service-form_forgot_block .service-form_forgot_set_password input[name=phone]").val(response.phone);
                        
                        //=== Timer for resend SMS
                        var repeat_btn_container = $(".service-form_forgot_confirm_sms .service-resend_sms");
                        var timer_container = $(".service-form_forgot_confirm_sms .service-resend_sms_timer");
                        repeatSmsBlock(repeat_btn_container, timer_container, response.next_try);

                    } else {
                        toastrPrintList(response.error, 'error');
                    }
                }

                return false;
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
    
//=== Forgot password (change phone)
    $("body").on("click", ".service-form_forgot_change_phone .service-form_submit", function () {        
        $(".service-form_forgot_change_phone form").submit();
        return false;
    });
    $("body").on("submit", ".service-form_forgot_change_phone form", function () {
        var _this_form = $(this);
        var _this_button = _this_form.find(".service-form_submit");
        var _this_preloader = _this_form.find(".service-form_submit");

        var phorm_data = _this_form.serialize();

        if (_this_button.hasClass("no_click")) {
            return false;
        }

        _this_button.addClass("no_click");
        _this_preloader.addClass("loading");

        $.ajax({
            url: _this_form.attr("action"),
            type: _this_form.attr("method"),
            data: phorm_data,
            success: function (response) {
                if (typeof (response) != 'undefined' && response != null) {
                    if (response.success > 0) {                        
                        //=== Timer for resend SMS
                        var repeat_btn_container = $(".service-form_forgot_confirm_sms .service-resend_sms");
                        var timer_container = $(".service-form_forgot_confirm_sms .service-resend_sms_timer");
                        repeatSmsBlock(repeat_btn_container, timer_container, response.next_try);

                        toastrPrintList(response.success_message, 'success');
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

//=== Forgot password (confirm sms)
    $("body").on("click", ".service-form_forgot_confirm_sms .service-form_submit", function () {        
        $(".service-form_forgot_confirm_sms form").submit();
        return false;
    });
    $("body").on("submit", ".service-form_forgot_confirm_sms form", function () {
        var _this_form = $(this);
        var _this_button = _this_form.find(".service-form_submit");
        var _this_preloader = _this_form.find(".service-form_submit");

        var phorm_data = _this_form.serialize();

        if (_this_button.hasClass("no_click")) {
            return false;
        }

        _this_button.addClass("no_click");
        _this_preloader.addClass("loading");

        $.ajax({
            url: _this_form.attr("action"),
            type: _this_form.attr("method"),
            data: phorm_data,
            success: function (response) {
                if (typeof (response) != 'undefined' && response != null) {
                    if (response.success > 0) {
                        //=== Set password
                        $(".service-form_forgot_block .service-form_forgot_check_phone").hide(); 
                        $(".service-form_forgot_block .service-form_forgot_change_phone").hide();
                        $(".service-form_forgot_block .service-form_forgot_confirm_sms").stop().animate({height: 0}, 150);
                        autoHeightAnimate($(".service-form_forgot_block .service-form_forgot_set_password"), 150);

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
                _this_button.removeClass('no_click');
                _this_preloader.removeClass('loading');
            }
        });

        return false;
    });
    
//=== Forgot password (set password)
    $("body").on("click", ".service-form_forgot_set_password .service-form_submit", function () {        
        $(".service-form_forgot_set_password form").submit();
        return false;
    });
    $("body").on("submit", ".service-form_forgot_set_password form", function () {
        var _this_form = $(this);
        var _this_button = _this_form.find(".service-form_submit");
        var _this_preloader = _this_form.find(".service-form_submit");

        var phorm_data = _this_form.serialize();

        if (_this_button.hasClass("no_click")) {
            return false;
        }

        _this_button.addClass("no_click");
        _this_preloader.addClass("loading");

        $.ajax({
            url: _this_form.attr("action"),
            type: _this_form.attr("method"),
            data: phorm_data,
            success: function (response) {
                if (typeof (response) != 'undefined' && response != null) {
                    if (response.success > 0) {
                        $.fancybox.close();

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
                _this_button.removeClass('no_click');
                _this_preloader.removeClass('loading');
            }
        });

        return false;
    });
});