$(document).ready(function () {  
    if (!getCookie('manual_change_city') || !getCookie('city_id')) {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function (position) {
                var latitude = position.coords.latitude;
                var longitude = position.coords.longitude;

                var phorm_data = Object();
                phorm_data.latitude = latitude;
                phorm_data.longitude = longitude;

                var loader = $(".cityChoice-link");
                loader.addClass("loading");

                $.ajax({
                    url: "/ajax/set_city.php",
                    type: "POST",
                    data: phorm_data,
                    dataType: 'json',
                    success: function (reply) {
                        setCookie('manual_change_city', 1);
                        loader.removeClass("loading");
                        //window.location.reload(true);
                        
                        try {
                            $(".city_accept .city_accept_name .name").text(reply.result.name);
                            $(".cityChoice-link .cityChoice-link-btn").text(reply.result.name);
                            $("select.service_set-city").val(reply.result.id).trigger('refresh');
                            $(".city_accept").addClass("active");
                        } catch (e) {

                        }
                    },
                    error: function () {
                        setCookie('manual_change_city', 1);
                        loader.removeClass("loading");
                    }
                });
            },
            function (error) {
                setCookie('manual_change_city', 1);
            });
        } else {
            setCookie('manual_change_city', 1);
        }
    }


    function filterCity() {
        var region_filter = null;
        var name_city_filter = null;
        var city_elem = $(".cityChoice-elem .service_set-city");

        var active_region = $(".service_choose-region.active");
        if (active_region.length) {
            var region_filter = active_region.data("id");
        }

        var name_city_input = $(".service_select-city-input");
        if (name_city_input.val() !== "") {
            var name_city_filter = new RegExp(name_city_input.val(), 'gi');
        }

        if (region_filter != null || name_city_filter != null) {
            city_elem.hide();
            city_elem.filter(function () {
                if (region_filter !== null && name_city_filter !== null) {
                    return ($(this).data('region_id') == region_filter) && ($(this).data('name').match(name_city_filter));
                } else if (region_filter !== null) {
                    return $(this).data('region_id') == region_filter;
                } else if (name_city_filter !== null) {
                    return $(this).data('name').match(name_city_filter);
                }
            }).show();
        } else {
            city_elem.show();
        }
    }
    
    //=== Filter city by region
    $("body").on("click", ".service_choose-region", function(){
        var _this = $(this);
        
        if (!_this.hasClass("active")) {
            $(".service_choose-region").removeClass("active");
            _this.addClass("active");
        } else {
            $(".service_choose-region").removeClass("active");
        }
        
        filterCity();
    });
    
    //=== Filter city by name
    $("body").on("keyup", ".service_select-city-input", function(){  
        filterCity();
    });
    
    //=== Set city
    function setCity(city_id, _this_preloader) {
        if (city_id > 0) {
            var phorm_data = Object();
            phorm_data.city_id = city_id;

            $.ajax({
                url: "/ajax/set_city.php",
                type: "POST",
                data: phorm_data,
                dataType: 'json',
                beforeSend: function () {
                    _this_preloader.addClass("loading");
                },
                success: function (reply) {
                    window.location.reload(true);
                },
                error: function () {
                    toastr.error(window.toastr_error);
                    _this_preloader.removeClass('loading');
                }
            });
        }
    }
    
    $("body").on("click", "div.service_set-city, a.service_set-city", function(){  
        var _this = $(this);
        var _this_preloader  = $(this);
        
        setCity(_this.data('id'), _this_preloader);
        
        return false;
    });
    
    $("body").on("change", "select.service_set-city", function(){  
        var _this = $(this);
        var _this_preloader  = $(".cityChoice-link");
        
        setCity(_this.val(), _this_preloader);
        
        return false;
    });
    

    $("body").on("click", ".service_city_accept_btn", function(){  
        $(".city_accept").removeClass('active');
        
        return false;
    });
});
