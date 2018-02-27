<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 2018/2/27
  Time: 13:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title><s:message code="CommonException.title"/></title>
</head>
<body>
    <c:out value="${exception}"/>
</body>
</html>
