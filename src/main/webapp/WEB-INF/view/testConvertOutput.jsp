<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title><s:message code="testConvertOutput.title"/></title>
</head>
<body>
the employees are:
    <c:forEach items="${employeeList}" var="employee">
        <c:out value="${employee}"/><br>
    </c:forEach>
</body>
</html>
