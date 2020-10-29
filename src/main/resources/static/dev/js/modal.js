$(function () {
    /*Модалка авторизации*/
    /*$('.modal_link').fancybox({
        btnTpl: {
            close: '',
            smallBtn: ''
        }
    });*/
    
    $("body").on("click", ".modal_link", function(){
        $.fancybox.open({
            src  : $(this).attr("href"),
            btnTpl: {
                close: '',
                smallBtn: ''
            }
        });
    });
    
    $('.modal_close').on('click', function () {    
        $.fancybox.close();
        return false;
    });

    $('#modal__login-reg').on('click', function () {
        $.fancybox.close();
        $.fancybox.open({
            src  : '#modal__login_2',
            btnTpl: {
                close: '',
                smallBtn: ''
            }
        });
        return false
    });
    $('#modal__login-reg-2').on('click', function () {
        $.fancybox.close();
        $.fancybox.open({
            src  : '#modal__login_2-2',
            btnTpl: {
                close: '',
                smallBtn: ''
            }
        });
        return false
    });
    $('#modal__login-forgot').on('click', function () {
        $.fancybox.close();
        $.fancybox.open({
            src  : '#modal__login_3',
            btnTpl: {
                close: '',
                smallBtn: ''
            }
        });
        
        if ($(".service-form_forgot_block .service-form_forgot_check_phone").length) {
            $(".service-form_forgot_block .service-form_forgot_check_phone").show(); 
        }
        
        if ($(".service-form_forgot_block .service-form_forgot_change_phone").length) {
            $(".service-form_forgot_block .service-form_forgot_change_phone").hide();
        }
        
        if ($(".service-form_forgot_block .service-form_forgot_confirm_sms").length) {
            $(".service-form_forgot_block .service-form_forgot_confirm_sms").stop().animate({height: 0}, 150);
        }
        
        if ($(".service-form_forgot_block .service-form_registration_set_password").length) {
            $(".service-form_forgot_block .service-form_registration_set_password").stop().animate({height: 0}, 150);
        }
        
        return false;
    });

    $('#modal__login-enter').on('click', function () {
        var $parent = $(this).closest('.modal_block'),
            $this = $(this);

        $this.removeClass('button_color1');
        $this.addClass('button_color2');
        $this.text('Изменить');
        $parent.addClass('active-sms');
        autoHeightAnimate($parent.find('.modal__login-sms'), 150);

        $parent.removeClass('active-pass');
        $parent.find('.modal__login-pass').stop().animate({height: 0}, 150);
        return false
    });
    $('#modal__login-sms').on('click', function () {
        var $parent = $(this).closest('.modal_block');

        $parent.find('#modal__login-enter').closest('.modal__login-line-two').hide();
        $parent.addClass('active-pass');
        autoHeightAnimate($parent.find('.modal__login-pass'), 150);

        $parent.removeClass('active-sms');
        $parent.find('.modal__login-sms').stop().animate({height: 0}, 150);
        return false
    });
    $('#modal__login-reg-sms').on('click', function () {
        var $parent = $(this).closest('.modal_block');

        $parent.find('#modal__login-reg-enter').closest('.modal__login-line-two').hide();
        $parent.addClass('active-reg-pass');
        autoHeightAnimate($parent.find('.modal__login-reg-pass'), 150);

        $parent.find('.modal__login-reg-sms').stop().animate({height: 0}, 150);
        return false
    });

    $('#modal__login-close').on('click', function () {
        var $parent = $(this).closest('.modal_block');
        $.fancybox.close();
        setTimeout(function () {
            $parent.removeClass('active-pass');
            $parent.removeClass('active-sms');
            $parent.find('.modal__login-sms').stop().animate({height: 0}, 150);
            $parent.find('.modal__login-pass').stop().animate({height: 0}, 150);

            $parent.find('#modal__login-enter').closest('.modal__login-line-two').show();
            $parent.find('#modal__login-enter').removeClass('button_color2');
            $parent.find('#modal__login-enter').addClass('button_color1');
            $parent.find('#modal__login-enter').text('Ввести');
        }, 100);
        return false
    });
    $('#modal__login-reg-close').on('click', function () {
        var $parent = $(this).closest('.modal_block');
        $.fancybox.close();
        setTimeout(function () {
            $parent.removeClass('active-pass');
            $parent.removeClass('active-sms');
            $parent.find('.modal__login-reg-pass').stop().animate({height: 0}, 150);
            autoHeightAnimate($parent.find('.modal__login-reg-sms'), 150);

            $parent.find('#modal__login-reg-enter').closest('.modal__login-line-two').show();
        }, 100);
        return false
    });

    //Модалка актиация бонусной карты
    $('#modal__bonus-active').on('click', function () {
        var $parent = $(this).closest('.modal_block');

        $parent.find('.modal__bonus-first').hide();
        $parent.addClass('active-sms');
        autoHeightAnimate($parent.find('.modal__login-sms'), 150);
        return false
    });
    $('#modal__bonus-sms').on('click', function () {
        var $parent = $(this).closest('.modal_block');
        $.fancybox.close();
        setTimeout(function () {
            $parent.removeClass('active-sms');
            $parent.find('.modal__bonus-first').show();
            $parent.find('.modal__login-sms').stop().animate({height: 0}, 150);
        }, 200);
        return false
    });
});
