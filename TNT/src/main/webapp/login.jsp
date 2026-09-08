<%-- 
    Document   : login
    Created on : Sep 8, 2026, 7:27:49 AM
    Author     : 56745654242453456656
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Đăng nhập - Quản Lý Kho TNT</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light d-flex align-items-center vh-100">
        <div class="container" style="max-width: 400px;">
            <div class="card shadow">
                <div class="card-body p-4">
                    <h3 class="text-center text-primary mb-4 fw-bold">KHO HÀNG TNT</h3>
                    <% if (request.getAttribute("error") != null) {%>
                    <div class="alert alert-danger"><%= request.getAttribute("error")%></div>
                    <% }%>
                    <form action="LoginServlet" method="post">
                        <div class="mb-3">
                            <label class="form-label">Tên đăng nhập</label>
                            <input type="text" name="username" class="form-control" required placeholder="Nhập tên đăng nhập">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Mật khẩu</label>
                            <input type="password" name="password" class="form-control" required placeholder="Nhập mật khẩu">
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Đăng Nhập</button>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>