<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 2018/2/26
  Time: 13:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title><s:message code="testConvert.title"/> </title>
    <link rel="stylesheet" type="text/css" href="../../resources/style.css">
</head>
<body>
    <form method="POST" action="<c:url value="/convert/insertEmployee"/>" >
        <label for="employeeList" class="error" >待加入Employee ：</label>
        <input type="text"  id="employeeList" name="employeeList"/><br>
        <input type="submit" value="提交">
    </form>
</body>
</html>
