<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 2018/2/19
  Time: 21:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/cssFiles/style.css"/>">
</head>
<body>
    <h1>Registration</h1>

    <form method="post">
        <%--直接转换为bean对应的属性--%>
        Id: <input type="text" name="id"><br>
        Name: <input  type="text" name="realName"/><br>
        Note: <input type="text" name="note"/><br>
        mobile: <input type="text" name="mobile"/><br>
            email: <input type="text" name="email"/><br>
            position: <input type="text" name="position"/><br>

        <%--bean不能对应的类型--%>
            Birthday:  <input type="date" name="emp_birthday"/><br>
            Sex: <input type="text" name="employee_sex"/><br>

        <input type="submit" value="Register"><br>
    </form>

</body>
</html>
