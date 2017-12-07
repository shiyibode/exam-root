<%--
  Created by IntelliJ IDEA.
  User: chenjianjun
  Date: 15/7/15
  Time: 10:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>创建用户成功</title>
</head>
<body>
    <c:out value="${message}" />
    <table>
        <tr>
            <th>用户名:</th>
            <td>${user.userName}</td>
            <th>姓名</th>
            <td>${user.name}</td>
            <th>年龄</th>
            <td>${user.age}</td>
        </tr>
    </table>
</body>
</html>
