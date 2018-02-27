<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title><s:message code="registerForm.title"/></title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css"/>">
    <style>
        h1{
            color: cornflowerblue;
        }
    </style>
</head>
<body>
    <h1>Registration</h1>
    <form method="post" action="<c:url value="/Employee/register"/>">

        <%--直接转换为bean对应的属性--%>
        <label>
            Name:<input type="text" name="realName"/><br>
        </label>
        <label>
            mobile:<input type="text" name="mobile"/><br>
        </label>
        <label>
            email:<input type="text" name="email"/><br>
        </label>
        <label>
            position:<input type="text" name="position"/><br>
        </label>

        <%--bean不能对应的类型--%>
        <label>
            Birthday:<input type="date" name="birthday"/><br>
        </label>
        <label>
            Sex:<input type="text" name="sex"/><br>
        </label>
        <label>
            Note:<input type="text" name="note"/><br>
         </label>
         <input type="submit" value="Register"><br>

    </form>
</body>
</html>
