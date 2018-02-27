<%@ page import="com.king.domain.Employee" %>
<%@ page import="java.util.Set" %>
<%@ page import="static sun.misc.Version.println" %><%--
  Created by IntelliJ IDEA.
  User: king
  Date: 2018/2/26
  Time: 23:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
the employees are:
    <c:forEach items="${employeeList}" var="employee">
        <c:out value="${employee}"/><br>
    </c:forEach>
</body>
</html>
