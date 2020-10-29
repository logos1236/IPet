$(function () {
    /*каталог в шапке*/
    $('.header-catalog-block-btn').on('click', function () {
        var $parent = $(this).closest('.header-catalog-block');
        $parent.addClass('active');
        $(".header-basket-block").removeClass('active');
        $(".cityChoice").removeClass('active');
        return false;
    });
    $(document).on("click", function(event){
        var $target = $(".header-catalog-block");
        if($target !== event.target && !$target.has(event.target).length){
            $target.removeClass('active');
            $(".thisLink").removeClass('active');
        }
    });
    /*каталог в шапке - второй уровень*/
    $('.NextLvl .thisLink-inn a').on('click', function () {
        if ($(this).attr("href") == "#" || $(this).attr("href") == "") {
            var $parent = $(this).closest('.thisLink');
            $(".thisLink").removeClass('active');
            $parent.addClass('active');
            return false;
        }
    });

    /*выбор города в шапке*/
    $('.cityChoice-link .cityChoice-link-btn').on('click', function () {
        var $parent = $(this).closest('.cityChoice-link');
        $parent.find('.cityChoice').addClass('active');
        $(".header-basket-block").removeClass('active');
        $(".header-catalog-block").removeClass('active');
        $(".city_accept").removeClass('active');
        return false;
    });
    $('.service_select_city_btn').on('click', function () {
        $('.cityChoice-link .cityChoice-link-btn').click();
        $('.cityChoice-link .jq-selectbox__select-text').click();
        return false;
    });
    $(document).on("click", function(event){
        var $target = $(".cityChoice");
        if($target !== event.target && !$target.has(event.target).length){
            $target.removeClass('active');
        }
    });
});
