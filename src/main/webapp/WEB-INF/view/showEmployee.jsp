<!doctype html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib  prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<html>
<head>
   <meta charset="UTF-8">
    <title><s:message code="showEmployee.title"/></title>
</head>
<body>
    <p>
        <strong><c:out value="${employee}"/> </strong>
    </p>
</body>
</html>
