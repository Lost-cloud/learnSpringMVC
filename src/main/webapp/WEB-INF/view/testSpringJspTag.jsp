<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 2018/2/24
  Time: 14:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <%--message元素 code属性可以选择属性文件中的参数--%>
    <title><s:message code="testSpringForm.title" /> </title>
    <link rel="stylesheet" type="text/css" href="../../resources/style.css">
</head>
<body>
    <sf:form method="Post" modelAttribute="employee"  action="register">
        <sf:errors path="*" element="div" cssClass="errors"/><br>
        <sf:label path="realName" cssErrorClass="error">名称</sf:label>
        <sf:input path="realName" cssErrorClass="error"/><br>

        <sf:label path="sex" cssErrorClass="error">性别</sf:label> <sf:input path="sex" cssErrorClass="error"/>
        <sf:errors path="sex" cssClass="error"/><br>

        <sf:label path="birthday" cssErrorClass="error">生日</sf:label><sf:input path="birthday" type="date" cssErrorClass="error"/><br>

        <sf:label path="mobile" cssErrorClass="error">电话</sf:label><sf:input path="mobile" cssErrorClass="error"/>
        <sf:errors path="mobile" cssClass="error"/><br>

        <sf:label path="email" cssErrorClass="error">邮箱</sf:label>
        <sf:input path="email" type="email" cssErrorClass="error"/><br>

        <sf:label path="position" cssErrorClass="error">地址</sf:label>
        <sf:input path="position" cssErrorClass="error"/><br>

        <sf:label path="note" cssErrorClass="error">备注</sf:label>
        <sf:input path="note" cssErrorClass="error"/><br>

        <input type="submit" value="注册">
    </sf:form>
</body>
</html>
