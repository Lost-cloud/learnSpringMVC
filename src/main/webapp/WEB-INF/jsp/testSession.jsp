<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    the id is :<c:out value="${sessionScope.id}"/><br>
    the employee is : <c:out value="${sessionScope.employee}"/><br>
    <p>
        the employees are:
        <c:forEach items="${redirectEmployeeList}" var="employee">
            <c:out value="${employee}"/><br>
        </c:forEach>
    </p>
    <p>
        The Employee in map :<c:out value="${sessionScope.employeeMap['employee100']}"/>
    </p>
</body>
</html>
