<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 2018/2/25
  Time: 12:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <style>
        h1{
            color: cadetblue;
        }
    </style>
    <meta charset="UTF-8">
    <title>Upload</title>
</head>
<h1>上传文件</h1>
<body>
    <form action="/file/upload" method="post" enctype="multipart/form-data">
        <input type="file" name="file" value="选择上传文件"/><br>
        <input type="submit" value="提交"/>
    </form>
</body>
</html>
