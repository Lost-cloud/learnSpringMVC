<!doctype html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/cssFiles/style.css"/>">
</head>
<body>
    <h1>Registration</h1>
    <form method="post" action="<c:url value="/Employee/register"/>">

        <%--直接转换为bean对应的属性--%>
        <label>
            Name:<input type="text" name="realName"/>
        </label><br>
        <label>
            mobile:<input type="text" name="mobile"/>
        </label><br>
            <label>
                email:<input type="text" name="email"/>
            </label><br>
            <label>
                position:<input type="text" name="position"/>
            </label><br>
            <label>
                Note:<input type="text" name="note"/>
            </label><br>

            <%--bean不能对应的类型--%>
            <label>
                Birthday:<input type="date" name="emp_birthday"/>
            </label><br>
            <label>
                Sex:<input type="text" name="employee_sex"/>
            </label><br>

        <input type="submit" value="Register"><br>

    </form>
</body>
</html>
