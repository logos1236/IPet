$(document).ready(function () {           
    //=== Sort on catalog page
    if ($("select.service_product_sort").length) {
        $("body").on("change", "select.service_product_sort", function(){
            var _this = $(this);

            window.location = _this.val().trim();
        });
    }
    
    //=== Search on header
    $("body").on("click", ".service_search_input", function(){
        $(this).closest("form").submit();
        return false;
    });
    
    //=== Load more on catalog page
    if ($(".service_load_more_catalog_elem_btn").length) {
        $("body").on("click", ".service_load_more_catalog_elem_btn", function(){
            var form = $(".service_load_more_catalog_elem_form");   
            var page_num_input = form.find("input[name=PAGEN_1]");
            var total_count_input = form.find("input[name=total_count]");
            var container = $(".items-table-inn");
            var btn_container = $(this).closest(".items-table-more");

            var _this_button = $(this);
            var _this_preloader = $(this).closest('.for-load');

            //=== Create query data
                var phorm_data = form.serialize();

                var search_data = window.location.search.substring(1);
                if (search_data != "" && phorm_data != "") {
                    phorm_data += "&";
                }
                if (search_data != "") {
                    phorm_data += search_data;
                }

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
                        var current_count_element = container.find(".item-card").length;

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
    }
    
    //=== Filter by group on discount page
    if ($(".service_filter_group").length) {
        $("body").on("change", ".service_filter_group", function(){
            var _this = $(this);
            var form = _this.closest("form");   
            var _this_preloader = _this.closest(".sales-page-select.for-load");

            _this_preloader.addClass("loading");
            form.submit();

            return false;
        });
    }
    
    //=== Send request for receipt product
        $("body").on("click", ".service_send_receipt", function(){
            var _this_button = $(this);
            var _this_preloader = $(this).closest('.for-load');
            
            var form = _this_button.closest(".service_send_receipt_form");
            var service_send_receipt_container = _this_button.closest(".service_send_receipt_container");

            var form_data = form.serialize();

            if (_this_preloader.hasClass("no_click")) {
                return;
            }

            $.ajax({
                url: form.attr("action"),
                method: form.attr("method"),
                dataType: 'json',
                data: form_data,
                beforeSend: function () {
                    _this_button.addClass("no_click");
                    _this_preloader.addClass("loading");
                },
                success: function (response) {
                    if (typeof (response) != 'undefined' && response != null) {
                        /*if (response.success > 0) {
                            toastrPrintList(response.success_message, 'success');
                        } else {
                            toastrPrintList(response.error, 'error');
                        }*/

                        if (typeof (response.url_redirect) != 'undefined' && response.url_redirect != null) {
                            window.location = response.url_redirect;
                        }    
                        
                        $.fancybox.open({
                            src  : '#modal__product',
                            btnTpl: {
                                close: '',
                                smallBtn: ''
                            }
                        });
                        
                        service_send_receipt_container.hide();
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
        
        
    //=== Fix bug
    $("body").on("click",".modal_link", function(){
        if ($(this).attr("href") == "#modal__login") {
            $("#modal__login input[name=phone]").val("");
            $("#modal__login input[name=password]").val("");
            $("#modal__login_2 input[name=password]").val("");
        }
    });
    
    
    //=== To up page 
    function backToTopShow() {
        if($(window).scrollTop()>=30){
            $(".service_back_to_top").addClass("visible");
        } else {
            $(".service_back_to_top").removeClass("visible");
        }
    }
    $("body").on("click",".service_back_to_top", function(){
        $("html, body").animate({ scrollTop: 0 }, "slow");
        return false;
    });
    $(window).scroll(function(){
        backToTopShow();
    });
});
