$(document).ready(function () {
    //=== Load more on catalog page
    $("body").on("click", ".service_load_more_special_btn", function(){
        var form = $(".service_load_more_special_form");   
        var page_num_input = form.find("input[name=PAGEN_1]");
        var total_count_input = form.find("input[name=total_count]");
        var container = $(".service_load_more_special_container");
        var btn_container = $(this).closest(".service_load_more_special_btn_container");
        
        var _this_button = $(this);
        var _this_preloader = $(this).closest('.for-load');
        
        //=== Create query data
            var phorm_data = form.serialize();
        
        if (_this_preloader.hasClass("no_click")) {
            return;
        }
        
        $.ajax({
            url: form.attr("action"),
            type: form.attr("method"),
            data: phorm_data,
            dataType: 'html',
            beforeSend: function () {
                _this_button.addClass('no_click');
                _this_preloader.addClass('loading');
            },
            success: function (reply) {
                //=== Add cards
                    var html_elements = $.parseHTML(reply);
                    container.append(html_elements);
                    
                //=== Update count of cards
                    var current_count_element = container.find(".service_item_card").length;
                                    
                //=== Update page counter   
                    page_num_input.val(page_num_input.val()*1+1);
                
                //=== Hide btn
                    if (current_count_element >= total_count_input.val()) {
                        btn_container.hide();
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
