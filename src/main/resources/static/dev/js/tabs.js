$(function () {
    $('ul.Jtabs__caption').on('click', 'li:not(.active)', function() {
        $(this)
            .addClass('active').siblings().removeClass('active')
            .closest('div.Jtabs').find('div.Jtabs__content').removeClass('active').eq($(this).index()).addClass('active');
    });
    $('ul.basket-page-place-map-tabs').on('click', 'li:not(.active-xs)', function() {
        $(this)
            .addClass('active-xs').siblings().removeClass('active-xs')
            .closest('div.basket-page-place-map').find('div.thisTabsContent').removeClass('active-xs').eq($(this).index()).addClass('active-xs');
    });
});
