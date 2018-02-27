<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!doctype html>
<html>
<head>
    <style>
        h1{
            color: cadetblue;
        }
    </style>
    <meta charset="UTF-8">
    <title><s:message code="testUpload.title"/></title>
</head>
<h1>上传文件</h1>
<body>
    <form action="/file/upload" method="post" enctype="multipart/form-data">
        <input type="file" name="file" value="选择上传文件"/><br>
        <input type="submit" value="提交"/>
    </form>
</body>
</html>
