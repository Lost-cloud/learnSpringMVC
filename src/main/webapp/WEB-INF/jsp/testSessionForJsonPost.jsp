<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JsonPost</title>
</head>
<body>
<p>
    the employees are:
    <c:forEach items="${jsonEmployeeList}" var="employee">
        <c:out value="${employee}"/><br>
    </c:forEach>
</p>
</body>
</html>
