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
        const url = "/params/json/listEmployee";
        const findName = {employeeName: "king"};
        const deleteIds=[93,94];
        $(function () {
            $("#submit").click(   function () {
                $.post({url:url,contentType : "application/json", data:JSON.stringify(findName),dataType:"text",success:function (msg) {
                        console.log(msg);
                        window.location.href="/params/json/showEmployees";
                    }});
            });
            $("#deleteEmp").click(   function(){
                $.post({url:"/params/json/deleteEmployees",contentType : "application/json", data:JSON.stringify(deleteIds),dataType:"text",success:function (msg) {
                        console.log(msg);
                    }});
            });
            $("#findEmpForm").click(function () {
                $.post({url:"/params/json/findEmployees",contentType : "application/json", data:$("findEmpForm").serialize(),dataType:"text",success:function (msg) {
                        console.log(msg);
                    }});
            });
        });
    </script>
</head>
<body>
    <button id="submit">提交数据</button><br>
    <button id="deleteEmp">删除Employee</button>
    <form  id="findEmpForm"  action="<c:url value="/params/json/findEmployees"/>">
        <label>
            Employee Name : <input type="text" name="employeeName"><br>
            Employee ID: <input type="text" name="id">
        </label>
        <input type="submit"  value="submit" id="showEmployees"/>
    </form>
</body>
</html>

