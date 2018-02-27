<%@page  pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8" >
    <title><s:message code="home.title"/> </title>
</head>
<body>
<h2>Hello World!</h2>
<a href="Employee/register">Register</a><br>
<a href="params/findEmployees">Find employee</a><br>
<a href="params/testSessionAttribute">Session Attribute</a><br>
<a href="excel/export">导出Excel</a><br>
<a href="Employee/SpringForm/register">测试Spring JspTag</a><br>
<a href="file/upload">上传文件</a><br>
<a href="convert/insertEmployee">转换插入Employee</a>
<a href="advice/">控制器通知测试</a>
</body>
</html>
