<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>LOGIN shiro login page</title>
</head>
<body>
<div>
    <input id="userName" name="userName" value="">
</div>
<div>
    <input id="password" name="password" value="">
</div>
<div>
    <input type="button" id="btnSave"  value="SUBMIT">
</div>
<script src="https://cdn.bootcss.com/jquery/1.11.3/jquery.js"></script>
<script>
    $(function() {
        $("#btnSave").click(function () {
            var username=$("#userName").val();
            var password=$("#password").val();
            $.ajax({
                cache: true,
                type: "POST",
                url: "/login",
                data: "userName=" + username + "&password=" + password,
                dataType: "json",
                async: false,
                error: function (request) {
                    console.log("Connection error");
                },
                success: function (data) {
                    if (data.status == 0) {
                        window.location = "/index";
                        return false;

                    } else {
                        alert(data.message);
                    }

                }
            });
        });
    });
</script>
</body>
</html>