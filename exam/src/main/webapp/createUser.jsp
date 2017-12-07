<%--
  Created by IntelliJ IDEA.
  User: chenjianjun
  Date: 15/7/15
  Time: 10:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>创建用户</title>
</head>
<body>
    <form method="post" action="user/createUser.do">
        登录名:<input type="text" name="userName" value="${user.userName}" />
        密码:<input type="password" name="password" />
        姓名:<input type="text" name="name" value="${user.name}" />
        年龄:<input type="text" name="age" value="${user.age}" />
        <input type="submit" name="提交"/>
        <input type="reset" name="重置">
    </form>

    <c:out value="${message}" />
</body>
</html>
