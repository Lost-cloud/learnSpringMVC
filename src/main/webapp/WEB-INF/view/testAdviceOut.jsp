<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title><s:message code="testAdviceOut.title"/></title>
</head>
<body>
    InitMessage:<c:out value="${initMessage}"/><br>
    Date:<c:out value="${date}"/><br>
    Amount:<c:out value="${amount}"/><br>
</body>
</html>
