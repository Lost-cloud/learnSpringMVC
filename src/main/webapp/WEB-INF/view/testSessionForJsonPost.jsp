<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ page pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title><s:message code="testSessionJsonPost.title"/></title>
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
