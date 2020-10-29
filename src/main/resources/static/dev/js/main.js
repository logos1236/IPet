$(function () {
    /*Scroll to href*/
    $('a[href^="#"].scrl').on('click', function(event) {
        var $target = $(this.getAttribute('href')),
            $widthScreen = $(window).width();
        if( $target.length ) {
            event.preventDefault();
            if ($widthScreen > "1219") {
                $('html, body').stop().animate({
                    scrollTop: $target.offset().top -130
                }, 500);
            } else {
                $('html, body').stop().animate({
                    scrollTop: $target.offset().top
                }, 500);
            }
        }
    });

    /*Form_Styler*/
    setTimeout(function () {
        $('input.stlr, select.stlr').not(':file').styler({
            onFormStyled: function() {
		//=== Sort select preloader
		if ($(".category-page-sort").length) {
                    $(".category-page-sort .thisValue").removeClass("loading");
                }
                
                if ($(".form-line.for-load.loading").length) {
                    $(".form-line.for-load.loading").removeClass("loading");
                }
                
                if ($(".service_filter_group").length) {
                    $(".sales-page-select").removeClass("loading");
                }
                
                if ($(".service_set-city").length) {
                    $(".cityChoice-link").removeClass("loading");
                }
            }
        });
        $('input.resume_file_input:file').styler({
            onFormStyled: function() {
                if ($(".form-line.for-load.loading").length) {
                    $(".form-line.for-load.loading").removeClass("loading");
                }
                
                $(".jq-file.resume_file_input").append("<div class='jq-file__remove'></div>");
            }
        });
        
        $("body").on("click", ".jq-file.resume_file_input .jq-file__remove", function () {
            $("input.resume_file_input:file").wrap('<form>').closest('form').get(0).reset();
            $("input.resume_file_input:file").unwrap();
            $("input.resume_file_input:file").trigger('refresh');
            $(".jq-file.resume_file_input").append("<div class='jq-file__remove'></div>");
        });
    }, 100);
    setTimeout(function () {
        $('select.stlr-filter').styler({
            selectSmartPositioning: false,
            onSelectOpened: function() {
                $('#header').addClass('blacked');
                $('#content').addClass('blacked');
                $('#allfooter').addClass('blacked');
                $(this).css('z-index', 'auto');
                $(this).find('.jq-selectbox__dropdown').css('z-index', '10');
            },
            onSelectClosed: function() {
                $('#header').removeClass('blacked');
                $('#content').removeClass('blacked');
                $('#allfooter').removeClass('blacked');
                $(this).css('z-index', '10');
            }
        });
    }, 100);

    /*Auth hidden blocks*/
    $('#auth_nexn_btn').on('click', function () {
        var $parent = $(this).closest('.auth-block-phone'),
            $this = $(this);
        $parent.addClass('active');
        autoHeightAnimate($parent.find('.auth-block-phone-hide'), 150);
        $this.hide();
        return false;
    });
    $('#auth_checkbox_email').change(function() {
        var $parent = $(this).closest('.auth-block-email').find('.auth-block-email-hide'),
            $this = $(this);
        if ($this.is(':checked')) {
            autoHeightAnimate($parent, 150);
        } else {
            $parent.stop().animate({height: 0}, 150);
        }
    });

    /*Send hidden blocks*/
    $('#send_checkbox_phone').change(function() {
        var $parent = $(this).closest('.send-block-phone').find('.send-block-phone-hide'),
            $this = $(this);
        if ($this.is(':checked')) {
            autoHeightAnimate($parent, 150);
        } else {
            $parent.stop().animate({height: 0}, 150);
        }
    });

    /*basket drugstore hidden block*/
    $('.basket-page-place-table-item .thisLink a').on('click', function () {
        var $parent = $(this).closest('.basket-page-place-table-item');
        if ($parent.hasClass('active')) {
            $parent.find('.basket-page-place-table-item-bottom').stop().animate({height: 0}, 150);
            $parent.removeClass('active');
        } else {
            $parent.addClass('active');
            autoHeightAnimate($parent.find('.basket-page-place-table-item-bottom'), 150);
        }
        return false
    });

    /*tabs*/
    $('.tabs').find('.tabs-nav-elem a').on('click', function() {
        var $this = $(this);
        if (!$this.closest('.tabs-nav-elem').hasClass('active')) {
            $this.closest('.tabs-nav-elem').addClass('active').siblings().removeClass('active');
            $this.closest('.tabs')
                .find('>.tabs-content >.tabs-content-elem').removeClass('active')
                .eq($(this).closest('.tabs-nav-elem').index()).addClass('active');
        }
        return false;
    });

    /*catalog page accordion*/
    $('.catalog-page-accordion-elem-top').on('click', function () {
        if (!$(this).hasClass("link")) {
            var $parent = $(this).closest('.catalog-page-accordion-elem');
            if ($parent.hasClass('active')) {
                $parent.find('.catalog-page-accordion-elem-bottom').stop().animate({height: 0}, 150);
                $parent.removeClass('active');
            } else {
                $parent.addClass('active');
                autoHeightAnimate($parent.find('.catalog-page-accordion-elem-bottom'), 150);
            }
            return false;
        }
    });
    /*help page accordion*/
    $('.help-block-accordion-elem-top').on('click', function () {
        var $parent = $(this).closest('.help-block-accordion-elem');
        if ($parent.hasClass('active')) {
            $parent.find('.help-block-accordion-elem-bottom').stop().animate({height: 0}, 150);
            $parent.removeClass('active');
        } else {
            $parent.addClass('active');
            autoHeightAnimate($parent.find('.help-block-accordion-elem-bottom'), 150);
        }
        return false;
    });
    /*vacancy page accordion*/
    $('.vacancy-block-accordion-elem-top').on('click', function () {
        var $parent = $(this).closest('.vacancy-block-accordion-elem');
        if ($parent.hasClass('active')) {
            $parent.find('.vacancy-block-accordion-elem-bottom').stop().animate({height: 0}, 150);
            $parent.removeClass('active');
        } else {
            $parent.addClass('active');
            autoHeightAnimate($parent.find('.vacancy-block-accordion-elem-bottom'), 150);
        }
        return false;
    });

    /*resize */
    var wScreen = $(window).width();
    $(window).ready(changeResize(wScreen)).resize(
        function () {
            var wScreen = $(window).width();
            changeResize(wScreen);
        }
    );

    /*значок показать\скрыть символы в инпуте*/
    $('.input-pass-icon').on('click', function () {
        var $parent = $(this).closest('.input-pass');
        var this_input = $parent.find("input");
        
        if ($parent.hasClass('show')) {
            $parent.removeClass('show');
            
            if (this_input.length) {
                this_input.attr("type","password");
            }
        } else {
            $parent.addClass('show');
            
            if (this_input.length) {
                this_input.attr("type","text");
            }
        }
        return false
    });

    /*lk toggle inputs pass*/
    $('.lk-profile-change-link a').on('click', function () {
        var $parent = $(this).closest('.lk-profile-change'),
            $this = $(this);
        $parent.addClass('active');
        autoHeightAnimate($parent.find('.lk-profile-change-hide'), 150);
        $this.replaceWith( $('<p/>').text( $this.text() + ':' ) );
        return false
    });
    /*lk orders hidden block*/
    $('.lk-profile-order-table-item a.thisLink').on('click', function () {
        var $parent = $(this).closest('.lk-profile-order-table-item'),
            $this = $(this);
        if ($parent.hasClass('active')) {
            $parent.find('.lk-profile-order-table-item-bottom').stop().animate({height: 0}, 150);
            $parent.removeClass('active');
            $this.text('Показать товары');
        } else {
            $parent.addClass('active');
            autoHeightAnimate($parent.find('.lk-profile-order-table-item-bottom'), 150);
            $this.text('Скрыть товары');
        }
        return false
    });

    /*Маска на input номера телефона */
    $('.phone-mask').inputmask("phoneru", {
        showMaskOnHover: false
    });
});

function autoHeightAnimate(element, time) {
    var curHeight = element.height(),
        autoHeight = element.css('height', 'auto').height();
    element.height(curHeight);
    element.stop().animate({height: autoHeight}, parseInt(time));
}

function changeResize($wScreen) {
    $('input.stlr, select.stlr, select.stlr-filter, input.stlrF').not(':input[type=file]').trigger('refresh');
    $('#header').removeClass('blacked');
    $('#content').removeClass('blacked');
    $('#allfooter').removeClass('blacked');
    if ($wScreen > "1219") {
        $('.header-bottom-input input').attr('placeholder','Название препарата, производитель или действующее вещество');
    } else {
        $('.header-bottom-input input').attr('placeholder','Название препарата');
    }
}
