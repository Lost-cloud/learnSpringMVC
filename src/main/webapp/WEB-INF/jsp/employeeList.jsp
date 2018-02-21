<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employees</title>
</head>
<body>
    <P>This is employee List</P>
    <c:forEach items="${employeeList}" var="employee">
        <c:out value="${employee}"/><br>
    </c:forEach>
</body>
</html>
