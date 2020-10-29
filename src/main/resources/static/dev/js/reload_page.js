$(document).ready(function () {           
    function replaceMainHtml() {
        console.log("111111");
        $.ajax({
                        type: "POST",
                        url: "/pet/",
                        dataType: 'json',
                        success: function (data_json) {
                            if (typeof data_json != 'undefined') {
                                $("#htmlPetControlPanel").html(data_json.htmlPetControlPanel);
                                $("#htmlPet").html(data_json.htmlPet);
                            }
                        },
                        error: function () {
                            toastr.error(window.toastr_error);
                        }
            });
    }

    setInterval(replaceMainHtml, 3000);
});
