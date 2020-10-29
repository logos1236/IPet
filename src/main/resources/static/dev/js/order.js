jQuery(document).ready(function () {
//=== Create pet
    $("body").on("click", ".service-action-pet-create-btn", function (event) {
        event.stopPropagation();

        var _this = $(this);
        var _this_form = _this.closest("form");

        $(this).closest("form").submit();
        return false;
    });
    $("body").on("submit", ".service-action-pet-create-from", function () {
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
                dataType: 'json',
                beforeSend: function () {
                    _this_loader.addClass("loading");
                },
                success: function (data_json) {
                    if (typeof data_json !== 'undefined') {
                        if (data_json.success == 1) {
                            toastr.success(data_json.success_message);

                            if (data_json.redirect_url) {
                                 window.location = data_json.redirect_url;
                            }
                        } else {
                            toastr.error(data_json.error_message);
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

//=== Create order to pet
    $("body").on("click", ".service-action-pet-btn", function (event) {
        event.stopPropagation();

        var _this = $(this);
        var _this_form = _this.closest("form");

        _this_form.find("input[name=action]").val(_this.data("action"));

        $(this).closest("form").submit();
        return false;
    });
    $("body").on("submit", ".service-action-pet-from", function () {
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
                dataType: 'json',
                beforeSend: function () {
                    _this_loader.addClass("loading");
                },
                success: function (data_json) {
                    if (typeof data_json !== 'undefined') {
                        if (data_json.success == 1) {
                            toastr.success(data_json.success_message);

                            $("#htmlPetControlPanel").html(data_json.htmlPetControlPanel);
                            $("#htmlPet").html(data_json.htmlPet);
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
