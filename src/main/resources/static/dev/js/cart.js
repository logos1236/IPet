//=== New/clear product list for add
    function createProductListForAdd() {
        try {
            delete window.change_basket; 
        } catch (error) {
            
        }
        window.change_basket = new Object();
        window.change_basket.time_exec = 0;
        window.change_basket.products_for_add = new Array();
    }

//=== Change product quantiy in basket
    function changeProductQuantity(id, quantity) {
        var add_obj = new Object();
        
        //=== xnl_id
        add_obj.id = id * 1;
        
        //=== Quantity
        add_obj.quantity = quantity * 1;
        
        //=== Execute time
        time_exec = ((new Date).getTime())*1 + 700;
        
        //=== Change basket
            if (typeof(window.change_basket) !== 'object') {    
                createProductListForAdd();
            }
            
            //=== Time change basket
                if (window.change_basket.time_exec < time_exec) {
                    window.change_basket.time_exec = time_exec;
                }

            //=== Product change basket  
                var is_add_elem = true;
                $.each(window.change_basket.products_for_add, function( index, value ) {

                    if (value.id == add_obj.id) {
                        window.change_basket.products_for_add[index].quantity = add_obj.quantity;
                        is_add_elem = false;
                    }
                });

                if (is_add_elem) {
                    window.change_basket.products_for_add.push(add_obj);
                }

            window.timer = window.setTimeout(checkProductForAdd, 1000);
    }
    
//=== Check product list for add
    function checkProductForAdd() {
        var current_time = (new Date).getTime();
        var _this_preloader = $(".loading");
        
        if (window.change_basket.time_exec < current_time && window.change_basket.products_for_add.length) {            
            //=== Send info
            var data_product = window.change_basket.products_for_add;
            
            //=== Fix bug: double send form because wait server answer
            createProductListForAdd();
            
            //=== Clear timer
            //clearTimeout(window.timer);

            //=== Send form
            $.ajax({
                url: window.url_add,
                method: 'post',
                dataType: 'json',
                data: {
                    product: JSON.stringify(data_product),
                },
                success: function (json) {
                    updateCartView(json);
                },
                error: function () {
                    toastr.error(window.toastr_error);
                },
                complete: function () {
                    _this_preloader.removeClass("loading");
                }
            });
        }
    }
    
//=== Change count of product on card
    function productChangeCountCart(_this) {                
        var id = _this.closest(".service_product_element").find(".service_product_info_form input[name=id]").val();
        var method = _this.data('method');
        
        //=== Set quantity
        if (_this.prop("tagName") == "INPUT") {
            var quantity = _this.val();
        } else {
            var quantity = _this.closest(".service_product_element").find(".service_product_info_form input[name=quantity]").val();
            
            if (method == "add") {
                quantity = quantity * 1 + 1;
            }
            if (method == "reduce") {
                quantity = quantity * 1 - 1;
            }
            if (method == "remove") {
                quantity = 0;
            }
            if (quantity < 0) {
                quantity = 0;
            }
        }
        
        //=== List of same product-input on page
        var input_list = $(".service_product_info_form input[name=id]").filter(function(){
            return $(this).val() == id;
        });
        
        jQuery.each(input_list, function(i) {
            var product_element = $(this).closest(".service_product_element");
            var product_info_form = product_element.find(".service_product_info_form");

            //=== Loader
            var loader = product_element.find(".for-load");
            if (loader.length) {                
                loader.addClass("loading");
            }

            //=== Card info
            var quantity_input_text = product_element.find(".service_quantity_text_input");

            //=== Form product info
            var quantity_input = product_info_form.find("input[name=quantity]");

            //=== Change card
            quantity_input_text.val(quantity);

            //=== Change card template
            if (quantity > 0) {
                product_element.addClass("active");
            } else {
                product_element.removeClass("active");
            }

            //=== Change form
            quantity_input.val(quantity);
        });
        
        //=== Set product to wait list
        changeProductQuantity(id, quantity);
        window.url_add = $(".service_product_info_form").attr("action");
    } 
    
//=== Update cart after change product quantity
    function updateCartView(data_json) {
        try {
            if (typeof data_json != 'undefined') {
                $("#cart").html(data_json.cart);
            }
        } catch (e) {
            console.log("Error update cart in function cartAdditionalListProduct");
        }
    }
    
//=== Update additional product list after change product quantity
    function cartAdditionalListProduct(is_empty_cart) {
        var $sale_carousel = $('.product-slider');
        $sale_carousel.slick("unslick");
        
        if (is_empty_cart) {
            $(".service_product_popular_cart").show();
            $(".service_product_related_cart").hide();
            
            //=== Update view of cards
            $(".service_product_element.active").removeClass("active");
            $(".service_product_info_form input[type=quantity]").val(0); 
        } else {
            $(".service_product_popular_cart").hide();
            $(".service_product_related_cart").show();
        } 
        
        var resizeEvent = new Event('resize');
        window.dispatchEvent(resizeEvent);
        $(".slider_loading").removeClass("slider_loading");
    }    
    
jQuery(document).ready(function () {
//=== Change product quantity in cart
    $("body").on("click", ".service_product_change_cart", function(){   
        var _this = jQuery(this);
        productChangeCountCart(_this);        
        return false;
    });
    $("body").on("change", ".service_quantity_text_input", function(){   
        var _this = jQuery(this);        
        productChangeCountCart(_this);
        return false;
    });
    $("body").on("keyup", ".service_quantity_text_input", function(e){   
        var code = e.which;
        if(code==13)e.preventDefault();
        if(code==32||code==13||code==188||code==186){
            var _this = jQuery(this);        
            productChangeCountCart(_this);
        }
        return false;
    });
    
    $("body").on("click", ".service_product_clear_cart", function(){  
        var form_data = new Object();
        form_data.clear_cart = 1;
        
        var loader = $(this).closest(".for-load");
        
        //=== Send form
            $.ajax({
                url: "/ajax/product.php",
                method: 'post',
                dataType: 'json',
                data: form_data,
                beforeSend: function () {
                    loader.addClass("loading");
                },
                success: function (json) {
                    updateCartView(json);
                },
                error: function () {
                    toastr.error(window.toastr_error);
                },
                complete: function () {
                    loader.removeClass("loading");
                }
            });
            
        return false;    
    });
  
//=== Set cart reserved 
    $("body").on("click", ".service-reserve-cart-btn", function (event) {
        event.stopPropagation();
        
        $(this).closest("form").submit();
        return false;
    });
    
    $("body").on("submit", ".service-reserve-cart-form", function () {
        var _this_form = $(this);
        var _this_loader = _this_form.find(".for-load");
        var data_form = _this_form.serialize();

        if (_this_loader.hasClass("loading")) {
            return false;
        }

        $.ajax({
            type: _this_form.attr("method"),
            url: _this_form.attr("action"),
            data: data_form,
            beforeSend: function () {
                _this_loader.addClass("loading");
            },
            success: function (data_json) {
                if (typeof data_json !== 'undefined') {
                    if (data_json.success == 1) {
                        if (data_json.redirect_url) {
                            window.location = data_json.redirect_url;
                        }

                        toastr.success(data_json.success_message);
                    } else {
                        toastr.success(data_json.error_message);
                    }
                }
            },
            error: function () {                
                toastr.error(window.toastr_error);
            },
            complete: function () {
                _this_loader.removeClass("loading");
            }
        });
        return false;
    });    
});
