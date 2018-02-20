<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 2018/2/20
  Time: 17:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html" charset="UTF-8">
    <title>EmployeeJson</title>
    <%--加载JQuery文件--%>
    <script  src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <script>
        $(function () {
            var url ="/params/json/listEmployee";
            var params={employeeName:"king"};
            $("button").click(function () {
                // $.get("/params/json/1",function (data, status) {
                //     alert(data+" "+status);
                // });
                $.ajax({
                    type:"POST",
                    url:url,
                    contentType :"application/json",
                    data : JSON.stringify(params),
                    dataType:"text",
                    success:function (msg) {
                        console.log(msg);
                    }
                });
                // $.post({url:url,contentType : "application/json", data:JSON.stringify(params),dataType:"text"});
            });
        });
    </script>
</head>
<body>
    <button id="submit">提交数据</button>
    <form action="/params/json/showEmployees" method="get">
        <input type="submit" value="submit" id="showEmployees"/>
    </form>
</body>
</html>

