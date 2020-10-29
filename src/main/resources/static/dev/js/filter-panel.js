$(function () {
    setTimeout(function () {
        if ($('input.stlrF').length) {
            $('input.stlrF').styler({
                onFormStyled: function() {
                    filtersPanelToggle();
                }
            });
        } else {
            $('.filters-panel').removeClass('loading');
        }
    }, 100);
    $('.selectCb').on('click', function () {
        var $this = $(this);
        $this.closest('.filters-panel-elem').find('input').addChecked().trigger('refresh');
        return false
    });
    $('.clearCb').on('click', function () {
        var $this = $(this);
        $this.closest('.filters-panel-elem').find('input').delChecked().trigger('refresh');
        return false
    });

    $.fn.addChecked = function () {
        return this.each(function () {
            this.checked = true;
        });
    };
    $.fn.delChecked = function () {
        return this.each(function () {
            this.checked = false;
        });
    };

    $('#openFilterPanel').on('click', function () {
        $('#content').addClass('openFilterPanel');
        return false
    });
});

function filtersPanelToggle() {
    var $toggle = $('.filters-panel-toggle-inn'),
        $count = $toggle.length,
        $widthScreen = $(window).width();

    $toggle.each(function(i) {
        var $parent =  $(this);
        if ($parent.children('.cb').length > 5) {
            var $sum = 0;
            $('.cb', $parent).each(function(index) {
                var $child =  $(this);
                var $htmlLink = '<div class="filters-panel-toggle-link"><a href="#"><span class="thisText">Показать всё</span><i class="icon icon-down"></i></a></div>';
                if (index < 5) {
                    if ($widthScreen > "1219") {
                        $sum += $child.outerHeight(true);
                    } else {
                        $sum += 32; // блок в мобильном виде скрыт, поэтому статикой выставим height + margin
                    }
                    $parent.height($sum - 17); // minus last margin
                    if (index < 1) {
                        $parent.closest('.filters-panel-toggle').append($htmlLink).contents();
                    }
                }
            });
        }

        $parent.closest('.filters-panel-toggle').find('.filters-panel-toggle-link a').click(function () {
            var $i = $(this).closest('.filters-panel-toggle');
            if ($i.hasClass('active')) {
                $i.find('.filters-panel-toggle-inn').stop().animate({height: $sum - 17}, 150);
                $i.removeClass('active');
                $(this).find('.thisText').text('Показать всё');
            } else {
                $i.addClass('active');
                autoHeightAnimate($i.find('.filters-panel-toggle-inn'), 150);
                $(this).find('.thisText').text('Скрыть');
            }
            return false
        });

        if (i+1 === $count) {
            $('.filters-panel').removeClass('loading');
        }
    });
}
$(window).resize(function() {
    var $toggle = $('.filters-panel-toggle-inn'),
        $widthScreen = $(window).width();

    $toggle.each(function() {
        var $parent =  $(this);
        if ($parent.children('.cb').length > 5) {
            var $sum = 0;
            $('.cb', $parent).each(function(index) {
                var $child =  $(this);
                if (index < 5) {
                    if ($widthScreen > "1219") {
                        $sum += $child.outerHeight(true);
                    } else {
                        $sum += 32; // блок в мобильном виде скрыт, поэтому статикой выставим height + margin
                    }
                    $parent.height($sum - 17); // minus last margin
                }
            });
        }
        $parent.closest('.filters-panel-toggle').removeClass('active');
        $parent.closest('.filters-panel-toggle').find('.filters-panel-toggle-link .thisText').text('Показать всё');
    });
});

$(function () {
    //-----------UI_Sliedr-----------//
    var html5Slider = document.getElementById('noUiPrice'),
            select1 = document.getElementById('noUiPrice_ui1'),
            select2 = document.getElementById('noUiPrice_ui2');

    if (select1 && select2) {
        noUiSlider.create(html5Slider, {
            start: [select1.getAttribute('value') * 1, select2.getAttribute('value') * 1],
            connect: true,
            range: {
                'min': select1.getAttribute('data-extrem_val') * 1,
                'max': select2.getAttribute('data-extrem_val') * 1
            }
        });
        html5Slider.noUiSlider.on('update', function (values, handle) {
            var value = values[handle];

            if (handle) {
                select2.value = value.replace(/(\d)(?=(\d\d\d)+([^\d]|$))/g, '$1 ').replace(/\.\d{2}/, " ₽");
            } else {
                select1.value = value.replace(/(\d)(?=(\d\d\d)+([^\d]|$))/g, '$1 ').replace(/\.\d{2}/, " ₽");
            }
        });

        select1.addEventListener('change', function () {
            Math.round(this.value);
            html5Slider.noUiSlider.set([this.value.replace(' ₽', '').replace(/\s+/g, ''), null]);
        });
        select2.addEventListener('change', function () {
            html5Slider.noUiSlider.set([null, this.value.replace(' ₽', '').replace(/\s+/g, '')]);
        });
    }
    
    if (document.getElementById('filters_clear')) {
        document.getElementById('filters_clear').addEventListener('click', function () {            
            if (select1 && select2) {  
                html5Slider.noUiSlider.set([select1.getAttribute('data-extrem_val')*1, select2.getAttribute('data-extrem_val')*1]);
                
                select1.disabled = true;
                select2.disabled = true;
            }

            var $this = $(this);
            $this.closest('.filters-panel-inn').find('input').delChecked().trigger('refresh');

            var _form = $(this).closest("form");
            if (_form.length) {
                _form.submit();
            }

            $('#content').removeClass('openFilterPanel');
            
            $(this).closest(".for-load").addClass("loading");
        });
    }
    
    $("body").on("click", ".service_filter_catalog_form input[type=submit]", function () {
        $(this).closest(".for-load").addClass("loading");
    });
});
