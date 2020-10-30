$(document).ready(function () {           
    function replaceMainHtml() {
        $.ajax({
                        type: "POST",
                        url: "/pet/",
                        dataType: 'json',
                        success: function (data_json) {
                            if (typeof data_json != 'undefined') {
                                $("#htmlPetControlPanel").html(data_json.htmlPetControlPanel);
                                $("#htmlPet").html(data_json.htmlPet);
                                $("#htmlPetLife").html(data_json.htmlPetLife);
                            }
                        },
                        error: function () {
                            toastr.error(window.toastr_error);
                        }
            });
    }

    setInterval(replaceMainHtml, 3000);
});
