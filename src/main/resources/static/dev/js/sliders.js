$(function () {
    $(window).on('load', function () {
        $('.slider_loading').removeClass("slider_loading");

        /*Слайдер на главной*/
        $('#fs-slider').slick({
            dots: false,
            arrows: true,
            infinite: false,
            slidesToShow: 1,
            autoplay: true,
            adaptiveHeight: true,
            autoplaySpeed: 4000,
        });

        /*Слайдер с товарами*/
        var $sale_carousel = $('.product-slider');
        function showSaleSliderScreen($widthScreen) {
            if ($widthScreen > "1219") {
                if (!$sale_carousel.hasClass('slick-initialized')) {
                    $sale_carousel.slick({
                        dots: false,
                        arrows: true,
                        slidesToShow: 1,
                        adaptiveHeight: true,
                        infinite: false
                    });
                }
            } else {
                if ($sale_carousel.hasClass('slick-initialized')) {
                    $sale_carousel.slick("unslick");
                }
            }
        }
        $('.partner-slider').slick({
            dots: false,
            arrows: true,
            slidesToShow: 6,
            slidesToScroll: 6,
            adaptiveHeight: true,
            infinite: false,
            responsive: [
                {
                    breakpoint: 1219,
                    settings: {
                        slidesToShow: 4,
                        slidesToScroll: 4,
                        dots: true,
                        arrows: false,
                    }
                }
            ]
        });
        var widthScreen = $(window).width();
        $(window).ready(showSaleSliderScreen(widthScreen)).resize(
            function () {
                var widthScreen = $(window).width();
                showSaleSliderScreen(widthScreen);
            }
        );

        /*Слайдер с новостями*/
        $('.news-slider').slick({
            dots: false,
            arrows: true,
            infinite: false,
            slidesToShow: 4,
            variableWidth: true,
            responsive: [
                {
                    breakpoint: 1219,
                    settings: {
                        arrows: false,
                        slidesToShow: 1
                    }
                }
            ]
        });

        /*Слайдер "Вы смотрели"*/
        $('.back-to-see-slider').slick({
            dots: false,
            arrows: true,
            infinite: false,
            slidesToShow: 1,
            adaptiveHeight: true
        });

        /*Слайдер картинок товара в карточке товара*/
        if ($('.product-page-first-img-slider').length) {
            var show_dots = ($('.product-page-first-img-slider li').length > 1) ? true : false;

            $('.product-page-first-img-slider').slick({
                dots: show_dots,
                arrows: false,
                infinite: false,
                slidesToShow: 1,
                adaptiveHeight: true
            });
        }
    });
});
