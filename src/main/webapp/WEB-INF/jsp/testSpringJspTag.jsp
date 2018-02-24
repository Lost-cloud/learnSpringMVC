<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 2018/2/24
  Time: 14:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <sf:form method="Post" modelAttribute="employee"  action="register">
        名称:<sf:input path="realName"/><br>
        性别:<sf:input path="sex"/><br>
        生日:<sf:input path="birthday" type="date"/><br>
        电话:<sf:input path="mobile"/><br>
        邮箱:<sf:input path="email" type="email"/><br>
        地址:<sf:input path="position"/><br>
        备注:<sf:input path="note"/><br>
        <input type="submit" value="注册">
    </sf:form>
</body>
</html>
