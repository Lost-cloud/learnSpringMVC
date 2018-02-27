<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title><s:message code="testControllerAdvice.title"/></title>
</head>
<body>
    <a href="<c:url value="/advice/exception"/>">测试异常</a>
    <form action="/advice/test" method="post">
        <label>
            Date :<input type="date" name="date">
        </label>
        <br>
        <label>
            Amount :<input type="text" name="amount">
        </label>
        <br>
        <input type="submit" value="提交">
    </form>
</body>
</html>
