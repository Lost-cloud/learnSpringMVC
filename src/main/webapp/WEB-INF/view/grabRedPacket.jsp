<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 2018/3/6
  Time: 12:15
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title><s:message code="grabRedPacket.title"/></title>
    <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <script>
        const url = "/grab/packet?redPacketId=1&userId=";
        $(document).ready(function () {
            const max = 30000;
            for(var i=1; i<=max; i++) {
                $.post({url:(url+i),success:function (msg) {
                        console.log(msg);
                    }});
            }
        })

    </script>
</head>
<body>

</body>
</html>
