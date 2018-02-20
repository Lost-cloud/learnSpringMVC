<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 2018/2/20
  Time: 15:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<html>
<head>
    <title>Employees</title>
</head>
<body>
    <P>This is employee List</P>
    <c:forEach items="${employeeList}" var="employee">
        <c:out value="${employee}"/><br>
    </c:forEach>
</body>
</html>
