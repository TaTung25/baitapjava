<%-- 
    Document   : welcome
    Created on : Aug 27, 2026, 10:07:00 AM
    Author     : 56745654242453456656
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Trang quản trị</title>
    </head>
    <body>
        <h2>Xin chào, ${sessionScope.username}</h2>
        <ul>
            <li><a href="${pageContext.request.contextPath}/students">Quản lý sinh viên</a></li>
            <li><a href="${pageContext.request.contextPath}/logout">Đăng xuất</a></li>
        </ul>
    </body>
</html>

