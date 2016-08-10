
setTimeout ( function() {
                var url = $.cookie("Referer").val();
                if (url != null) {
                window.location = url;
                } else {
                window.location = "../user";
                };

    }, 10000);