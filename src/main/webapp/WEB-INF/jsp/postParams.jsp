<!doctype html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>EmployeeJsonParams</title>
    <%--加载JQuery文件--%>
    <script  src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <script>
        $(function () {
            const url = "/params/json/listEmployee";
            const params = {employeeName: "king"};
            $("button").click(function () {
                // $.ajax({
                //     type:"POST",
                //     url:url,
                //     contentType :"application/json",
                //     data : JSON.stringify(params),
                //     dataType:"text",
                //     success:function (msg) {
                //         console.log(msg);
                //     }
                // });
                $.post({url:url,contentType : "application/json", data:JSON.stringify(params),dataType:"text",function () {
                        window.location.href="/params/json/showEmployees";
                    }});
            });
        });
    </script>
</head>
<body>
    <button id="submit">提交数据</button>
    <form action="<c:url value="/params/json/showEmployees"/>" method="get">
        <input type="submit" value="submit" id="showEmployees"/>
    </form>
</body>
</html>

